package com.zwb.demo.refectClass;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author zhouw
 * @title: AdvancedCompiler
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/1/817:28
 */

public class AdvancedCompiler {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        newProxyInstance();
        System.out.println("动态生成代理耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println();
    }

    public static Object newProxyInstance() throws Exception {
        String src = "package com.zwb.demo.refectClass;\n\n" +
                "public class StaticProxy {\n" +
                "\tpublic void hello()\n" +
                "\t{\n" +
                "\t\tSystem.out.println(\"Before Hello World!\");\n" +
                "\t\tSystem.out.println(\"After Hello World!\");\n" +
                "\t}\n" +
                "}";

        /** 生成一段Java代码 */
        String fileDir = System.getProperty("user.dir");
        String fileName = "D:\\ideaProjects\\zwbProjects\\src\\main\\java\\com\\zwb\\demo\\refectClass\\StaticProxy.java";
        File javaFile = new File(fileName);
        Writer writer = new FileWriter(javaFile);
        writer.write(src);
        writer.close();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iter = sjfm.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask ct = compiler.getTask(null, sjfm, null, null, null, iter);
        ct.call();
        sjfm.close();

        URL[] urls = new URL[]{(new URL("file:\\" + "D:\\ideaProjects\\zwbProjects\\src"))};
        URLClassLoader ul = new URLClassLoader(urls);
        System.out.println("class loader for url");
        Class<?> c = ul.loadClass("com.zwb.demo.refectClass.StaticProxy");
        Object o = c.newInstance();
        Method m = c.getDeclaredMethod("hello", null);
        m.invoke(o);

        File classFile = new File(fileDir + "/src/main/java/com/zwb/demo/refectClass/StaticProxy.class");
        javaFile.delete();
        classFile.delete();
        ul.close();

        return "";
    }

}
