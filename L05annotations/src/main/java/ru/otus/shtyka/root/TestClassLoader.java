package ru.otus.shtyka.root;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import ru.otus.shtyka.root.annotations.After;
import ru.otus.shtyka.root.annotations.Before;
import ru.otus.shtyka.root.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


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
        Set<Class> classes = new HashSet<>();
        new Reflections(packageName, new MethodAnnotationsScanner()).
                getMethodsAnnotatedWith(Test.class).forEach(m -> classes.add(m.getDeclaringClass()));
        classes.forEach(TestClassLoader::loadMethods);
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
        testMethods.forEach(t -> {
            Object instance = ReflectionHelper.instantiate(t.getDeclaringClass());
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
