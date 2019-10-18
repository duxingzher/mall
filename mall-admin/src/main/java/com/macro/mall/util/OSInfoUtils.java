package com.macro.mall.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OSInfoUtils implements InitializingBean {

    private static String OS = System.getProperty("os.name").toLowerCase();

    @Value("${aliyun.oss.dir.windowsPrefix}")
    private String windowsCollFilePath;

    @Value("${aliyun.oss.dir.prefix}")
    private String linuxCollFilePath;

    public static String collFilePath = "";


    private OSInfoUtils() {
    }

    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (isLinux()) {
            collFilePath = linuxCollFilePath;
        } else {
            collFilePath = windowsCollFilePath;
        }

        File collFilePathDir = new File(collFilePath);
        if (!collFilePathDir.exists()) {
            collFilePathDir.mkdirs();
        }

    }
}
