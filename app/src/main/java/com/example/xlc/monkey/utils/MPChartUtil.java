package com.example.xlc.monkey.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;

import java.util.List;

/**
 * @author:xlc
 * @date:2018/11/16
 * @descirbe:MPAndroidChart 图标控件工具类
 */
public class MPChartUtil {

    /**
     * 不显示数据的提示
     * @param chart
     */
    public static  void  NotShowNoDataText(Chart chart){
        chart.clear();
        chart.notifyDataSetChanged();
        chart.setNoDataText("你还没有记录数据");
        chart.setNoDataTextColor(Color.WHITE);
        chart.invalidate();
    }

    /**
     * 配置chart基础设置
     * @param chart 图标
     * @param labels x轴标签
     * @param yMax y轴最大值
     * @param yMin X轴最大值
     * @param isShowLegend 是否显示图例
     */
    public static void configChart(CombinedChart chart, List<String> labels,float yMax,float yMin,boolean isShowLegend){
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setScaleEnabled(false);
        chart.setDragEnabled(true);
        chart.setNoDataText("");
        //不显示描述数据
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        Legend legend = chart.getLegend();
        if (isShowLegend) {
            legend.setEnabled(true);
            legend.setTextColor(Color.WHITE);
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setYEntrySpace(20f);
            //图例的大小
            legend.setFormSize(7f);
            // 图例描述文字大小
            legend.setTextSize(10);
            legend.setXEntrySpace(20f);
        }else{
            legend.setEnabled(false);
        }

        XAxis xAxis = chart.getXAxis();
        // 是否显示x轴线
        xAxis.setDrawAxisLine(true);
        // 设置x轴线的颜色
        xAxis.setAxisLineColor(Color.parseColor("#4cffffff"));
        // 是否绘制x方向网格线
        xAxis.setDrawGridLines(false);
        //x方向网格线的颜色
        xAxis.setGridColor(Color.parseColor("#30FFFFFF"));

        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置x轴文字的大小
        xAxis.setTextSize(12);

        // 设置x轴数据偏移量
        xAxis.setYOffset(5);
        final List<String> labels;
    }

}
