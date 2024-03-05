package com.suidao.service;

import cn.hutool.json.JSONObject;
import com.suidao.base.Result;

import java.util.List;
import java.util.Map;

public interface MonitorTableDataService {
    Map getTableData(Map map, Long current, Long limit);

    Map add(JSONObject map, Long current, Long limit);

    Result getYuzhi(Map map);

    Result editData(Map map);

    Result deleteData(List datas);
}
