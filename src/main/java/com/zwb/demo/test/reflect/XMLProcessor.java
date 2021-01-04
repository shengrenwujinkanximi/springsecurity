package com.zwb.demo.test.reflect;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * <p>
 * Description: XML生成类，将传入的对象生成XML文件
 *
 * </p>
 *
 *
 * @author Administrator
 * @since  2008-12-1
 * @version
 */
public class XMLProcessor {

    private ReflectionUtil reflectionUtil;

    /**
     * 建立一个XML文档,文档名由输入参数决定
     *
     * @param objs
     * @param unReflectProp
     * @param filename
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public void createXMLFile(List<Object> objs, String[] unReflectProp,
                              String filename) throws IndexOutOfBoundsException, IOException {
        reflectionUtil = new ReflectionUtil();
        // 建立Document对象
        Document document = DocumentHelper.createDocument();
        // 添加根节点进入Document
        Element rootElement = document.addElement(objs.get(0).getClass()
                .getSimpleName()
                + "s");
        for (Object obj : objs) {
            Map<String, Object> rootMap = reflectionUtil.transformObjData2Map(
                    obj, unReflectProp);
            String rootString = ReflectionUtil.getUserDefiendTypePrefix()
                    + obj.getClass().getSimpleName();
            map2Node((Map) rootMap.get(rootString), rootElement, rootString);
        }

        // 格式化输出XML，换行缩进
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置字符集
        format.setEncoding("GBK");
        // 将document中的内容写入文件中
        XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),
                format);
        writer.write(document);
        writer.close();

    }

    /**
     * 将映射为 对象的属性-->属性对应的值 的map转换为xml文件中的各个节点
     * @param dataMap
     *
     * @param objNode
     *            父节点，map中创建的节点都是其子节点
     * @param objName
     *            父节点的名称
     */
    @SuppressWarnings("unchecked")
    private void map2Node(Map<String, Object> dataMap, Element objNode,
                          String objName) {
        //第一个字符是*，创建*后面字符为名称的节点
        Element objElement = objNode.addElement(objName.substring(1));
        if (isAllSysType(dataMap)) {
            //如果该对象中全部是系统类型则全部添加到objElement节点下
            for (String propName : dataMap.keySet()) {
                createNextNode(objElement, propName, dataMap.get(propName) + "");
            }

        } else {
            //如果该对象中还有用户自定义类型则系统类型添加进入  objNameInfo节点下，用户自定义类型添加进入 自定义类型名成节点下
            Element infoNode = objElement.addElement(objName.substring(1)
                    + "Info");

            for (String propName : dataMap.keySet()) {
                if (propName.startsWith("*")) {
                    map2Node((Map) dataMap.get(propName), objElement, propName);
                } else {
                    createNextNode(infoNode, propName, dataMap.get(propName)
                            + "");
                }

            }
        }
    }

    /**
     * 根据name和text创建elements的子节点
     *
     * @param element
     * @param nodeName
     * @param text
     */
    private void createNextNode(Element element, String nodeName, String text) {
        Element nextElement = element.addElement(nodeName);
        if (text == null) {
            return;
        }
        nextElement.setText(text);
    }

    /**
     * 是否是系统类，如果前缀是*则是自定义类型
     * @param dataMap
     * @return
     */
    private boolean isAllSysType(Map<String, Object> dataMap) {

        for (String propName : dataMap.keySet()) {
            if (propName.startsWith("*")) {
                return false;
            }
        }
        return true;
    }

    public ReflectionUtil getReflectionUtil() {
        return reflectionUtil;
    }

    public void setReflectionUtil(ReflectionUtil reflectionUtil) {
        this.reflectionUtil = reflectionUtil;
    }

}
