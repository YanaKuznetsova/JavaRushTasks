package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File[] fileList = new File(packageName).listFiles();
        MyClassLoader myClassLoader = new MyClassLoader();
        for (File file : fileList) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                Class currentClass = myClassLoader.loadClass(file.toPath());
                if (hasParameterLessConstructor(currentClass) && implementsHiddenClassInterface(currentClass)){
                    hiddenClasses.add(currentClass);
                }
            }
        }
    }

    private boolean implementsHiddenClassInterface(Class currentClass) {
        Class[] interfaces = currentClass.getInterfaces();
        for (Class currentInterface: interfaces) {
            if (currentInterface.getSimpleName().endsWith("HiddenClass")) {
                return true;
            }
        }
        return false;
    }

    private boolean hasParameterLessConstructor(Class currentClass) {
        Constructor[] constructors = currentClass.getDeclaredConstructors();
        for (Constructor constructor: constructors) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    private class MyClassLoader extends ClassLoader {

        public Class<?> loadClass(Path path) {
            Class result = null;
            try {
                byte[] b = Files.readAllBytes(path);
                result = defineClass(null, b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        HiddenClass result = null;
        for (Class currentClass: hiddenClasses) {
            if (currentClass.getSimpleName().toLowerCase().contains(key.toLowerCase())) {
                Constructor[] constructors = currentClass.getDeclaredConstructors();
                for (Constructor currentConstructor: constructors) {
                    if (currentConstructor.getParameterCount() == 0) {
                        try {
                            currentConstructor.setAccessible(true);
                            result = (HiddenClass) currentConstructor.newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        return result;
    }
}

