package com.fwzs.master.modules.fwzs.entity.EChart;

import java.io.Serializable;

/**
 * @author Tyler Yin
 * @create 2018-03-30 9:59
 * @description EChartData Entity
 **/
public class EChartData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;     // 名称
    private String value;    // 数值
    private Boolean selected;//是否选中

    public EChartData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
