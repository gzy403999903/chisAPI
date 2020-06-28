package com.chisapp.common.utils;

import io.netty.util.CharsetUtil;
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
        // sheet.setDefaultColumnWidth(15);
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
                    text = new XSSFRichTextString(""); // 如果获取到内容为空则写入空串
                } else {
                    text = new XSSFRichTextString(bodyMap.get(key).toString()); // 将其转成字符串写入表格
                }
                cell.setCellValue(text); // 设置该列内容
                // bodyRow.createCell(cellIndex++).setCellValue(bodyMap.get(key).toString());
            }
        }

        // 设置自动列宽
        for (int i = 0; i < titleMap.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        // 处理中文不能自动调整列宽的问题 (必须在设置完表体内容后执行)
        setSizeColumn(sheet, titleMap.size());

        return workbook;
    }


    // 自适应宽度(中文支持)
    private synchronized static void setSizeColumn(XSSFSheet sheet, int size) {
        // 遍历每一列
        for (int cellIndex = 0; cellIndex < size; cellIndex++){
            // 获取当前单元格宽度
            int cellWidth = sheet.getColumnWidth(cellIndex) / 256;

            // 遍历每行
            for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
                // 获取当前行, 如果当前行未使用则创建该行
                XSSFRow currentRow = sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
                // 获取单元格内容
                XSSFCell currentCell = currentRow.getCell(cellIndex);
                // 如果单元格内容不为空且不为空串
                if (currentCell != null && !currentCell.toString().trim().equals("")) {
                    // 如果为字符串类型则获取其长度
                    /*
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes(CharsetUtil.UTF_8).length;
                    }
                    */
                    // 获取其内容的长度
                    int length = currentCell.getStringCellValue().getBytes(CharsetUtil.UTF_8).length;
                    // 如果单元格宽度小于当前获取内容的长度, 则单元格宽度等于当前获取内容长度
                    if (cellWidth < length) {
                        cellWidth = length;
                    }
                }
            }

            // 设置该列的宽度
            sheet.setColumnWidth(cellIndex, cellWidth * 256);
        }
    }

}
