package com.zwb.demo.refectClass;

import javax.tools.*;

/**
 * @author zhouw
 * @title: AdvancedCompiler
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/1/817:28
 */
public class AdvancedCompiler {
    public static void main(String[] args) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        standardJavaFileManager.getJavaFileObjects("fileName");
        Iterable<? extends JavaFileObject> iter = standardJavaFileManager.getJavaFileObjects("");
        JavaCompiler.CompilationTask ct = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, iter);
    }
//    private static SimpleJavaFileObject simpleJavaFileObject () {
//        StringBuilder stringBuilder = new StringBuilder("package math;" +
//                "class CalculatorTest {\n" +
//                "  public void testMultiply() {\n" +
//                "    Calculator c = new Calculator();\n" +
//                "    System.out.println(c.multiply(2, 4));\n" +
//                "  }\n" +
//                "  public static void main(String[] args) {\n" +
//                "    CalculatorTest ct = new CalculatorTest();\n" +
//                "    ct.testMultiply();\n" +
//                "  }\n" +
//                "}\n");
//
//        return so;
//    }
}
