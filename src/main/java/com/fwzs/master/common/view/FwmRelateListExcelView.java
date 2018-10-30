package com.fwzs.master.common.view;

import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmRelate;
import com.fwzs.master.modules.fwzs.entity.ScPlan;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 该视图模版用来生成Excel报表，用在企业和经销商出入库列表界面导出某个计划所关联的产品码，一级包装码和二级包装码
 *
 * @author Tyler Yin
 * @create 2017-11-27 14:40
 * @description Qrcode List Excel View
 **/
@Component(value = "fwmRelateExcel")
public class FwmRelateListExcelView extends AbstractExcelView {

    private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        final HSSFSheet sheet = workbook.createSheet("生产任务关联码");
        final ScPlan scPlan = (ScPlan) model.get("scPlan");
        final BsProduct bsProduct = (BsProduct) model.get("bsProduct");

        if (null != scPlan && null != bsProduct) {
            globalSetting(scPlan, bsProduct, sheet, response);
            createTitle(model, scPlan, bsProduct, workbook, sheet);
            createHeader(sheet, workbook);
            createContent(model, sheet);
        }
    }

    private void createContent(Map<String, Object> model, final HSSFSheet sheet) {
        final List<FwmRelate> fwmRelates = (List<FwmRelate>) model.get("fwmRelates");
        if (CollectionUtils.isNotEmpty(fwmRelates)) {
            int rowNum = 8;
            for (FwmRelate fwmRelate : fwmRelates) {
                HSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(fwmRelate.getFwmQrcode().getQrcode());

                if (null != fwmRelate.getFwmQrcode() && null != fwmRelate.getFwmQrcode().getUpdateDate()) {
                    row.createCell(1).setCellValue(DateFormatUtils.format(fwmRelate.getFwmQrcode().getUpdateDate(), DATE_PATTERN));
                }

                if (null != fwmRelate.getFwmBoxCode() && StringUtils.isNotBlank(fwmRelate.getFwmBoxCode().getBoxCode())) {
                    row.createCell(2).setCellValue(fwmRelate.getFwmBoxCode().getBoxCode());
                }

                if (null != fwmRelate.getFwmBoxCode() && null != fwmRelate.getFwmBoxCode().getUpdateDate()) {
                    row.createCell(3).setCellValue(DateFormatUtils.format(fwmRelate.getFwmBoxCode().getUpdateDate(), DATE_PATTERN));
                }

                if (null != fwmRelate.getBigboxCode() && StringUtils.isNotBlank(fwmRelate.getBigboxCode().getBigBoxCode())) {
                    row.createCell(4).setCellValue(fwmRelate.getBigboxCode().getBigBoxCode());
                }

                if (null != fwmRelate.getBigboxCode() && null != fwmRelate.getBigboxCode().getUpdateDate()) {
                    row.createCell(5).setCellValue(DateFormatUtils.format(fwmRelate.getBigboxCode().getUpdateDate(), DATE_PATTERN));
                }
            }
        }
    }

    private void globalSetting(ScPlan scPlan, final BsProduct bsProduct, final HSSFSheet sheet, final HttpServletResponse response) throws Exception {
        response.reset();
        response.setContentType("application/x-download");
        String title = "生产任务关联码_" + scPlan.getPlanNo() + "_" + bsProduct.getProdNo() + ".xls";
        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(title.getBytes(), "ISO8859-1"));
        sheet.setColumnWidth(0, (short) (37 * 260));
        sheet.setColumnWidth(1, (short) (37 * 260));
        sheet.setColumnWidth(2, (short) (37 * 260));
        sheet.setColumnWidth(3, (short) (37 * 260));
        sheet.setColumnWidth(4, (short) (37 * 260));
        sheet.setColumnWidth(5, (short) (37 * 260));
    }

    private void createTitle(Map<String, Object> model, ScPlan scPlan, BsProduct bsProduct, HSSFWorkbook workbook, final HSSFSheet sheet) {
        HSSFRow header = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(cra);
        Cell titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 14, CellStyle.ALIGN_CENTER));
        titleCell.setCellValue("生产任务关联码报表");

        cra = new CellRangeAddress(1, 1, 0, 2);
        sheet.addMergedRegion(cra);
        header = sheet.createRow(2);
        cra = new CellRangeAddress(2, 2, 0, 2);
        sheet.addMergedRegion(cra);
        titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
        titleCell.setCellValue("产品名称: " + bsProduct.getProdName());

        header = sheet.createRow(3);
        cra = new CellRangeAddress(3, 3, 0, 2);
        sheet.addMergedRegion(cra);
        titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
        titleCell.setCellValue("生产计划任务号: " + scPlan.getPlanNo());

        header = sheet.createRow(4);
        cra = new CellRangeAddress(4, 4, 0, 2);
        sheet.addMergedRegion(cra);
        titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
        final List<FwmRelate> fwmRelates = (List<FwmRelate>) model.get("fwmRelates");
        titleCell.setCellValue("单品码数量: " + fwmRelates.size());

        header = sheet.createRow(5);
        cra = new CellRangeAddress(5, 5, 0, 2);
        sheet.addMergedRegion(cra);
        titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
        titleCell.setCellValue("报表日期: " + DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));

        sheet.createRow(6);
        cra = new CellRangeAddress(6, 6, 0, 2);
        sheet.addMergedRegion(cra);
    }

    private void createHeader(final HSSFSheet sheet, final HSSFWorkbook workbook) {
        HSSFRow header = sheet.createRow(7);
        Cell headerCell1 = header.createCell(0);
        headerCell1.setCellValue("单品码");
        headerCell1.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell2 = header.createCell(1);
        headerCell2.setCellValue("单品码采集时间");
        headerCell2.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell3 = header.createCell(2);
        headerCell3.setCellValue("一级包装码");
        headerCell3.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell4 = header.createCell(3);
        headerCell4.setCellValue("一级包装时间");
        headerCell4.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell5 = header.createCell(4);
        headerCell5.setCellValue("二级包装码");
        headerCell5.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell6 = header.createCell(5);
        headerCell6.setCellValue("二级包装时间");
        headerCell6.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
    }

    private CellStyle createCellStyle(final HSSFWorkbook workbook, final short borderStyle, final short fontSize, final short align) {
        CellStyle cs = workbook.createCellStyle();
        Font f = workbook.createFont();

        f.setFontHeightInPoints(fontSize);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cs.setFont(f);
        cs.setAlignment(align);
        if (borderStyle > 0) {
            cs.setBorderLeft(borderStyle);
            cs.setBorderRight(borderStyle);
            cs.setBorderTop(borderStyle);
            cs.setBorderBottom(borderStyle);
        }
        return cs;
    }
}
