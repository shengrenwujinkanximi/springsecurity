package com.zwb.demo;

import jodd.http.HttpRequest;
import logwire.web.service.ActionContext;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class test {
    static void sort1() {
        int K = 44;
        List<Integer> inputs = Arrays.asList(19, 23, 41, 5, 40, 36);

        int opt = 0;                // optimal solution so far

        Set<Integer> sums = new HashSet<>();
        sums.add(opt);

        // loop over all input values
        for (Integer input : inputs) {
            Set<Integer> newSums = new HashSet<>();

            // loop over all sums so far
            for (Integer sum : sums) {
                int newSum = sum + input;

                // ignore too big sums
                if (newSum <= K) {
                    newSums.add(newSum);

                    // update optimum
                    if (newSum > opt) {
                        opt = newSum;
                    }
                }
            }

            sums.addAll(newSums);
        }

        System.out.println(opt);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 120; i++) {
            System.out.println(String.format("%02d", i));
        }
        MultipartResolver multipartResolver = new StandardServletMultipartResolver();
        HttpServletRequest httpServletRequest = null;
        MultipartHttpServletRequest multipartHttpServletRequest = multipartResolver.resolveMultipart(httpServletRequest);
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        fileMap.forEach((k,v)->{
            System.out.println(v.getName());
        });
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
        while(fileNames.hasNext()){
            String next = fileNames.next();

        }
//        for (int i = 0; i < 10; i++) {
//            if (i > 5) {
//                continue;
//            }
//            System.out.println(i);
//        }
//        sort1 ();
//        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 22, 23, 28};
//        //结果值
//        Integer num = 13;
//        List strList = new ArrayList();
//        for (int i = 0; i < arr.length; i++) {
//            //获取当前循环第I个参数
//            int all = arr[i];
//            //声明拼接
//            String str = arr[i] + "";
//            //从后往前遍历
//            for (int j = arr.length - 1; j > i; j--) {
//                //参数大于规定数值
//                if (arr[j] > num) {
//                    continue;
//                }
//                //两个参数相加大于规定数值
//                if ((all + arr[j]) > num) {
//                    //将all置为初始状态
//                    all = arr[i];
//                    str = arr[i] + "";
//                    continue;
//                }
//                //参数相加
//                all += arr[j];
//                str += "-" + arr[j];
//                if (all == num) {
//                    strList.add(str);
//                    //将all置为初始状态
//                    all = arr[i];
//                    str = arr[i] + "";
//                }
//            }
//        }
//        //结果 [1-5-4-3, 2-11, 2-6-5, 3-10, 4-9, 5-8, 6-7]
//        System.out.println(strList);
//        List list = new ArrayList();
//        for (int i = 0; i < 10; i++) {
//            list.add(i);
//        }
//        System.out.println(list.subList(5,7));
//        System.out.println(list.subList(0,5));
//        String str1 = "DE001";
//        String str2 = "DE002";
//        System.out.println(str1.compareToIgnoreCase(str1));
//        Object o = Class.forName("com.situ.super.Sclass").newInstance();  //为了防止编译器报错，先用o声明，并一直使用Class.forName来获取类文件
//        Method getter = o.getClass().getMethod("getName");
//        System.out.println(getter.invoke(o));
//
//        // 调用其setName方法
//        Method setter = o.getClass().getMethod("setName", new Class[] {String.class});
//        setter.invoke(o, "Adam");
//        System.out.println(getter.invoke(o));
//        String str = "jekk111";
//        System.out.println(str.toLowerCase());
//        HashMap map = new HashMap<>();
//        System.out.println("判断是否是同一类型：" + (map instanceof java.util.Map));
//        com.aliyun.openservices.shade.com.alibaba.fastjson.JSON.toJSONString(map);
//        List list = new ArrayList();
////        list.add("啊尼玛0001");
//        Map map1 = new HashMap();
//        map1.put("啊尼玛0001", "啊尼玛0001");
//        list.add(map1);
////        list.add("啊尼玛0002");
//        Map map2 = new HashMap();
//        map2.put("啊尼玛0002", "啊尼玛0002");
//        list.add(map2);
////        list.add("测试0001");
//        Map map3 = new HashMap();
//        map3.put("测试0001", "测试0001");
//        list.add(map3);
//        Collections.sort(list);
//        System.out.println(list);
//        System.out.println();
//        TreeMap treeMap = new TreeMap(new Comparator()  {
//            @Override
//            public int compare(Object o1, Object o2) {
//                return String.valueOf(o2).compareTo(String.valueOf(o1));
//            }
//        });
//        treeMap.put("1", "测试");
//        treeMap.put("2", "测试");
//        treeMap.put("1+A", "测试");
//        System.out.println(treeMap);
//        Collections.sort();

        // 通过反射创建有参的实例，并调用getName方法
//        Object oo = Class.forName("com.situ.super.Sclass").getConstructor(String.class).newInstance("Liu Jian");  //调用构造有参函数
//        getter = o.getClass().getMethod("getName");
//        System.out.println(getter.invoke(o));
    }
}
