package com.coagent.lyscs.crazydemoall.bean;

/**
 * 仿微信图片选择器弹出选文件夹的内容的Bean
 * Created by lyscs on 2017/5/18.
 */
public class FolderBean {
    private String dir;   //文件夹的路径
    private String firstImgPath;        //文件夹下第一张图片的路径
    private String name;    //当前文件夹的名称
    private int count;      //当前文件夹下图片数量

    public FolderBean(String dir, String firstImgPath, String name, int count) {
        this.dir = dir;
        this.firstImgPath = firstImgPath;
        this.name = name;
        this.count = count;
    }

    public FolderBean() {
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;

        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf+1);
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
