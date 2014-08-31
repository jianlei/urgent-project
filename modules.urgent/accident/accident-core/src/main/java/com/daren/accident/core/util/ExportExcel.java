package com.daren.accident.core.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @类描述：导出Excel
 * @创建人：xukexin
 * @创建时间：2014/8/30 13:31
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ExportExcel {

    private HSSFWorkbook wb = null;

    /**
     * @param wb
     */
    public ExportExcel(HSSFWorkbook wb) {
        super();
        this.wb = wb;
        //this.sheet = sheet;
    }

    /**
     * @return the wb
     */
    public HSSFWorkbook getWb() {
        return wb;
    }

    /**
     * @param wb
     *            the wb to set
     */
    public void setWb(HSSFWorkbook wb) {
        this.wb = wb;
    }

    /**
     * 创建通用EXCEL头部
     *
     * @param headString
     *            头部显示的字符
     * @param colSum
     *            该报表的列数
     */
    public void createNormalHead(String headString, int colSum, HSSFSheet sheet) {

        HSSFRow row = sheet.createRow(0);

        // 设置第一行
        HSSFCell cell = row.createCell(0);
        row.setHeight((short) 400);

        // 定义单元格为字符串类型
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(new HSSFRichTextString(headString));

        // 指定合并区域
        sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) colSum));

        HSSFCellStyle cellStyle = wb.createCellStyle();

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行
        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 300);
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setColor(HSSFFont.COLOR_RED); //字体颜色
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);
    }

    /**
     * 创建通用报表第二行
     *
     * @param params
     *            统计条件数组
     * @param colSum
     *            需要合并到的列索引
     */
    public void createNormalTwoRow(String[] params, int colSum, HSSFSheet sheet) {
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 300);

        HSSFCell cell2 = row1.createCell(0);

        cell2.setCellType(HSSFCell.ENCODING_UTF_16);
        cell2.setCellValue(new HSSFRichTextString("统计时间：" + params[0] + "至"
                + params[1]));

        // 指定合并区域
        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) colSum));

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        //cellStyle.setWrapText(true);// 指定单元格自动换行

        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        cell2.setCellStyle(cellStyle);
    }

    /**
     * 设置报表标题
     *
     * @param columHeader
     *            标题字符串数组
     */
    public void createColumHeader(String[] columHeader, HSSFSheet sheet) {

        // 设置列头
        HSSFRow row2 = sheet.createRow(2);

        // 指定行高
        row2.setHeight((short) 600);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行

        // 单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        /*
         *
         * cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
         *
         *
         *
         * cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
         * cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
         * cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
         */

        // 设置单元格背景色
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCell cell3 = null;

        for (int i = 0; i < columHeader.length; i++) {
            cell3 = row2.createCell(i);
            cell3.setCellType(HSSFCell.ENCODING_UTF_16);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue(new HSSFRichTextString(columHeader[i]));
        }

    }

    /**
     * 创建内容单元格
     *
     * @param wb
     *            HSSFWorkbook
     * @param row
     *            HSSFRow
     * @param col
     *            short型的列索引
     * @param align
     *            对齐方式
     * @param val
     *            列值
     */
    public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, short align,
                           String val[]) {
        for(int i=0;i<col;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(new HSSFRichTextString(val[i]));
            HSSFCellStyle cellstyle = wb.createCellStyle();
            cellstyle.setAlignment(align);
            cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
            cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cell.setCellStyle(cellstyle);
        }
    }

    /**
     * 创建合计行
     *
     * @param colSum
     *            需要合并到的列索引
     * @param cellValue
     */
    public void createLastSumRow(int colSum, String[] cellValue, HSSFSheet sheet) {

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行

        // 单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
        HSSFCell sumCell = lastRow.createCell(0);

        sumCell.setCellValue(new HSSFRichTextString("合计"));
        sumCell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new Region(sheet.getLastRowNum(), (short) 0,
                sheet.getLastRowNum(), (short) colSum));// 指定合并区域

        for (int i = 2; i < (cellValue.length + 2); i++) {
            sumCell = lastRow.createCell(i);
            sumCell.setCellStyle(cellStyle);
            sumCell.setCellValue(new HSSFRichTextString(cellValue[i - 2]));

        }

    }

    /**
     * 输入EXCEL文件
     */
    public InputStream outputExcel() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            wb.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] ba = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        return bais;
    }


}
