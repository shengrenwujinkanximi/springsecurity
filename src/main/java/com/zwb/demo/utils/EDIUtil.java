package com.zwb.demo.utils;

import java.io.*;

/**
 * @author zhouw
 * @title: EDIUtil
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/3/270:39
 */
public class EDIUtil {
    public static void main(String[] args) {
        File file = new java.io.File("C:\\Users\\zhouw\\Desktop\\oppo-code");
        if (file.isDirectory()) {
            for (File readFile : file.listFiles()) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new java.io.BufferedReader(new FileReader(readFile));
                    String str = null;
                    int len = 0;
                    while ((str = bufferedReader.readLine()) != null) {
                        String contents = str;
                        System.out.println("--------" + contents);
                        len++;
                        /**
                         * 解析数据
                         */
                    }
                    System.out.println("行数====" + len);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    } catch (Exception e) {

                    }
                }
//                readFile.delete();
            }
        }
    }
    public static String value(String str) {
        String value = "";

        return value;
    }
}
