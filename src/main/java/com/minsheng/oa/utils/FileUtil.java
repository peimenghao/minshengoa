package com.minsheng.oa.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {


    public static void deleteFile(File... files) {  //删除文件
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


    public static String readableFileSize(long size) {  //返回文件大小
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    //获得文件后缀
    public static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
    }

}
