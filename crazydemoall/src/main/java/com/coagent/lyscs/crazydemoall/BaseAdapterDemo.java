package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyscs on 2017/5/13.
 */
public class BaseAdapterDemo extends Activity {

    List<BaseAdapterItemBean> itemBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseadapter);

        itemBeanList = new ArrayList<>();
        initSureData();
        ListView listView = (ListView) findViewById(R.id.lv_main);
        listView.setAdapter(new MyBaseAdapter(this, itemBeanList));

    }

    /**
     * 对itemBeanList随便赋值20个数据做显示测试
     */
    private void initSureData() {
        for (int i = 0; i < 20; i++) {
            BaseAdapterItemBean bean = new BaseAdapterItemBean();
            bean.itemImageResId = R.mipmap.ic_launcher;
            bean.itemTitle = "我是标题"+i;
            bean.itemContent = "我是内容"+i;
            itemBeanList.add(bean);
        }
    }
}
