package com.suidao.api;

import cn.hutool.json.JSONObject;
import com.suidao.base.Result;
import com.suidao.pojo.JianceTablehead;
import com.suidao.pojo.MonitorTablehead;
import com.suidao.pojo.vo.Menue;
import com.suidao.service.MonitorDictService;
import com.suidao.service.MonitorTableDataService;
import com.suidao.service.MonitorTableheadService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monitor")
@CrossOrigin
public class MonitorApi {
    @Resource
    MonitorDictService monitorDictService;

    @Resource
    MonitorTableheadService monitorTableheadService;

    @Resource
    MonitorTableDataService monitorTableDataService;
    @GetMapping("/dict")
    public Result getDict() {
        //从数据中选择出所有隧道,并转换为menue类型
        ArrayList<Menue> suidaoMenue = new ArrayList<>();
        Menue suidao1 = new Menue();
        suidao1.setLabel("扬子江隧道");
        suidao1.setId(1001L);
        Menue suidao2 = new Menue();
        suidao2.setId(1002L);
        suidao2.setLabel("定淮门隧道");
        suidaoMenue.add(suidao1);
        suidaoMenue.add(suidao2);
        List<Menue> results = suidaoMenue.stream().map(menue -> {
            menue.setChildren(monitorDictService.getAllDict());
            return menue;
        }).collect(Collectors.toList());
        return Result.ok(results);
    }

    @PostMapping("/getTable/{current}/{limit}")
    public Result getTable(@RequestBody Map map,
                           @PathVariable("current") Long current,
                           @PathVariable("limit") Long limit) {
        List<MonitorTablehead> tableheads = monitorTableheadService.getHead(map,Long.valueOf(map.get("itemIndexId").toString()));
        Map result = monitorTableDataService.getTableData(map, current, limit);
        if (result == null || result.size() == 0) {
            HashMap<Object, Object> headMap = new HashMap<>();
            headMap.put("tableHead", tableheads);
            headMap.put("current", 1);
            headMap.put("total", 0);
            headMap.put("tableData", null);
            return Result.ok(headMap);
        }
        result.put("tableHead", tableheads);
        return Result.ok(result);
    }

    @PostMapping("/page/{current}/{limit}")
    public Result getPage(@RequestBody Map map,
                          @PathVariable("current") Long current,
                          @PathVariable("limit") Long limit){
        Map result = monitorTableDataService.getTableData(map,current,limit);
        if (result == null || result.size() == 0) {
            HashMap<Object, Object> headMap = new HashMap<>();
            headMap.put("current",1);
            headMap.put("total",0);
            headMap.put("tableData",null);
            return Result.ok(headMap);
        }
        return Result.ok(result);
    }

    @PostMapping("/add/{current}/{limit}")
    public Result add(@RequestBody JSONObject map,
                      @PathVariable("current") Long current,
                      @PathVariable("limit") Long limit){
        Map result = monitorTableDataService.add(map, current,limit);
        if (result == null || result.size() == 0)
            return Result.fail("插入失败");
        return Result.ok(result);
    }
    @PostMapping("/getYuzhi")
    public Result getYuzhi(@RequestBody Map map){
        return monitorTableDataService.getYuzhi(map);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Map map){
        return monitorTableDataService.editData(map);
    }

    @PostMapping("/delete")
    public Result deleteData(@RequestBody Map map){
        List datas = (List)map.get("list");
        return monitorTableDataService.deleteData(datas);
    }
}
