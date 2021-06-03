package com.zwb.demo.utils;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.ImageUtils;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * @author zhouw
 * @title: ExcelUtil
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/2/2516:42
 */
public class ExcelUtil {
    public static void main(String[] args) {
        List<String> list = Arrays.asList(
                "C:\\Users\\zhouw\\Desktop\\1.xlsx",
                "C:\\Users\\zhouw\\Desktop\\2.xlsx"
        );
        String dirPath = "C:\\Users\\zhouw\\Desktop";
        String str = "{\"\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"SerialKey\": \"be308cdf-fbf3-d6dd-5e05-393010a0abca\",\n" +
                "      \"SystemType\": \"01\",\n" +
                "      \"OrderClassCode\": \"10\",\n" +
                "      \"OrderClassName\": \"零售单\",\n" +
                "      \"OrderTypeCode\": \"30\",\n" +
                "      \"OrderTypeName\": \"小样\",\n" +
                "      \"ProductClassCode\": \"610\",\n" +
                "      \"ProductClassName\": \"小样选型\",\n" +
                "      \"ServiceNum\": \"OPLCC襄汾县210300004CA\",\n" +
                "      \"ServiceOrderNum\": \"F2101090001972\",\n" +
                "      \"ContractNum\": \"OPLCC襄汾县210300004CA\",\n" +
                "      \"OrderNum\": \"176943269\",\n" +
                "      \"MTDSNum\": \"JS00026821031400001\",\n" +
                "      \"OrderSelfNum\": \"OPLCC襄汾县210300004CA1-1小样\",\n" +
                "      \"DealerCode\": \"S000268\",\n" +
                "      \"DueDate\": \"2021-03-24T15:14:42\",\n" +
                "      \"MatingCode\": \"A\",\n" +
                "      \"FirstClassCode\": \"10\",\n" +
                "      \"FirstClassName\": \"橱柜\",\n" +
                "      \"LeaveBehind\": false,\n" +
                "      \"StatusCode\": \"1024\",\n" +
                "      \"BrandCode\": \"10\",\n" +
                "      \"BrandName\": \"欧派\",\n" +
                "      \"LineCode\": \"GC-XB188\",\n" +
                "      \"LineName\": \"E6-1\",\n" +
                "      \"Insurance\": false,\n" +
                "      \"ProjectName\": null,\n" +
                "      \"SaleDepartmentName\": \"CG-JXB027\",\n" +
                "      \"CreateTime\": \"2021-03-24T18:25:24\",\n" +
                "      \"DeskDueDate\": \"0001-01-01T00:00:00\",\n" +
                "      \"LivePurchaseNum\": \"\",\n" +
                "      \"LivePurchaseLine\": \"\",\n" +
                "      \"LiveProjectNum\": \"\",\n" +
                "      \"reqdate\": \"2021-04-10T00:00:00\",\n" +
                "      \"StoreNo\": \"LS00026800002\",\n" +
                "      \"StoreName\": \"襄汾橱柜店\",\n" +
                "      \"CusName\": \"襄汾橱柜店\",\n" +
                "      \"CusPhone\": \"18634792111\",\n" +
                "      \"CusAddress\": \"襄汾县0000w海建材城欧派橱柜店\",\n" +
                "      \"Receiver\": \"丁志荣\",\n" +
                "      \"ReceiverProvince\": \"山西省\",\n" +
                "      \"ReceiverCity\": \"临汾市\",\n" +
                "      \"ReceiverAddress\": \"中国山西省临汾市襄汾县新城镇瑆海建材城欧派橱柜\",\n" +
                "      \"SpecialOrder\": null,\n" +
                "      \"Channel\": \"欧派零售\",\n" +
                "      \"KitStrategy\": \"002\",\n" +
                "      \"Details\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"MsgKeyID\": \"be308cdf-fbf3-d6dd-5e05-393010a0abca\",\n" +
                "  \"ApiName\": \"MSCSMainOrderOverview\",\n" +
                "  \"optionType\": \"ADD\"\n" +
                "}";
        System.out.println(str.replace("{\"", "{").trim());
        String fileName = "merge" + System.currentTimeMillis() + ".xlsx";
//        mergexcel(list, fileName, dirPath);
//        File file = new File(dirPath + "\\" + fileName);
//        file.delete();
        System.out.println("OJBK");
    }

    /**
     * * 合并多个ExcelSheet
     *
     * @param files     文件字符串(file.toString)集合,按顺序进行合并，合并的Excel中Sheet名称不可重复
     * @param excelName 合并后Excel名称(包含后缀.xslx)
     * @param dirPath   存储目录
     * @return
     * @Date: 2020/9/18 15:31
     */
    public static void mergexcel(List<String> files, String excelName, String dirPath) {
        SXSSFWorkbook newExcelCreat = new SXSSFWorkbook();
        // 遍历每个源excel文件，TmpList为源文件的名称集合
        for (String fromExcelName : files) {
            try (InputStream in = new FileInputStream(fromExcelName)) {
                XSSFWorkbook fromExcelXs = new XSSFWorkbook(in);
                SXSSFWorkbook fromExcelSx = new SXSSFWorkbook(fromExcelXs);
                XSSFWorkbook fromExcel = fromExcelSx.getXSSFWorkbook();
                int length = fromExcel.getNumberOfSheets();
                /**
                 * // 遍历每个sheet
                 */
                for (int i = 0; i < length; i++) {
                    XSSFSheet oldSheet = fromExcel.getSheetAt(i);
                    File file = new File(fromExcelName);
                    SXSSFSheet newSheet = newExcelCreat.createSheet(file.getName().replace(".xlsx", "") + i);
                    copySheet(newExcelCreat, oldSheet, newSheet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 定义新生成的xlxs表格文件
        String allFileName = dirPath + File.separator + excelName;
        try (FileOutputStream fileOut = new FileOutputStream(allFileName)) {
            newExcelCreat.write(fileOut);
            fileOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                newExcelCreat.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并单元格
     *
     * @param fromSheet
     * @param toSheet
     */
    private static void mergeSheetAllRegion(XSSFSheet fromSheet, SXSSFSheet toSheet) {
        int num = fromSheet.getNumMergedRegions();
        CellRangeAddress cellR = null;
        for (int i = 0; i < num; i++) {
            cellR = fromSheet.getMergedRegion(i);
            toSheet.addMergedRegion(cellR);
        }
    }

    /**
     * 复制单元格
     *
     * @param wb
     * @param fromCell
     * @param toCell
     */
    private static void copyCell(SXSSFWorkbook wb, XSSFCell fromCell, SXSSFCell toCell) {
        CellStyle newstyle = wb.createCellStyle();
        // 复制单元格样式
        newstyle.cloneStyleFrom(fromCell.getCellStyle());
        // 样式
        toCell.setCellStyle(newstyle);
        if (fromCell.getCellComment() != null) {
            toCell.setCellComment(fromCell.getCellComment());
        }
        // 不同数据类型处理

        CellType fromCellType = fromCell.getCellTypeEnum();
        toCell.setCellType(fromCellType);
        if (fromCellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(fromCell)) {

            } else {
                toCell.setCellValue(fromCell.getNumericCellValue());
            }
        } else if (fromCellType == CellType.STRING) {
            toCell.setCellValue(fromCell.getRichStringCellValue());
        } else if (fromCellType == CellType.BLANK) {
            // nothing21
        } else if (fromCellType == CellType.BOOLEAN) {
            toCell.setCellValue(fromCell.getBooleanCellValue());
        } else if (fromCellType == CellType.ERROR) {
            toCell.setCellErrorValue(fromCell.getErrorCellValue());
        } else if (fromCellType == CellType.FORMULA) {
            toCell.setCellFormula(fromCell.getCellFormula());
        } else {
            // nothing29
        }
    }

    /**
     * 行复制功能
     *
     * @param wb
     * @param oldRow
     * @param toRow
     */
    private static void copyRow(SXSSFWorkbook wb, XSSFRow oldRow, SXSSFRow toRow) {
        toRow.setHeight(oldRow.getHeight());
        int cellNum = 0;
        for (Iterator cellIt = oldRow.cellIterator(); cellIt.hasNext(); ) {
            XSSFCell tmpCell = (XSSFCell) cellIt.next();
            SXSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
            System.out.println("行复制功能" + cellNum);
            cellNum++;
            copyCell(wb, tmpCell, newCell);
        }
    }

    /**
     * Sheet复制
     *
     * @param wb
     * @param fromSheet
     * @param toSheet
     */
    private static void copySheet(SXSSFWorkbook wb, XSSFSheet fromSheet, SXSSFSheet toSheet) {
        mergeSheetAllRegion(fromSheet, toSheet);
        Map pictureMap = getPicture(fromSheet);
        // 设置列宽
        int length = fromSheet.getRow(fromSheet.getFirstRowNum()).getLastCellNum();
        for (int i = 0; i <= length; i++) {
            toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
        }
        int rowNum = 0;
        for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext(); ) {
            System.out.println("rowNum " + rowNum);
            rowNum++;
            XSSFRow oldRow = (XSSFRow) rowIt.next();
            SXSSFRow newRow = toSheet.createRow(oldRow.getRowNum());
            copyRow(wb, oldRow, newRow);
        }
        writeImageExcel(wb, pictureMap, toSheet);
    }

    /**
     * 读取excel的图片
     *
     * @param xssfSheet
     * @return
     */
    private static Map getPicture(XSSFSheet xssfSheet) {
        Map<String, Map> map = new HashMap<>();
        if (xssfSheet.getDrawingPatriarch() == null) {
            return map;
        }
        List<XSSFShape> list = xssfSheet.getDrawingPatriarch().getShapes();
        for (XSSFShape shape : list) {
            if (shape instanceof XSSFPicture) {
                XSSFPicture xssfPicture = (XSSFPicture) shape;
                XSSFClientAnchor xssfClientAnchor = (XSSFClientAnchor) xssfPicture.getAnchor();
                /**
                 * 获取图片的data
                 */
                XSSFPictureData xssfPictureData = xssfPicture.getPictureData();
                String key = xssfClientAnchor.getRow1() + "," + xssfClientAnchor.getCol1();
                System.out.println("key=" + key);
                Map pDataMap = new HashMap();
                pDataMap.put("xssfPictureData", xssfPictureData);

                pDataMap.put("c1", xssfClientAnchor.getCol1());
                pDataMap.put("c2", xssfClientAnchor.getCol2());
                pDataMap.put("r1", xssfClientAnchor.getRow1());
                pDataMap.put("r2", xssfClientAnchor.getRow2());
                pDataMap.put("dx1", xssfClientAnchor.getDx1());
                pDataMap.put("dx2", xssfClientAnchor.getDx2());
                pDataMap.put("dy1", xssfClientAnchor.getDy1());
                pDataMap.put("dy2", xssfClientAnchor.getDy2());
                map.put(key, pDataMap);
            }
        }
        return map;
    }

    /**
     * 写图片到excel中
     *
     * @param wb
     * @param map
     * @param sxssfSheet
     */
    private static void writeImageExcel(SXSSFWorkbook wb, Map<String, Map> map, SXSSFSheet sxssfSheet) {
        map.forEach((k, v) -> {
            SXSSFDrawing patri_item = sxssfSheet.createDrawingPatriarch();
            int dx1 = java.lang.Integer.parseInt(v.get("dx1").toString());
            int dx2 = java.lang.Integer.parseInt(v.get("dx2").toString());
            int dy1 = java.lang.Integer.parseInt(v.get("dy1").toString());
            int dy2 = java.lang.Integer.parseInt(v.get("dy2").toString());
            int c1 = java.lang.Integer.parseInt(v.get("c1").toString());
            int r1 = java.lang.Integer.parseInt(v.get("r1").toString());
            int c2 = java.lang.Integer.parseInt(v.get("c2").toString());
            int r2 = java.lang.Integer.parseInt(v.get("r2").toString());
            XSSFClientAnchor anchor_item = new XSSFClientAnchor(dx1, dy1, dx2, dy2, c1, r1, c2, r2);
            XSSFPictureData xssfPictureData = (XSSFPictureData) v.get("xssfPictureData");
            patri_item.createPicture(anchor_item, wb.addPicture(xssfPictureData.getData(), SXSSFWorkbook.PICTURE_TYPE_JPEG));
        });
    }
}
