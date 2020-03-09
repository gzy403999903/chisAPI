package com.chisapp.common.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/14 11:27
 * @Version 1.0
 */
public class ExcelFileUtils {

    /**
     * 导出 xslx 格式的 excel 文件
     * @param titleMap 表头, key 为 与 bodyList Map 中 key 必须一致, value 为 表头的中文名称
     * @param bodyList 表体
     * @return
     */
    public synchronized static XSSFWorkbook createXSSFWorkbook (Map<String, String> titleMap, List<Map<String, Object>> bodyList) {
        if (bodyList == null || bodyList.isEmpty()) {
            return null;
        }

        // 创建 Excel 文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        // 设置表格默认宽度
        sheet.setDefaultColumnWidth(15);
        // 设置标题框样式
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        titleCellStyle.setBorderLeft(BorderStyle.THIN); // 左边框
        titleCellStyle.setBorderTop(BorderStyle.THIN); // 上边框
        titleCellStyle.setBorderRight(BorderStyle.THIN); // 右边框
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中

        XSSFFont titleFont = workbook.createFont();
        // titleFont.setFontName("黑体"); // 字体样式
        // titleFont.setFontHeightInPoints((short) 10); // 字体大小
        titleFont.setBold(true); // 是否加粗
        titleCellStyle.setFont(titleFont);

        // 设置表体框样式
        XSSFCellStyle bodyCellStyle = workbook.createCellStyle();
        bodyCellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        bodyCellStyle.setBorderLeft(BorderStyle.THIN); // 左边框
        bodyCellStyle.setBorderTop(BorderStyle.THIN); // 上边框
        bodyCellStyle.setBorderRight(BorderStyle.THIN); // 右边框

        // 当前行号
        int rowNum = 0;

        // 创建表头
        XSSFRow titleRow = sheet.createRow(rowNum);
        int titleCellIndex = 0;
        for (String key : titleMap.keySet()) {
            XSSFCell cell = titleRow.createCell(titleCellIndex++); // 创建一列
            cell.setCellStyle(titleCellStyle); // 设置该列的样式
            XSSFRichTextString text = new XSSFRichTextString(titleMap.get(key)); // 将该列的标题转成字符串
            cell.setCellValue(text); // 设置该列内容
        }

        // 创建表体
        for (Map<String, Object> bodyMap : bodyList) {
            rowNum++;
            XSSFRow bodyRow = sheet.createRow(rowNum); // 创建一行
            int bodyCellIndex = 0;
            for (String key : titleMap.keySet()) { // 获取 titleMap 对应的 key, 该 key 必须和 bodyList 每个 map 的 key 一致
                XSSFCell cell = bodyRow.createCell(bodyCellIndex++); // 创建一列
                cell.setCellStyle(bodyCellStyle); // 设置该列样式
                XSSFRichTextString text;
                if (bodyMap.get(key) == null) {
                    text = new XSSFRichTextString(""); // 将该列的标题转成字符串
                } else {
                    text = new XSSFRichTextString(bodyMap.get(key).toString()); // 将该列的标题转成字符串
                }
                cell.setCellValue(text); // 设置该列内容
                // bodyRow.createCell(cellIndex++).setCellValue(bodyMap.get(key).toString());
            }
        }

        return workbook;
    }

}
