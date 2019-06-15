package com.minsheng.oa.main.upload.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * 　　* 主要复习文件创建以及文件价
 * 　　* @author heack
 * 　　*
 */
public class Test {


    File file;

    File[] files;


    List<String> pathName = new ArrayList<String>();

// 用于遍历文件价


    public void iteratorPath(String dir) {
        file = new File(dir);
        files = file.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    pathName.add(file.getName());

                } else if (file.isDirectory()) {
                    iteratorPath(file.getAbsolutePath());
                }
            }
        }
    }


    public static void main(String[] args) {
        Test test = new Test();
        test.iteratorPath("E:/git");
        for (String name : test.pathName) {
            System.out.println(name);
        }


    }
}