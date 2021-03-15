package com.zwb.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.CellColorSheetWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

import java.io.*;
import java.util.*;

public class EasyExcelTest {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream outputByteOutputStream = new ByteArrayOutputStream();
        String templateFileName = "D:/Grea/projects/oppo/oppo-code/config/excel/template/consignment.xlsx";
        OutputStream outputStream = new FileOutputStream("D:/" + System.currentTimeMillis() + ".xlsx");

        Map headerMap = new HashMap();
        headerMap.put("carrierName", "承运商");
        headerMap.put("customerName", "客户");
        headerMap.put("shipmentDate", "2020202");
        // headerMap.put("orderVehicleNo", "JLDT0919-001-1");
        headerMap.put("contacts", "谢恒珍");
        headerMap.put("phone", "1333333333");
        headerMap.put("loadAddress", "新成品仓库");
        headerMap.put("companyAddress", "东莞市长安镇乌沙环南路一街1号厂房");
        // 口岸
        headerMap.put("exportPort", "口岸");
        headerMap.put("driverName", "driverName");
        headerMap.put("driverHkPhone", "driverHkPhone");
        headerMap.put("driverPhone", "driverPhone");
        headerMap.put("hkVehicleNo", "hkVehicleNo");
        headerMap.put("vehicleNo", "vehicleNo");
        headerMap.put("tonnage", "tonnage");
        headerMap.put("orderTonnage", "orderTonnage");
        headerMap.put("vehicleWeight", "vehicleWeight");
//        excelWriter.finish();

        List rowColumnArray = new ArrayList();
        int rowIndex = 17;
        List dataList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            Map listMap = new HashMap();
            listMap.put("country", "中国");
            listMap.put("contractNo", "DME-20090180");
            listMap.put("qty", 250);
            listMap.put("kgs", 1532.5);
            listMap.put("dest", "佳宏物流C仓");
            listMap.put("detail", "智能手机RMX2185 6400 PCS智能手机RMX2030 1600 PCS;");
            dataList.add(listMap);
            for (int j = 1; j <= listMap.size(); j++) {
                if (j == 0 || j == 1 || j == 4 || j == 5) {
                    Map rowColumnMap = new HashMap();
                    rowColumnMap.put("rowIndex", rowIndex);
                    rowColumnMap.put("columnIndex", j+1);
                    rowColumnMap.put("rowHeight", (int)(listMap.get("detail").toString().length() / 10) * 276);
                    rowColumnArray.add(rowColumnMap);
                }
            }
            rowIndex++;
        }
        ExcelWriter excelWriter =EasyExcel.write(outputStream)
                .withTemplate(templateFileName)
                .registerWriteHandler(new CellColorSheetWriteHandler(rowColumnArray))
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
//        EasyExcel.write(outputByteOutputStream)
//                .withTemplate(templateFileName)
//                .registerWriteHandler(new CellColorSheetWriteHandler(rowColumnArray))
//                .build();
        // 头部信息
        excelWriter.fill(headerMap, writeSheet);
        // 明细信息
        FillConfig fillConfig = FillConfig.builder().forceNewRow(java.lang.Boolean.TRUE).build();
        excelWriter.fill(new FillWrapper("line", dataList), fillConfig, writeSheet);
//        excelWriter.fill(dataList, writeSheet);
        // 统计信息
        Map totalMap = new HashMap();
        totalMap.put("totalQty", 271);
        totalMap.put("totalKgs", 5524.7);
        excelWriter.fill(totalMap, writeSheet);
        //
        Map map = new HashMap();
        FileInputStream fis = new FileInputStream("D:/Grea/projects/oppo/oppo-code/config/excel/template/gaizhang.jpg");
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
//        StreamUtil.copyStream(fis, byteArrayOutputStream1);
//
//        map.put("byteArray", byteArrayOutputStream1.toByteArray());
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();

    }
}
