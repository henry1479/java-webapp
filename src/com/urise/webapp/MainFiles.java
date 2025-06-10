package com.urise.webapp;

import java.io.File;
import java.util.Objects;

public class MainFiles {
    public static void main(String[] args) {
        directoryWalker(new File("/home/henry1479/Рабочий стол/Junior Java-разработчик веб-приложений - BaseJava/code/java-webapp"));
    }


    private static void directoryWalker(File directory) {
        for(File file : Objects.requireNonNull(directory.listFiles())) {
            if(!file.isDirectory()) {
                System.out.println("    " + file.getName());
            } else {
                System.out.println(file.getName());
                directoryWalker(file);
            }
        }
    }
}
