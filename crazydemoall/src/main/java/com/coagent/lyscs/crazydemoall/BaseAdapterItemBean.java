package com.coagent.lyscs.crazydemoall;

import java.io.Serializable;

/**
 * Created by lyscs on 2017/5/13.
 */
public class BaseAdapterItemBean implements Serializable {

    public int itemImageResId;
    public String itemTitle;
    public String itemContent;

    public BaseAdapterItemBean(String itemContent, int itemImageResId, String itemTitle) {
        this.itemContent = itemContent;
        this.itemImageResId = itemImageResId;
        this.itemTitle = itemTitle;
    }

    public BaseAdapterItemBean() {
    }
}
