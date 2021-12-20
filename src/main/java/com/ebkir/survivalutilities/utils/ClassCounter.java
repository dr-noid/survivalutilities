package com.ebkir.survivalutilities.utils;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassCounter {

    public static Set<Class> findAllClassesUsingReflection(String packageName) {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName()
                            .equalsIgnoreCase(packageName))
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
