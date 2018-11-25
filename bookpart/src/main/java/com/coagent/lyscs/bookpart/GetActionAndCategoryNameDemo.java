package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

/**
 * Created by lyscs on 2017/9/10.
 */
public class GetActionAndCategoryNameDemo extends Activity{

    private final static String strAction = "Action为：\n";
    private final static String strCategory = "Category属性为：\n";
    private StringBuilder sb;
    private String strData;
    private TextView tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMain = (TextView) findViewById(R.id.tv_main);

        sb = new StringBuilder();
        sb.append(strAction);
        strData = getIntent().getAction();
        sb.append(strData);
        sb.append("\n");
        Set<String> cates = getIntent().getCategories();
        sb.append(strCategory);
        sb.append(cates);

        tvMain.setText(sb.toString());
    }
}
