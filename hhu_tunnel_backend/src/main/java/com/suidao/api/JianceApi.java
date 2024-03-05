package com.suidao.api;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suidao.base.Result;
import com.suidao.pojo.*;
import com.suidao.pojo.vo.DataVo;
import com.suidao.pojo.vo.Menue;
import com.suidao.service.JianceDataService;
import com.suidao.service.JianceDictService;
import com.suidao.service.JianceTableheadService;
import com.suidao.service.RealYuzhiService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/jiance")
public class JianceApi {
    @Resource
    JianceDictService jianceDictService;
    @Resource
    JianceTableheadService jianceTableheadService;
    @Resource
    JianceDataService jianceDataService;

    @GetMapping("/dict")
    public Result getJiancDict() {
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
            menue.setChildren(jianceDictService.getAllDict());
            return menue;
        }).collect(Collectors.toList());
        return Result.ok(results);
    }

    @PostMapping("/getTable/{current}/{limit}")
    public Result getTable(@RequestBody Map map,
                           @PathVariable("current") Long current,
                           @PathVariable("limit") Long limit) {
        //首先从表头数据库中读取表头
        List<JianceTablehead> tableheads = jianceTableheadService.getTableHead(map,Long.valueOf(map.get("itemIndexId").toString()));
        //获取对应条件的表格数据
        Map result = jianceDataService.getTableData(map, current, limit);
        if (result == null || result.size() == 0) {
            HashMap<Object, Object> headMap = new HashMap<>();
            headMap.put("tableHead", tableheads);
            headMap.put("current",1);
            headMap.put("total",0);
            headMap.put("tableData",null);
            return Result.ok(headMap);
        }
        result.put("tableHead", tableheads);
        return Result.ok(result);
    }
    @PostMapping("/page/{current}/{limit}")
    public Result getPage(@RequestBody Map map,
                          @PathVariable("current") Long current,
                          @PathVariable("limit") Long limit){
        Map result = jianceDataService.getTableData(map,current,limit);
        if (result == null || result.size() == 0) {
            HashMap<Object, Object> headMap = new HashMap<>();
            headMap.put("current",1);
            headMap.put("total",0);
            headMap.put("tableData",null);
            return Result.ok(headMap);
        }
        return Result.ok(result);
    }
    /**
     * @param map
     * @return
     */
    @PostMapping("/add/{current}/{limit}")
    public Result addData(@RequestBody JSONObject map,
                         @PathVariable("current") Long current,
                           @PathVariable("limit") Long limit) {
        Map result = jianceDataService.add(map, current,limit);
        if (result == null || result.size() == 0)
            return Result.fail("插入失败");
        return Result.ok(result);
    }
    @PostMapping("/edit")
    public Result editData(@RequestBody Map map){
        return jianceDataService.editData(map);
    }

    @PostMapping("/delete")
    public Result deleteData(@RequestBody Map map){
        List datas = (List)map.get("list");
        return jianceDataService.deleteData(datas);
    }

    @PostMapping("/getYuzhi")
    public Result getYuzhi(@RequestBody Map map){
        return jianceDataService.getYuzhi(map);
    }
}