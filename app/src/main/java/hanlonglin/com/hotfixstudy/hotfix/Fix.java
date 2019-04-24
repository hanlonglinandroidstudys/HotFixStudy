package hanlonglin.com.hotfixstudy.hotfix;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 热修复类
 */

public class Fix {
    private final static String TAG = "Fix";

    public static boolean fix(ClassLoader classLoader, String optPath, String path) {
        try {
            //=======1.反射获取到pathList属性========

            Class clazz;
            Field pathListField = null;

            //从父类找到属性pathList
            for (clazz = classLoader.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    pathListField = clazz.getDeclaredField("pathList");
                    if (pathListField != null) {
                        pathListField.setAccessible(true);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //=======2.反射修改pathList的dexElements属性==========
            //1.把补丁包patch.dex转化为Elements[](patch)
            /**
             * 参考源码 将 .dex文件转化  Elements[] 的方法，
             * 使用 makePathElements(splitDexPath(dexPath), optimizedDirectory,suppressedExceptions);
             *
             */
            Object pathListObj = pathListField.get(classLoader);
            Class<?> pathListClass = pathListObj.getClass();

            //反射pathListField 找到makePathElements
            Method makePathElementsMethod = pathListClass.getDeclaredMethod("makePathElements", List.class, File.class, List.class);
            makePathElementsMethod.setAccessible(true);

            //调用makePathElements()得到Elements
            List<File> list = new ArrayList<>();
            File dexFile=new File(path);
            if(!dexFile.exists())
                return false;
            list.add(dexFile);
            ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
            Object[] pathElementsObj = (Object[]) makePathElementsMethod.invoke(pathListObj, list, new File(optPath), suppressedExceptions);

            //2.获得dexElements属性（old）
            Field dexElementsFiled = pathListClass.getDeclaredField("dexElements");
            dexElementsFiled.setAccessible(true);
            Object[] dexElementsObj = (Object[]) dexElementsFiled.get(pathListObj);

            //3.path+old 合并，并反射赋值给dexElements

            //创建Element数组
            Object[] newElements = (Object[]) Array.newInstance(dexElementsObj.getClass().getComponentType(), pathElementsObj.length + dexElementsObj.length);

            //将pathElements 复制到newElements中
            System.arraycopy(pathElementsObj, 0, newElements, 0, pathElementsObj.length);

            //将dexElements 复制到newElements中
            System.arraycopy(dexElementsObj, 0, newElements, pathElementsObj.length, dexElementsObj.length);

            //反射修改 dexElements  使用newElements 替换
            dexElementsFiled.set(pathListObj, newElements);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Fix异常：" + e.getMessage());
            return false;
        }
        return true;
    }
}
