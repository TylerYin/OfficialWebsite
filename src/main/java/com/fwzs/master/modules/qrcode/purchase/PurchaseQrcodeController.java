package com.fwzs.master.modules.qrcode.purchase;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.FileUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author Tyler Yin
 * @create 2018-06-11 14:02
 * @description Purchase Qrcode Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/qrCode")
public class PurchaseQrcodeController extends BaseController {

    private final static String PACKAGE_TYPE_ZERO = "1";
    private final static String PACKAGE_TYPE_FIVE_HUNDRED = "2";
    private final static String PACKAGE_TYPE_ONE_THOUSAND = "3";

    /**
     * 跳转到防伪码套餐选择界面
     *
     * @return
     */
    @RequestMapping("purchase")
    public String purchase(String packageSize, String packageType, String amount, HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        boolean isFormValid = StringUtils.isNotBlank(packageSize) && StringUtils.isNotBlank(packageType)
                && StringUtils.isNotBlank(amount);
        if (isFormValid) {
            if (!PACKAGE_TYPE_ZERO.equalsIgnoreCase(packageType)) {
                return "redirect:" + Global.getAdminPath() + "/fwzs/qrCode/orderPayment?packageType=" + packageType;
            } else {
                return "redirect:" + Global.getAdminPath() + "/fwzs/qrCode/orderList";
            }
        }
        return "modules/qrcode/purchase/qrCodePurchaseForm";
    }

    /**
     * 跳转到防伪码订单支付界面
     */
    @RequestMapping("orderPayment")
    public String orderPayment(String packageType, String paymentType, HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(packageType) && StringUtils.isBlank(paymentType)) {
            return "modules/qrcode/purchase/qrCodePurchaseForm";
        } else {
            if (StringUtils.isNotBlank(paymentType)) {
                return "redirect:" + Global.getAdminPath() + "/fwzs/qrCode/orderList";
            } else {
                model.addAttribute("packageType", packageType);
                return "modules/qrcode/purchase/qrCodeOrderPaymentForm";
            }
        }
    }

    /**
     * 跳转到防伪码订单列表界面
     *
     * @return
     */
    @RequestMapping("orderList")
    public String orderList(HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        return "modules/qrcode/purchase/qrCodeOrderList";
    }

    /**
     * 下载码文件
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("downloadQrcodeFile")
    public void downloadQrcodeFile(HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        String qrCodeFilePath = this.getClass().getClassLoader().getResource("qrCodeList.zip").getPath();
        String filePath = FileUtils.path(qrCodeFilePath);
        FileUtils.downFile(new File(filePath), request, response);
    }
}