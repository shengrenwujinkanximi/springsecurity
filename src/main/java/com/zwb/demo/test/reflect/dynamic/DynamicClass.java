package com.zwb.demo.test.reflect.dynamic;

import javassist.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DynamicClass {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        // TODO Auto-generated method stub
        // 创建类
        ClassPool pool = ClassPool.getDefault();
        CtClass cls = pool.makeClass("com.situ.super.Sclass");
        try {
            // 添加私有成员name及其getter、setter方法
            CtField param = new CtField(pool.get("java.lang.String"), "name", cls); //相当于private String name
            param.setModifiers(Modifier.PRIVATE);  //私有修饰
            cls.addMethod(CtNewMethod.setter("setName", param));//增加set方法，名字为"setName"
            cls.addMethod(CtNewMethod.getter("getName", param));//增加get方法，名字为getname
            cls.addField(param, CtField.Initializer.constant("")); //写入class文件

            // 添加无参的构造体
            CtConstructor cons = new CtConstructor(new CtClass[] {}, cls);  //相当于public Sclass(){this.name = "brant";}
            cons.setBody("{name = \"Brant\";}");
            cls.addConstructor(cons);

            // 添加有参的构造体
            cons = new CtConstructor(new CtClass[] {pool.get("java.lang.String")}, cls);//把参数列表写在本行
            cons.setBody("{$0.name = $1;}");  //第一个传入的形参$1,$2第二个传入的形参，相当于public Sclass(String s){this.name = s;}
            cls.addConstructor(cons);

            //把生成的class文件写入文件,也可以不写入
//            byte[] byteArr = ctClass.toBytecode();
//            FileOutputStream fos = new FileOutputStream(new File("D://Emp.class"));
//            fos.write(byteArr);
//            fos.close();

        } catch (CannotCompileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 打印创建类的类名
        try {
            System.out.println(cls.toClass());
        } catch (CannotCompileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
