package com.coagent.lyscs.keepaccounts;

import android.app.Activity;
import android.os.Bundle;

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
 * Created by lyscs on 2017/5/12.
 */
public class ChartsActivity extends Activity {
    private LineChartView mChart;
    private List<CostBean> mCostBeenList;
    private Map<String, Integer> table = new TreeMap<>();  //自动排好序
    private LineChartData mChartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mChart = (LineChartView) findViewById(R.id.chart);
        mCostBeenList = (List<CostBean>) getIntent().getSerializableExtra("costList");

        generateValues(mCostBeenList);
        generateData();
        mChart.setLineChartData(mChartData);
    }

    private void generateData() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int index = 0;
        for (Integer value : table.values()){
            values.add(new PointValue(index, value));
            index++;
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        mChartData = new LineChartData(lines);
    }

    /**
     * 对花费的全部数据整理成一个map，相同日期花费的合并在一起
     * @param allData
     */
    private void generateValues(List<CostBean> allData) {
        if(allData != null){
            for (int i=0; i<allData.size(); i++){
                CostBean bean = allData.get(i);
                String date = bean.costDate;
                int money = Integer.parseInt(bean.costMoney);
                if (!table.containsKey(date)){  //判断表中是否已经有改日期的数据
                    table.put(date, money);
                }else {
                    int originMoney = table.get(date);
                    table.put(date, (originMoney+money));
                }
            }
        }
    }
}
