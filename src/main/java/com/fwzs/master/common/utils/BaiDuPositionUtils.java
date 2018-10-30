package com.fwzs.master.common.utils;

import com.fwzs.master.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-17 10:16
 * @description 百度定位工具类
 **/
public class BaiDuPositionUtils {

    private final static Logger logger = LoggerFactory.getLogger(BaiDuPositionUtils.class);

    private static final double EARTH_RADIUS = 6378.137;            //地球半径,单位千米

    /**
     * 根据地址获取经纬度信息
     *
     * @param address
     * @return
     */
    public static Map generatePosition(final String address) {
        final Map<String, BigDecimal> position = new HashMap<>();
        final String baiduAPIKey = Global.getConfig("apikey");
        try {
            final String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak=" + baiduAPIKey + "&output=json&address=%s", java.net.URLEncoder.encode(address, "UTF-8"));
            final URL myURL = new URL(url);
            final URLConnection httpsConn = myURL.openConnection();
            if (httpsConn != null) {
                final InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                final BufferedReader br = new BufferedReader(insr);
                String data;
                try {
                    if ((data = br.readLine()) != null) {
                        String lat = data.substring(data.indexOf("\"lat\":")
                                + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                        String lng = data.substring(data.indexOf("\"lng\":")
                                + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                        insr.close();
                        position.put("lat", new BigDecimal(lat));
                        position.put("lng", new BigDecimal(lng));
                    }
                } catch (IOException e) {
                    logger.error("无法访问指定的地址", e);
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码", e);
        } catch (MalformedURLException e) {
            logger.error("URL地址不正确", e);
        } catch (IOException e) {
            logger.error("无法访问指定的地址", e);
        } finally {
            return position;
        }
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param lat1 第一个纬度
     * @param lng1 第一个经度
     * @param lat2 第二个纬度
     * @param lng2 第二个经度
     * @return 计算两个经纬度的距离
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
