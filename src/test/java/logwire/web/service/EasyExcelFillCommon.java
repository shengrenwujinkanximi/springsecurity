package logwire.web.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.CellColorSheetWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

import java.io.*;
import java.util.*;

public class EasyExcelFillCommon {
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
    public static void commonLineFill(ExcelWriter excelWriter, WriteSheet writeSheet, String wrapperName, List dataList, String type) {

        if (wrapperName == "none") {
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(dataList, fillConfig, writeSheet);
        } else {
            if (type == "insert") {
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                excelWriter.fill(new FillWrapper(wrapperName, dataList), fillConfig, writeSheet);
            } else {
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
                excelWriter.fill(new FillWrapper(wrapperName, dataList), fillConfig, writeSheet);
            }
        }
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

    /**
     * 返会writeSheet
     *
     * @return
     */
    public static WriteSheet writeSheetCommon() {
        return EasyExcel.writerSheet().build();
    }

    public static void FillData(String tmpFileName, String templateFileName, String type, List dataList) {
        ExcelWriter excelWriter = EasyExcel.write(tmpFileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(dataList, fillConfig, writeSheet);
        excelWriter.finish();
    }

    public static void main(String[] args) throws IOException {
        String tmpFilePath = "C:\\Users\\zhouw\\Desktop\\" + java.util.UUID.randomUUID() + ".xlsx";
        File file = new File(tmpFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
//        FileOutputStream fos = new FileOutputStream(tmpFilePath);
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String templatePlate = "C:\\Users\\zhouw\\Desktop\\plDetailDe.xlsx";
//        ExcelWriter excelWriter = excelWriterCommon(fos, templatePlate, null, "normal");
//        WriteSheet writeSheet = writeSheetCommon();
        Map headerMap = new HashMap();
        headerMap.put("test_head", "1122222");
        List dataLineList1 = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("item_package_count", 7360);
            dataLineList1.add(map);
        }
        FillData(tmpFilePath, templatePlate, "", dataLineList1);
//        EasyExcelFillCommon.commonHeadFill(excelWriter,  writeSheet, headerMap);
//        EasyExcelFillCommon.commonLineFill(excelWriter, writeSheet, "none", dataLineList1, "type");
//        EasyExcelFillCommon.finish(excelWriter);
//        System.out.println("大小：" + bos.size());
//        List list = new ArrayList();
//        for (int i=0;i<10;i++) {
//            Map map = new HashMap();
//            map.put("item_package_count", "1222");
//            list.add(map);
//        }
//        FillData ("D:/" + UUID.randomUUID() + ".xlsx","C:\\Users\\zhouw\\Desktop\\plDetailDe.xlsx", "", list);
    }
}
