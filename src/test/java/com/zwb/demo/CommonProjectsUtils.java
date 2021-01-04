package com.zwb.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.CellColorSheetWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class CommonProjectsUtils {
    /**
     * 填充头部信息
     *
     * @param excelWriter
     * @param writeSheet
     * @param headerMap
     */
    public static void commonHeadFill(ExcelWriter excelWriter, WriteSheet writeSheet, Map headerMap) {
        excelWriter.fill(headerMap, writeSheet);
    }

    /**
     * 填充明细
     *
     * @param excelWriter
     * @param writeSheet
     * @param wrapperName
     * @param dataList
     * @param type
     */
    public static void commonLineFill(ExcelWriter excelWriter, WriteSheet writeSheet, String wrapperName, List dataList, Boolean type) {
        FillConfig fillConfig = FillConfig.builder().forceNewRow(type).build();
        excelWriter.fill(new FillWrapper(wrapperName, dataList), fillConfig, writeSheet);
    }

    /**
     * ExcelWriter实例
     *
     * @param outputStream
     * @param templateFileName
     * @param rowColumnArray
     * @param type
     * @return
     */
    public static ExcelWriter excelWriterCommon(OutputStream outputStream, String templateFileName, List rowColumnArray, String type) {
        if (type == "normal") {
            ExcelWriter excelWriter = EasyExcel.write(outputStream)
                    .withTemplate(templateFileName)
                    .build();
            return excelWriter;
        } else {
            ExcelWriter excelWriter = EasyExcel.write(outputStream)
                    .withTemplate(templateFileName)
                    .registerWriteHandler(new CellColorSheetWriteHandler(rowColumnArray))
                    .build();
            return excelWriter;
        }
    }

    /**
     * 关闭excelWriter
     *
     * @param excelWriter
     */
    public static void finish(ExcelWriter excelWriter) {
        excelWriter.finish();
    }

}
