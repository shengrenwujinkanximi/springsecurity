package com.zwb.demo.test.reflect;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Description: 提供对象转换为XML的调用接口
 *
 * </p>
 *
 *
 * @author Administrator
 * @since  2008-12-2
 * @version
 */
public class Object2XMLHander {

    private XMLProcessor xmlProcessor;

    /**
     * 生成一个XML文档,文档名由输入参数决定
     *
     * @param objs
     *          待转换为XML文件的对象集合
     *
     * @param unReflectProp
     *           不需要映射进入XML属性；如果全部要映射进入XML文件则传入null
     *           eg. CbsTargetTime对象中有DATE_FORMAT不需要映射则在该参数中添加
     *               CbsTargetTime.DATE_FORMAT
     *
     * @param filename
     *           需建立Xml文件的完全路径和文件名
     *
     * @return 返回操作结果,
     *         0表失败, 1表成功
     */
    @SuppressWarnings("unchecked")
    public int createXMLFile(List objs, String[] unReflectProp, String filename) {
        try {
            xmlProcessor = new XMLProcessor();
            xmlProcessor.createXMLFile(objs, unReflectProp, filename);
            return 1;
        } catch (IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            //System.out.println("传入的对象数组长度为零！");
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("生成XML异常！");
            e.printStackTrace();
            return 0;
        }
    }

}
