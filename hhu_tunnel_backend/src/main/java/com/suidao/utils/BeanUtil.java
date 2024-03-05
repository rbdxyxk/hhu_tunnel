package com.suidao.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.suidao.base.SysConf;
import com.suidao.pojo.RealYuzhi;

import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    public static Map mapCamelToUnderLine(Map map){
        HashMap<String, Object> dataMap = new HashMap<>();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String camelKey = (String) entry.getKey();
            String underLineKey = StrUtil.CameltoUnderLine(camelKey);
            dataMap.put(underLineKey,entry.getValue());
        }
        return dataMap;
    }
    /*
    将map所有的值都转换成str,不然mybatis插入0值会自动判空
     */
    public static void mapValueToStr(Map map){
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object value = entry.getValue();
            if (value instanceof Integer || value instanceof Double || value instanceof Long)
                map.replace(entry.getKey(),value.toString());
        }
    }

    public static RealYuzhi transRealformArrayToStr(JSONObject temp) {
        RealYuzhi realYuzhi = new RealYuzhi();
        //将temp转换成实体类
        Long tempItemIndexId = Long.valueOf(temp.get(SysConf.itemIndexId).toString());
        realYuzhi.setItemIndexId(tempItemIndexId);
        //真实值
        JSONArray realDataArray = (JSONArray) temp.get(SysConf.realData);
        StringBuilder realDataStringBuilder = new StringBuilder();
        //在这里进行非空判断，避免只有,
        if (realDataArray != null && realDataArray.size() > 0 && !realDataArray.get(0).equals("")) {
            //转换成字符串，用,分隔
            for (int i = 0; i < realDataArray.size(); i++) {
                realDataStringBuilder.append(realDataArray.get(i));
                if (i != realDataArray.size() - 1)
                    realDataStringBuilder.append(",");
            }
            String realDataStr = realDataStringBuilder.toString();
            realYuzhi.setRealDataStr(realDataStr);
        }
        //阈值
        JSONArray yuzhiDataArray = (JSONArray) temp.get(SysConf.yuzhiData);
        if (yuzhiDataArray != null && yuzhiDataArray.size() > 0 && !yuzhiDataArray.get(0).equals("")) {
            StringBuilder yuzhiDataStringBuilder = new StringBuilder();
            //转换成字符串，用,分隔
            for (int i = 0; i < yuzhiDataArray.size(); i++) {
                yuzhiDataStringBuilder.append(yuzhiDataArray.get(i));
                if (i != yuzhiDataArray.size() - 1)
                    yuzhiDataStringBuilder.append(",");
            }
            String yuzhiDataStr = yuzhiDataStringBuilder.toString();
            realYuzhi.setYuzhiDataStr(yuzhiDataStr);
        }
        return realYuzhi;
    }
}
