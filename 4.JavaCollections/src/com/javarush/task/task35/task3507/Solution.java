package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
//        Set<? extends Animal> allAnimals = getAllAnimals(
//                Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
//                        Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        String pathToAnimals = "/C:/=Yashika=/Javarush/JavaRushTasks/out/production/4.JavaCollections/com/javarush/task/task35/task3507/data";
        Set<? extends Animal> allAnimals = getAllAnimals(pathToAnimals);
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        File[] fileList = new File(pathToAnimals).listFiles();
        MyClassLoader classLoader = new MyClassLoader();

        Set<Animal> resultSet = new HashSet<>();
        for (File file: fileList) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                try {
                    Class currentClass = classLoader.loadClass(file.toPath());
                    if (hasSpecificInterface(currentClass, "Animal") &&
                            hasParameterlessPublicConstructor(currentClass)) {
                        resultSet.add((Animal) currentClass.newInstance());
                    }

                } catch (IllegalAccessException e) {
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultSet;
    }

    private static boolean hasSpecificInterface(Class<?> clazz, String specificInterfaceName) {
        for (Class<?> currentInterface: clazz.getInterfaces()) {
            if (currentInterface.toString().contains(specificInterfaceName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    private static class MyClassLoader extends ClassLoader {

        public Class loadClass(Path path) {
            String packageName = Solution.class.getPackage().getName() + ".data";
            Class result = null;
            try {
                String className = packageName + "." + path.getFileName().toString().replace(".class", "");
                byte[] b = Files.readAllBytes(path);
                result = defineClass(className, b, 0, b.length); //here main magic
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    //        private Map<String, Class> classesHash = new HashMap<>();
//        @Override
//        protected Class<?> findClass(String filename) throws ClassNotFoundException {
//            Class result = (Class) classesHash.get(filename);
//            if (result == null){
//                File file = new File(filename);
//                if (file.exists()) {
//                    byte[] classBytes = new byte[0];
//                    try {
//                        classBytes = loadFileAsBytes(file);
//                    } catch (IOException e) {
//                        throw new ClassNotFoundException(e.getMessage());
//                    }
//                    result = defineClass(filename, classBytes, 0, classBytes.length);
//                    classesHash.put(filename, result);
//                }
//            }
//            return result;
//        }
//
//        static byte[] loadFileAsBytes(File file) throws IOException {
//            byte[] result = new byte[(int)file.length()];
//            FileInputStream f = new FileInputStream(file);
//            f.read(result,0,result.length);
//            f.close();
//            return result;
//        }

}
