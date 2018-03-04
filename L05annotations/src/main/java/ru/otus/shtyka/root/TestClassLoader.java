package ru.otus.shtyka.root;

import ru.otus.shtyka.root.annotations.After;
import ru.otus.shtyka.root.annotations.Before;
import ru.otus.shtyka.root.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class TestClassLoader extends ClassLoader {

    public static void loadClassByName(final String className) {
        ClassLoader classLoader = getSystemClassLoader();
        try {
            Class clazz = classLoader.loadClass(className);
            System.out.println("Run class: " + clazz.getName());
            loadMethods(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find class for name " + className);
        }
    }

    public static void loadTestByPackage(String packageName) {
        String packageNameRepl = packageName.replaceAll("\\.", "/");
        URL packageURL = Thread.currentThread().getContextClassLoader().getResource(packageNameRepl);
        if (packageURL == null) {
            throw new IllegalArgumentException("Incorrect package name " + packageName);
        }
        List<Class> classes;
        if (packageURL.getFile().contains("jar!")) {
            classes = readTestJar(packageURL, packageNameRepl);
        } else {
            classes = readTestClass(packageURL, packageName);
        }
        System.out.println("Load from package: " + packageName);
        classes.stream().filter(c -> c.getName().endsWith("Test")).forEach(c -> {
            System.out.println("Run class: " + c.getName());
            loadMethods(c);
        });
    }

    private static List<Class> readTestClass(URL packageURL, String packageName) {
        List<Class> classes = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) packageURL.getContent()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(".class")) {
                    classes.add(Class.forName(packageName + "." + line.substring(0, line.lastIndexOf('.'))));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot read from package " + packageName);
            e.printStackTrace();
        }
        return classes;
    }

    private static List<Class> readTestJar(URL packageURL, String packageName) {
        List<Class> classes = new ArrayList<>();
        try {
            JarFile jarFile = ((JarURLConnection) packageURL.openConnection()).getJarFile();
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().startsWith(packageName) || !je.getName().endsWith(".class")) {
                    continue;
                }
                String className = je.getName().substring(0, je.getName().lastIndexOf('.')).replace('/', '.');
                classes.add(Class.forName(className));
            }
        } catch (IOException e) {
            System.out.println("Cannot read from jar");
        } catch (ClassNotFoundException e1) {
            System.out.println("Cannot read class file");
        }
        return classes;
    }

    @SuppressWarnings({"unchecked"})
    private static void loadMethods(final Class clazz) {
        Set<Method> beforeMethods = new HashSet<>();
        Set<Method> testMethods = new HashSet<>();
        Set<Method> afterMethods = new HashSet<>();
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }
        Object instance = ReflectionHelper.instantiate(clazz);
        testMethods.forEach(t -> {
            beforeMethods.forEach(b -> executeMethod(instance, b));
            executeMethod(instance, t, true);
            afterMethods.forEach(a -> executeMethod(instance, a));
        });
    }

    @SuppressWarnings("ConstantConditions")
    private static void executeMethod(final Object instance, final Method method, final boolean isTest) {
        try {
            ReflectionHelper.callMethod(instance, method.getName());
            if (isTest) {
                System.out.println(method.getName() + " SUCCESS");
            }

        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof TestException && isTest) {
                System.out.println(method.getName() + " FAILED");
            } else {
                e.printStackTrace();
                System.out.println(method.getName() + " throw invoking error");
            }
        } catch (Exception e) {
            System.out.println(method.getName() + " throw error");
        }
    }

    private static void executeMethod(final Object instance, final Method method) {
        executeMethod(instance, method, false);
    }
}
