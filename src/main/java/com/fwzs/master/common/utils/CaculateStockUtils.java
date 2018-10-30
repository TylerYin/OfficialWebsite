package com.fwzs.master.common.utils;

import com.fwzs.master.common.config.Global;

/**
 * @author Tyler Yin
 * @create 2018-01-11 18:51
 * @description 根据包装比例和扫来的码计算单品数量
 **/
public class CaculateStockUtils {
    /**
     * 根据包装规格计算需要更新的库存和发货数量
     *
     * @param packRate
     * @param boxCodeCount
     * @return
     */
    public static int caculateSendAmunts(final String packRate, int boxCodeCount, final String boxCodeType) {
        int stock = 1;
        final String packRateArray[] = packRate.split(":");
        if (Global.CODE_TYPE_SKU.equals(boxCodeType)) {
            stock = boxCodeCount;
        } else if (Global.CODE_TYPE_BOX.equals(boxCodeType)) {
            if (2 == packRateArray.length) {
                stock = Integer.valueOf(packRateArray[1]) * boxCodeCount;
            } else {
                stock = Integer.valueOf(packRateArray[2]) * boxCodeCount;
            }
        } else {
            for (String ratio : packRateArray) {
                stock *= Integer.valueOf(ratio);
            }
            stock *= boxCodeCount;
        }
        return stock;
    }

    /**
     * 检查PDA传入的码类型和产品的包装比例是否一致
     *
     * @param packRate
     * @param boxCodeType
     * @return
     */
    public static boolean checkPackRateAndBoxCodeTypeIsValid(final String packRate, final String boxCodeType) {
        boolean isValid = false;
        final String packRateArray[] = packRate.split(":");
        if (Global.CODE_TYPE_SKU.equals(boxCodeType)) {
            isValid = true;
        } else if (Global.CODE_TYPE_BOX.equals(boxCodeType) && (2 == packRateArray.length || 3 == packRateArray.length)) {
            isValid = true;
        } else if (3 == packRateArray.length) {
            isValid = true;
        }
        return isValid;
    }
}
