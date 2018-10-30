package com.fwzs.master.modules.fwzs.entity;

/**
 * @author Tyler Yin
 * @create 2018-01-25 16:48
 * @description 二维码实体
 **/
public class BitMatrix {
    private static final long serialVersionUID = 1L;

    private String url;                  //要生成的url地址
    private String bitMatrixUrl;         //浏览器文件路径

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBitMatrixUrl() {
        return bitMatrixUrl;
    }

    public void setBitMatrixUrl(String bitMatrixUrl) {
        this.bitMatrixUrl = bitMatrixUrl;
    }
}
