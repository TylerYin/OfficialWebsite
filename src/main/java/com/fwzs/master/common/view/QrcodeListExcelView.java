package com.fwzs.master.common.view;

import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.entity.Qrcode2BoxcodeMapping;
import org.apache.commons.collections.CollectionUtils;
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
@Component(value = "qrCodeExcel")
public class QrcodeListExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        final HSSFSheet sheet = workbook.createSheet("单品码报表");
        globalSetting(model, sheet, response);
        createTitle(model, workbook, sheet);
        createHeader(sheet, workbook);
        createContent(model, sheet);
    }

    private void createContent(Map<String, Object> model, final HSSFSheet sheet) {
        final List<Qrcode2BoxcodeMapping> qrCodeList = (List<Qrcode2BoxcodeMapping>) model.get("qrCodeList");
        if (CollectionUtils.isNotEmpty(qrCodeList)) {
            int rowNum = 8;
            for (Qrcode2BoxcodeMapping qrcode2BoxcodeMapping : qrCodeList) {
                HSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(qrcode2BoxcodeMapping.getQrCode());
                if (!StringUtils.equals("1", qrcode2BoxcodeMapping.getBoxCode())) {
                    row.createCell(1).setCellValue(qrcode2BoxcodeMapping.getBoxCode());
                }

                if (!StringUtils.equals("1", qrcode2BoxcodeMapping.getBigBoxCode())) {
                    row.createCell(2).setCellValue(qrcode2BoxcodeMapping.getBigBoxCode());
                }
            }
        }
    }

    private void globalSetting(Map<String, Object> model, final HSSFSheet sheet, final HttpServletResponse response) throws Exception {
        response.reset();
        response.setContentType("application/x-download");

        String title;
        if (null != model.get("boundNo")) {
            title = "单品码报表_" + model.get("boundNo").toString() + ".xls";
        } else {
            title = "单品码报表_" + model.get("scPlanNo").toString() + ".xls";
        }

        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(title.getBytes(), "ISO8859-1"));

        sheet.setColumnWidth(0, (short) (37 * 260));
        sheet.setColumnWidth(1, (short) (37 * 260));
        sheet.setColumnWidth(2, (short) (37 * 260));
    }

    private void createTitle(Map<String, Object> model, HSSFWorkbook workbook, final HSSFSheet sheet) {
        HSSFRow header = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(cra);
        Cell titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 14, CellStyle.ALIGN_CENTER));
        titleCell.setCellValue("单品码报表");

        cra = new CellRangeAddress(1, 1, 0, 2);
        sheet.addMergedRegion(cra);
        if (null != model.get("productName")) {
            header = sheet.createRow(2);
            cra = new CellRangeAddress(2, 2, 0, 2);
            sheet.addMergedRegion(cra);
            titleCell = header.createCell(0);
            titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
            titleCell.setCellValue("产品名称: " + model.get("productName"));
        }

        if (null != model.get("scPlanNo") || null != model.get("boundNo")) {
            header = sheet.createRow(3);
            cra = new CellRangeAddress(3, 3, 0, 2);
            sheet.addMergedRegion(cra);
            titleCell = header.createCell(0);
            titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
            if (null != model.get("scPlanNo")) {
                titleCell.setCellValue("生产计划任务号: " + model.get("scPlanNo"));
            } else {
                titleCell.setCellValue("单号: " + model.get("boundNo"));
            }
        }

        header = sheet.createRow(4);
        cra = new CellRangeAddress(4, 4, 0, 2);
        sheet.addMergedRegion(cra);
        titleCell = header.createCell(0);
        titleCell.setCellStyle(createCellStyle(workbook, (short) 0, (short) 12, CellStyle.ALIGN_LEFT));
        final List<Qrcode2BoxcodeMapping> qrCodeList = (List<Qrcode2BoxcodeMapping>) model.get("qrCodeList");
        if (CollectionUtils.isNotEmpty(qrCodeList)) {
            titleCell.setCellValue("单品码数量: " + qrCodeList.size());
        } else {
            titleCell.setCellValue("单品码数量: 0");
        }

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
        headerCell1.setCellValue("一级单品码");
        headerCell1.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell2 = header.createCell(1);
        headerCell2.setCellValue("二级包装码");
        headerCell2.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
        Cell headerCell3 = header.createCell(2);
        headerCell3.setCellValue("三级包装码");
        headerCell3.setCellStyle(createCellStyle(workbook, CellStyle.BORDER_THIN, (short) 11, CellStyle.ALIGN_CENTER));
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
