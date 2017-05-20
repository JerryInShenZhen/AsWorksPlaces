package com.coagent.lyscs.crazydemoall.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 图片加载类----单例模式
 * Created by lyscs on 2017/5/17.
 */
public class ImageLoader {

    private static ImageLoader mInstance;
    private LruCache<String, Bitmap> mLruCache;  //图片加载缓存核心对象
    private ExecutorService mThreadPool;        //线程池
    private static final int DEFAULT_THREAD_COUNT = 3;  //默认线程池线程数量
    private Type mType = Type.LIFO;     //队列默认调度方式为后入先出

    public enum Type {       //调度方式枚举
        FIFO, LIFO;
    }

    private LinkedList<Runnable> mTaskQueue;    //任务队列
    private Thread mPoolThread;         //后台轮询线程
    private Handler mPoolThreadHandler; //跟轮询线程绑定的handler
    private Handler mUIHandler;         //更新UI的handler

    //同步线程顺序防止mPoolThreadHandler开始时是空指针
    private Semaphore mSemaphoremPoolThreadHandler = new Semaphore(0);

    private Semaphore mSemaphoremThreadPool;       //信号量--为了轮询任务中一个任务执行完才去获取下一个任务

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    /**
     * 初始化轮询线程、缓存、
     */
    private void init(int threadCount, Type type) {

        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //从线程池中取出任务执行并再次LOOP
                        if (msg.what == 0x110) {
                            mThreadPool.execute(getTask());
                            try {
                                //获取一个任务后阻塞直到该任务执行完才释放
                                mSemaphoremThreadPool.acquire();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                mSemaphoremPoolThreadHandler.release();     //释放阻塞
                Looper.loop();
            }
        };
        mPoolThread.start();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();   //得到应用使用最大内存
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {    //返回每张图的大小
                return (value.getRowBytes() * value.getHeight());
            }
        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;

        mSemaphoremThreadPool = new Semaphore(threadCount);
    }

    /**
     * 两重判断--单例模式
     *
     * @return：单例模式的图片加载类对象mInstance
     */
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(DEFAULT_THREAD_COUNT, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    /**
     * 通过路径加载图片并回调设置好在imageView中
     *
     * @param path 加载图片的路径
     */
    public void loadImage(final String path, final ImageView imageView) {
        imageView.setTag(path);
        if (mUIHandler == null) {
            mUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //获取得到图片，并为imageView回调设置图片
                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bitmap = holder.bitmap;
                    String path = holder.path;
                    ImageView imageView = holder.imageView;
                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            };
        }

        Bitmap bitmap = getBitmapFromLruCache(path);
        if (bitmap != null) {
            Message message = Message.obtain();
            ImageBeanHolder holder = new ImageBeanHolder(path, bitmap, imageView);
            message.obj = holder;
            mUIHandler.sendMessage(message);
        } else {
            addTasks(new Runnable() {   //缓存没有图片的话要去添加一个加载图片的任务到队列
                @Override
                public void run() {
                    //获得图片要显示的大小并压缩图片
                    ImageSize imageSize = getImageSize(imageView);
                    Bitmap bm = decodeSampledBitmapFromPath(path, imageSize.width, imageSize.height);
                    addBitmapToLruCache(path, bm);  //将图片加入缓存中

                    Message message = Message.obtain();
                    ImageBeanHolder holder = new ImageBeanHolder(path, bm, imageView);
                    message.obj = holder;
                    mUIHandler.sendMessage(message);

                    mSemaphoremThreadPool.release();    //执行完了就释放信号量以便获取下一个信号量
                }
            });
        }
    }

    /**
     * 将刚加载好的图片加入缓存中，path为key
     */
    private void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null){
            if (bm != null){
                mLruCache.put(path, bm);
            }
        }
    }

    /**
     * 从缓存中获得缓存图片
     *
     * @param key：存入图片时的字段，文件的绝对路径和文件名
     */
    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 往任务队列中添加任务并通知轮询线程
     *
     * @param runnable：要添加的任务
     */
    private synchronized void addTasks(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            //mPoolThreadHandler没初始化前阻塞直到初始化完释放
            if (mPoolThreadHandler == null){
                mSemaphoremPoolThreadHandler.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * @return：取出一个要执行的任务，LIFO
     */
    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else {
            return mTaskQueue.removeLast();
        }
    }

    /**
     * 得到压缩的要显示 imageView的长和宽
     */
    //@SuppressLint("NewApi")
    private ImageSize getImageSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics metrics = imageView.getContext().getResources().getDisplayMetrics();

        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        /*imageSize.width = (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView.getWidth());
        imageSize.height = (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView.getHeight());*/
        int width = imageView.getWidth();   //获取ImageView的实际宽度，若没设定返回的为负数
        if (width <= 0){
            width = lp.width;   //获取ImageView在Layout声明的宽度，若没声明为负数
        }
        if (width <= 0){
            //width = imageView.getMaxWidth();    //检查最大值
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0){    //还是<=0的话取屏幕宽度作为压缩显示宽度
            width = metrics.widthPixels;
        }

        int height = imageView.getHeight();   //获取ImageView的实际高度，若没设定返回的为负数
        if (height <= 0){
            height = lp.height;   //获取ImageView在Layout声明的高度，若没声明为负数
        }
        if (height <= 0){
            //height = imageView.getMaxHeight();    //检查最大值
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        }
        if (height <= 0){    //还是<=0的话取屏幕高度作为压缩显示高度
            height = metrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    /**
     * 利用反射机制获得ImageView的各个属性值
     */
    private static int getImageViewFieldValue(Object object, String fieldName){
        int value = 0;

        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                value = fieldValue;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return value;
    }

    /**
     * 根据图片要显示的宽高对图片进行压缩
     * @param width-height：指定压缩目的的宽高
     */
    private Bitmap decodeSampledBitmapFromPath(String path, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //获得图片的宽高且不把图片加载到内存中
        BitmapFactory.decodeFile(path, options);   //获取图片的实际属性

        options.inSampleSize = caculateInSampleSize(options,width,height); //计算压缩比例
        //使用inSampleSize再次解析图片，要先加载到内存中了
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    /**
     * 根据需求的宽高和图片实际的宽高计算压缩比例SampleSize
     */
    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight){
            int widthRadio = Math.round((width * 1.0f)/reqWidth);
            int heightRadio = Math.round((height * 1.0f)/reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

    private class ImageSize {
        public int width;
        public int height;
    }

    private class ImageBeanHolder {
        public String path;
        public Bitmap bitmap;
        public ImageView imageView;

        public ImageBeanHolder(String path, Bitmap bitmap, ImageView imageView) {
            this.path = path;
            this.bitmap = bitmap;
            this.imageView = imageView;
        }
    }
}
