package com.fwzs.master.common.utils;

import org.springframework.ui.Model;

import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-17 10:51
 * @description Spring Model工具类
 **/
public class ModelUtils {

    /**
     *
     * @param model
     * @param attributeName
     * @return
     */
    public static String getValueFromModel(final Model model, final String attributeName) {
        final Map modelMap = model.asMap();
        for (Object key : modelMap.keySet()) {
            if (key.equals(attributeName) && StringUtils.isNotBlank(modelMap.get(key).toString())) {
                return modelMap.get(key).toString();
            }
        }
        return StringUtils.EMPTY;
    }
}
