package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.FileUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.utils.ZxingHandler;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.common.web.Servlets;
import com.fwzs.master.modules.fwzs.entity.BitMatrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * @author Tyler Yin
 * @create 2018-01-25 14:20
 * @description 根据URL生成二维码Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/bitMatrix")
public class BitMatrixController extends BaseController {

    @RequestMapping(value = "/form")
    public String save(BitMatrix bitMatrix, Model model) {
        if (StringUtils.isNotBlank(bitMatrix.getUrl())) {
            String bitMatrixStorePath = FileUtils.path(Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
                    + Global.getConfig("bitMatrixStorePath"));
            File file = new File(bitMatrixStorePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            ZxingHandler.encode2(bitMatrix.getUrl(), 400, 400, bitMatrixStorePath);
            bitMatrix.setBitMatrixUrl(FileUtils.path(Servlets.getRequest().getContextPath()
                    + Global.USERFILES_BASE_URL + Global.getConfig("bitMatrixStorePath")));
            model.addAttribute("bitMatrix", bitMatrix);
        } else {
            model.addAttribute("bitMatrix", new BitMatrix());
        }
        return "modules/fwzs/bitMatrixForm";
    }
}
