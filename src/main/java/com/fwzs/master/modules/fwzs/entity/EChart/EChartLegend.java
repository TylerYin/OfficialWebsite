package com.fwzs.master.modules.fwzs.entity.EChart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-03-30 9:49
 * @description EChartLegend Entity
 **/
public class EChartLegend implements Serializable {
    private static final long serialVersionUID = 1L;

    private String left;                                    // 对齐
    private Integer bottom;                                 // 向下的距离
    private List<String> data = new ArrayList<>();          // 图例

    public Integer getBottom() {
        return bottom;
    }

    public void setBottom(Integer bottom) {
        this.bottom = bottom;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
