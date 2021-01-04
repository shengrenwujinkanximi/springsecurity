package com.zwb.demo.test.reflect;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Description: 反射机制工具类，根据传入的Obj，
 *              为生成xml文件提取所有属性和属性对应的值
 * </p>
 *
 *
 * @author Administrator
 * @since  2008-12-1
 * @version
 */
public class ReflectionUtil {

    //所有的系统类型
    private static String[] allSysDefinedType = new String[] {
            "java.lang.String", "java.lang.Long", "long", "java.lang.Integer",
            "int", "java.lang.Short", "short", "java.lang.Byte", "byte",
            "java.lang.Float", "float", "java.lang.Double", "double",
            "java.util.Date", "java.lang,Char", "char", "java.lang.Boolean",
            "boolean", "java.sql.Timestamp" };

    private static String dateFormatString = "yyyy-MM-dd HH:mm:ss";

    //使用不可以是属性名前缀的*作为标识是否是用户自定义的类型
    private static String userDefiendTypePrefix = "*";

    /**
     * 根据传入的对象将对象的属性和该属性对应的值放入map
     * @param  obj
     *           待映射属性和属性值的对象
     * @param  unReflectProp
     *           不需要映射属性和属性值的所有属性；
     *           eg. CbsTargetTime对象中有DATE_FORMAT不需要映射则在该参数中添加
     *               CbsTargetTime.DATE_FORMAT
     *
     * @return Map
     *           key = property name ; value = property value;
     *           (如果属性为一个用户自定义类型的对象那么
     *           key = '*' + 'user defined type';
     *           value = map;map中key是属性，value是值，依次循环）
     */
    public Map<String, Object> transformObjData2Map(Object obj,
                                                    String[] unReflectProp) {
        Map<String, Object> rootMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        rootMap.put(userDefiendTypePrefix + obj.getClass().getSimpleName(),
                dataMap); // 使用不可以是属性名前缀的*作为标识是否是用户自定义的类型
        this.getObjPro(obj, unReflectProp, dataMap, obj.getClass().getName());
        return rootMap;
    }

    /**
     * 得到传入对象的每个属性域，
     * 根据每个属性域得到属性域对应的值
     *
     * @param obj
     *         待映射得到属性和值的对象
     *
     * @param  unReflectProp
     *         不需要映射属性和属性值的所有属性；
     *
     * @param  className
     *         对象的类型名，如果传入对象为空时，用其新建一个对象
     * @param dataMap
     *         key=属性，value=属性的值
     */

    @SuppressWarnings("unchecked")
    private void getObjPro(Object obj, String[] unReflectProp, Map dataMap,
                           String className) {
        try {
            Class clas;
            if (obj == null) {
                clas = Class.forName(className);
                obj = clas.newInstance();
            } else {
                clas = Class.forName(obj.getClass().getName());
            }

            //得到obj类的所有属性
            Field[] fileds = clas.getDeclaredFields();
            for (Field field : fileds) {
                String fieldName = field.getName();
                String fieldType = field.getType().getName();
                //如果该属性是不需要映射的属性则跳出循环
                if (!reflectPropOrNot(obj.getClass().getSimpleName() + "."
                        + fieldName, unReflectProp)) {
                    continue;
                }
                //属性名的第一个字母大写，与get或者is组成方法名
                String firstCharUpper = fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1, fieldName.length());
                Method method;
                //如果是boolean型则方法名为is*；反之为get*
                if (isBooleanType(fieldType)) {
                    method = clas.getMethod("is" + firstCharUpper, null);
                } else {
                    method = clas.getMethod("get" + firstCharUpper, null);
                }

                if (isSysDefinedType(fieldType)) {
                    //如果是系统类型则添加进入map
                    String formatDateStr = isTimeType(fieldType, method, obj);
                    if (formatDateStr != null) {
                        dataMap.put(fieldName, formatDateStr);
                        continue;
                    }
                    dataMap.put(fieldName,
                            method.invoke(obj, null) == null ? "" : method
                                    .invoke(obj, null));

                } else {
                    //如果不是系统类型对象则key = * + 属性名 ； value = map；循环迭代
                    Map dateMap2 = new HashMap();
                    this.getObjPro(method.invoke(obj, null), unReflectProp,
                            dateMap2, fieldType);
                    dataMap.put(userDefiendTypePrefix
                            + field.getType().getSimpleName(), dateMap2);
                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("映射属性和属性所对应值出现异常！！");
            e.printStackTrace();

        }

    }

    /**
     * 验证属性是否是要通过反射机制得到名称和值
     * @param fieldName
     *              待验证的属性
     * @return
     *        true  要利用反射机制的到属性名和值
     *        false 不需要利用反射机制的到属性名和值
     */
    private boolean reflectPropOrNot(String fieldName, String[] unReflectProp) {
        if (unReflectProp == null) {
            return true;
        }
        for (String propName : unReflectProp) {
            if (propName.equals(fieldName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证属性的类型是否是系统定义的类型
     * @param fieldType
     *             待验证类型的名称
     * @return
     *        true  是系统定义的对象
     *        false 用户定义的对象
     */
    private boolean isSysDefinedType(String fieldType) {
        for (String type : allSysDefinedType) {
            if (fieldType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果该属性是日期属性则根据要输出的类型返回字符串，反之返回null
     * @param fileType
     * @param method
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private String isTimeType(String fileType, Method method, Object obj)
            throws IllegalAccessException, InvocationTargetException {
        if (fileType.equals("java.util.Date")
                || fileType.equals("java.sql.Timestamp")) {
            if (method.invoke(obj, null) == null) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
            return sdf.format(method.invoke(obj, null));
        } else {
            return null;
        }
    }

    public boolean isBooleanType(String fieldType) {
        if (fieldType.equals("java.util.Boolean")
                || fieldType.equals("boolean")) {
            return true;
        }
        return false;
    }

    public static void setDateFormatString(String dateFormatString) {
        ReflectionUtil.dateFormatString = dateFormatString;
    }

    public static String getUserDefiendTypePrefix() {
        return userDefiendTypePrefix;
    }

    public static void setUserDefiendTypePrefix(String userDefiendTypePrefix) {
        ReflectionUtil.userDefiendTypePrefix = userDefiendTypePrefix;
    }

}