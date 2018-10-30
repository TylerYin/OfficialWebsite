package com.fwzs.master.modules.fwzs.entity.EChart;

import java.io.Serializable;

/**
 * @author Tyler Yin
 * @create 2018-03-30 9:48
 * @description EChartTitle Entity
 **/
public class EChartTitle implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;       // 标题
    private String subtext;    // 副标题
    private String left;       // 对齐

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }
}
