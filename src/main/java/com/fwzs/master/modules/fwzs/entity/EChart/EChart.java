package com.fwzs.master.modules.fwzs.entity.EChart;

import java.io.Serializable;

/**
 * @author Tyler Yin
 * @create 2018-03-29 15:40
 * @description EChart Entity
 **/
public class EChart implements Serializable {
    private static final long serialVersionUID = 1L;

    private EChartTitle echartTitle;
    private EChartSeries echartSeries;
    private EChartLegend echartLegend;

    public EChartTitle getEchartTitle() {
        return echartTitle;
    }

    public void setEchartTitle(EChartTitle echartTitle) {
        this.echartTitle = echartTitle;
    }

    public EChartSeries getEchartSeries() {
        return echartSeries;
    }

    public void setEchartSeries(EChartSeries echartSeries) {
        this.echartSeries = echartSeries;
    }

    public EChartLegend getEchartLegend() {
        return echartLegend;
    }

    public void setEchartLegend(EChartLegend echartLegend) {
        this.echartLegend = echartLegend;
    }
}
