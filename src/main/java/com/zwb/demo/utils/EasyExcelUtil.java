package com.zwb.demo.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouw
 * @title: EasyExcelUtil
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/3/1316:09
 */
public class EasyExcelUtil {
    public static String excelFilePath = "C:\\Users\\zhouw\\Desktop\\test.xlsx";

    public static void main(String[] args) {
        try {
            writeExcel(excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void writeExcel(String excelFile) throws IOException {
        // 文件输出位置
        OutputStream out = new FileOutputStream(excelFile);
        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        // 动态添加表头，适用一些表头动态变化的场景
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        // 创建一个表格，用于 Sheet 中使用
        Table table1 = new Table(1);
        // 无注解的模式，动态添加表头
        table1.setHead(createTestListStringHead());
        // 写数据
        writer.write1(new ArrayList<>(), sheet1, table1);

        // 动态添加表头，适用一些表头动态变化的场景
        Sheet sheet2 = new Sheet(2, 0);
        sheet2.setSheetName("第2个sheet");
/*
        添加TableStyle属性会使内存OOM，没办法满足分批插入100W条数据
        TableStyle tableStyle = new TableStyle();
        com.alibaba.excel.metadata.Font font = new com.alibaba.excel.metadata.Font();
        font.setBold(true);
        tableStyle.setTableContentFont(font);
        sheet2.setTableStyle(tableStyle);
*/

        // 创建一个表格，用于 Sheet 中使用
        Table table2 = new Table(2);
        // 无注解的模式，动态添加表头
        table2.setHead(createTestListStringHead());
        writer.write1(new ArrayList<>(), sheet2, table2);

        int x = 0;
        while (x < 1000000) {
// 模拟分批写入数据到excel，每次写入100条
            System.out.println("x = " + x);
            Table tableX = new Table(1);

// 每次从sheet的第几行开始写入
            sheet1.setStartRow(x);
            writer.write1(createDynamicModelList(x), sheet1, tableX);

            Table tableX2 = new Table(1);
            sheet2.setStartRow(x);
            writer.write1(createDynamicModelList(x), sheet2, tableX2);

            x = x + 100;
        }
        // 将上下文中的最终 outputStream 写入到指定文件中
        writer.finish();
        // 关闭流
        out.close();
    }

    private static List<List<Object>> createDynamicModelList(int x) {
        List<List<Object>> rows = new ArrayList<>();
        for (int i= x; i < 100 + x; i++){
            List<Object> row = new ArrayList<>();
            row.add("字符串-" + i);
            row.add(Long.valueOf(187837834L) + i);
            row.add(Integer.valueOf(2233 + i));
            row.add("宁-" + i);
            row.add("微信公众号： demo");
            rows.add(row);
        }

        return rows;
    }

    private static List<List<String>> createTestListStringHead() {
        // 模型上没有注解，表头数据动态传入
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        List<String> headCoulumn3 = new ArrayList<String>();
        List<String> headCoulumn4 = new ArrayList<String>();
        List<String> headCoulumn5 = new ArrayList<String>();
        headCoulumn1.add("第1列");
        headCoulumn2.add("第2列");
        headCoulumn3.add("第3列");
        headCoulumn4.add("第4列");
        headCoulumn5.add("第5列");


        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        head.add(headCoulumn4);
        head.add(headCoulumn5);
        return head;
    }
}
