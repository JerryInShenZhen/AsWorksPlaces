package com.coagent.lyscs.countshitdiary;

import android.app.Activity;
import android.os.Bundle;

import com.coagent.lyscs.countshitdiary.part.ShitBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by lyscs on 2017/5/26.
 */
public class ChartsActivity extends Activity {
    private LineChartView mChartView;
    private List<ShitBean> mShitBeenList;
    private Map<String, Float> table = new TreeMap<>();   //自动排好序
    private LineChartData chartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mChartView = (LineChartView) findViewById(R.id.chart);
        mShitBeenList = (List<ShitBean>) getIntent().getSerializableExtra("shitData");

        generateValues(mShitBeenList);
        generateData();
        mChartView.setLineChartData(chartData);

    }

    /**
     *将map数据封装成PointValue点数据，点数据分装成线数据
     * 线数据产生LineChartData数据
     */
    private void generateData(){
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int index = 1;
        for (Float value: table.values()){
            values.add(new PointValue(index, value));
            index++;
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        chartData = new LineChartData(lines);
    }

    /**
     * 将数据中日期相同的数据合并，所有数据封装成map格式
     */
    private void generateValues(List<ShitBean> allData){
        if (allData != null){
            for (int i=0; i<allData.size(); i++){
                ShitBean bean = allData.get(i);
                String date = bean.shitData;
                //int shitKg = Integer.parseInt(bean.shitKg);
                float shitKg = Float.parseFloat(bean.shitKg);
                if (!table.containsKey(date)){
                    table.put(date, shitKg);
                }else {
                    float originShit = table.get(date);
                    table.put(date, (originShit + shitKg));
                }
            }
        }
    }
}
