package com.suidao.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sysconf")
public class SysConf {
    public  static String uploadurl;
    public static String fireUrlPrefix;

    public static String pythonCompile;
    public static String pythonFilepath;

    public static String getPythonCompile() {
        return pythonCompile;
    }

    public  void setPythonCompile(String pythonCompile) {
        SysConf.pythonCompile = pythonCompile;
    }

    public static String getPythonFilepath() {
        return pythonFilepath;
    }

    public  void setPythonFilepath(String pythonFilepath) {
        SysConf.pythonFilepath = pythonFilepath;
    }

    public static String firstIndexId = "firstIndexId";

    public static String secondIndexId = "secondIndexId";

    public static String itemIndexId = "itemIndexId";

    public static String realData = "realData";

    public static String yuzhiData = "yuzhiData";

    public static String getUploadurl() {
        return uploadurl;
    }

    public  void setUploadurl(String uploadurl) {
        SysConf.uploadurl = uploadurl;
    }

    public static String getFireUrlPrefix() {
        return fireUrlPrefix;
    }

    public  void setFireUrlPrefix(String fireUrlPrefix) {
        SysConf.fireUrlPrefix = fireUrlPrefix;
    }
}
