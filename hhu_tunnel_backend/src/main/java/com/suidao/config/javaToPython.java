package com.suidao.config;

import com.suidao.base.SysConf;

import java.io.*;
import java.util.ArrayList;

/**
 * @author
 * @date 2021/10/30 0030 9:28
 */
public class javaToPython {

    public void getFile(String path) throws IOException, InterruptedException {
//        String path = "F:\\postgraduate\\01 学习\\python\\pythonProject\\ARIMA_test01.py";
        // String path = "D:\\randomforest\\IForest_dataDetection.py";
        //String path = "F:\\postgraduate\\异常检测\\前端代码\\data-exception\\data-exception\\src\\main\\java\\com\\hhu\\config\\ARIMA_test01.py";
        ArrayList<Object> result = new ArrayList<>();
//        String[] command = new String[] {"F:\\Annacoda3\\envs\\pytorch\\python",path,param[0],param[1],param[2]};
        String[] command = null;
//            command = new String[]{"D:\\Anaconda3\\python.exe", path, mpcd, start, end, autoChange};
        command = new String[]{SysConf.pythonCompile};
        final Process process = Runtime.getRuntime().exec(command);
        System.out.println("python command:" + command);
        printMessage(process.getInputStream());
        printMessage(process.getErrorStream());
        process.waitFor();

//        return result;
    }


    public String buildLock(String str) {
        StringBuilder sb = new StringBuilder(str);
        String lock = sb.toString().intern();
        return lock;
    }

    private static void printMessage(InputStream input) {
        new Thread(new Runnable() {
            public void run() {
                Reader reader = new InputStreamReader(input);
                BufferedReader bf = new BufferedReader(reader);
                String line = null;
                try {
                    while ((line = bf.readLine()) != null) {

                        System.out.println(line); // 返回值
                    }
                    reader.close();
                    bf.close();
                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }).start();
    }
}
