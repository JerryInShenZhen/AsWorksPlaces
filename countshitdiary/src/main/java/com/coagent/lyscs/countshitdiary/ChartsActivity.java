package com.coagent.lyscs.countshitdiary;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.coagent.lyscs.countshitdiary.part.ShitBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
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
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mChartView = (LineChartView) findViewById(R.id.chart);
        mShitBeenList = (List<ShitBean>) getIntent().getSerializableExtra("shitData");

        generateValues(mShitBeenList);
        generateData();

        mChartView.setInteractive(true);
        mChartView.setZoomType(ZoomType.HORIZONTAL);
        mChartView.setMaxZoom(2);
        mChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL); //图标内可移动
        mChartView.setLineChartData(chartData);
        mChartView.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(mChartView.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        mChartView.setCurrentViewport(v);
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
            //mAxisXValues.add(new AxisValue(index).setLabel(table.toString()));
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);   //数据点形状--圆形
        //line.setHasLabels(true);    //数据坐标加上备注
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        chartData = new LineChartData(lines);

        //X坐标轴
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true); //true是斜显示,false是直显示
        axisX.setTextColor(Color.BLUE);
        axisX.setName("日");
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(8);  //最多几个坐标
        axisX.setValues(mAxisXValues);  //填充坐标名称
        axisX.setHasLines(true);    //设置x轴分割线
        chartData.setAxisXBottom(axisX);    //设置X轴在底部

        //Y坐标轴
        Axis axisY = new Axis();
        axisY.setTextSize(10);
        axisY.setName("量/KG");
        chartData.setAxisYLeft(axisY);
    }

    /**
     * 将数据中日期相同的数据合并，所有数据封装成map格式
     */
    private void generateValues(List<ShitBean> allData){
        if (allData != null){
            int index = 1;
            for (int i=0; i<allData.size(); i++){
                ShitBean bean = allData.get(i);
                String date = bean.shitData;
                //int shitKg = Integer.parseInt(bean.shitKg);
                float shitKg = Float.parseFloat(bean.shitKg);
                if (!table.containsKey(date)){
                    //Log.i("记录日期", "generateValues: "+date.substring(5));
                    table.put(date, shitKg);
                    mAxisXValues.add(new AxisValue(index).setLabel(date.substring(5)));
                    index++;
                }else {
                    float originShit = table.get(date);
                    table.put(date, (originShit + shitKg));
                }
            }
        }
    }
}
