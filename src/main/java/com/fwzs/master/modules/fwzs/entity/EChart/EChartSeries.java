package com.fwzs.master.modules.fwzs.entity.EChart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-03-30 10:15
 * @description EChartSeries Entity
 **/
public class EChartSeries implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;                                              // 数据名称
    private String type;                                              // 图表类型
    private String radius;                                            // 半径大小
    private String selectedMode;                                      // 选择模式
    private List<EChartData> eChartDataList = new ArrayList<>();      // 数据集

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public List<EChartData> geteChartDataList() {
        return eChartDataList;
    }

    public void seteChartDataList(List<EChartData> eChartDataList) {
        this.eChartDataList = eChartDataList;
    }
}
