package ebu9.entity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class Test {


    public static <T> List<String> getObjectAttributeNames(Class<T> clazzT) {
        List<String> list = new LinkedList<>();
        try {
            //根据类名获得其对应的Class对象 写上你想要的类名就是了 注意是全名 如果有包的话要加上 比如java.Lang.String
            Class clazz = Class.forName(clazzT.getName());
            //根据Class对象获得属性 私有的也可以获得
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                list.add(f.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> objectAttributeNames = getObjectAttributeNames(Student.class);
        for (String objectAttributeName : objectAttributeNames) {
            System.out.println(objectAttributeName);
        }
    }
    
}
