package cn.gsq.utils;

import cn.gsq.crawler.owninterface.Value;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * 图片下载配置类1
 *
 * @author qsefr
 */
public class PathUtil2 {

    @Value("path")
    public static String PATH;

    @Value("count")
    public static String COUNT;

    @Value("filePath")
    public static String FILEPATH;


    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException {
       /* String pathHeader = "K:\\test";
        File file = new File(pathHeader);
        String[] names = file.list();
        assert names != null;
        for (String name : names) {
            String[] split = name.split("\\.");
            File file1 = new File(pathHeader+"\\" + split[0]);
            if (file1.mkdir()) {
                File file2 = new File(pathHeader + "\\" + name);
                file2.renameTo(new File(pathHeader + "\\" + split[0] + "\\" + name));
            }

        }*/
        Class<User> userClass = User.class;
        Method test = userClass.getDeclaredMethod("test");
        test.setAccessible(true);
        try {
            test.invoke(userClass.newInstance());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
class User {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void test() {
        System.out.println("我执行了");
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
