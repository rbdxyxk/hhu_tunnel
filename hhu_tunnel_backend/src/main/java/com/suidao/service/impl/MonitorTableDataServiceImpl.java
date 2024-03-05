package com.suidao.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suidao.base.Result;
import com.suidao.base.SqlConf;
import com.suidao.base.SysConf;
import com.suidao.pojo.*;
import com.suidao.service.*;
import com.suidao.utils.BeanUtil;
import com.suidao.utils.Constant;
import com.suidao.utils.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MonitorTableDataServiceImpl implements MonitorTableDataService {

    @Resource
    MonitorDynamicTablenameService monitorDynamicTablenameService;
    @Resource
    DynamicTableService dynamicTableService;
    @Resource
    RealYuzhiService realYuzhiService;
    @Resource
    MonitorTableheadService monitorTableheadService;
    @Resource
    MonitorYuzhiService monitorYuzhiService;

    @Override
    public Map getTableData(Map map, Long current, Long limit) {
        //根据itemId来获取对应的数据表
        QueryWrapper<MonitorDynamicTablename> dynamicTableQueryWrapper = new QueryWrapper<>();
//        dynamicTableQueryWrapper.eq(SqlConf.firstIndexId, map.get(SysConf.firstIndexId));
//        dynamicTableQueryWrapper.eq(SqlConf.secondIndexId, map.get(SysConf.secondIndexId));
        dynamicTableQueryWrapper.eq(SqlConf.itemId, map.get("itemIndexId"));
        MonitorDynamicTablename dynamicTable = monitorDynamicTablenameService.getOne(dynamicTableQueryWrapper);
        if (dynamicTable == null) {
            return null;
        }
        return getData(dynamicTable, current, limit, map);
    }

    public Map<String, Object> getData(MonitorDynamicTablename dynamicTable, Long current, Long limit, Map map) {
        Long suidaoId = Long.valueOf(map.get("suidaoId").toString());
        Long firstIndexId = Long.valueOf(map.get("firstIndexId").toString());
        Long secondIndexId = Long.valueOf(map.get("secondIndexId").toString());
        Long intemIndxId = Long.valueOf(map.get("itemIndexId").toString());
        //获取总数据个数
        Long total = dynamicTableService.getTotal(dynamicTable.getTableName(), suidaoId, firstIndexId, secondIndexId, intemIndxId);
        if (total == 0)
            return null;
        Long begin = (current - 1) * limit;
        //数据list
        List<Map<String, Object>> tableDataList = dynamicTableService.getDataByTableName(dynamicTable.getTableName(), begin, limit, suidaoId, firstIndexId, secondIndexId, intemIndxId);
        //因为最终需要的是驼峰命名的数据,所以赋值到一个新的list集合中
        ArrayList<Map<String, Object>> campeResultList = new ArrayList<>();
        for (Map<String, Object> dataMap : tableDataList) {
            HashMap<String, Object> temp = new HashMap<>();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String key = entry.getKey();
                String camelKey = StrUtil.toCamel(key, "_");
                temp.put(camelKey, entry.getValue());
            }
            campeResultList.add(temp);
        }
        //遍历每一个map,给他们添加children属性，每一个存放的是实际值，阈值
        for (Map<String, Object> dataMap : campeResultList) {
            ArrayList<RealYuzhi> childrens = new ArrayList<>();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                //找到日期,因为日期是loca
                //找到有阈值的
                String key = entry.getKey();
                if (!(Constant.exculdeCheckItem.contains(key))) {
                    Long id = Long.valueOf(entry.getValue().toString());
                    //根据id去真实值和阈值表中查找数据
                    RealYuzhi yuzhi = realYuzhiService.getById(id);
                    //这里的data都是字符串,如果需要将它们转换为数组,用,隔开
                    //进行非空判断
                    if (yuzhi.getYuzhiDataStr() != null)
                        yuzhi.setYuzhiData(yuzhi.getYuzhiDataStr().split(","));
                    if (yuzhi.getRealDataStr() != null)
                        yuzhi.setRealData(yuzhi.getRealDataStr().split(","));
                    childrens.add(yuzhi);
                }
            }
            dataMap.put("children", childrens);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("tableData", campeResultList);
        result.put("current", current);
        result.put("total", total);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map add(JSONObject map, Long current, Long limit) {
        //根据itemIndexId来获取表头数据
        Long itemIndexId = Long.valueOf(map.get("itemIndexId").toString());
        List<MonitorTablehead> tableHead = monitorTableheadService.getTableHeadWithoutMap(itemIndexId);
        HashMap<String, String> headMap = new HashMap<>();
        for (MonitorTablehead jianceTablehead : tableHead) {
            headMap.put(jianceTablehead.getId().toString(), jianceTablehead.getProp());
        }
        //首先获取map中的children集合,遍历每一个list集合,插入到
        List children = (List) map.get("children");
        for (Object child : children) {
            JSONObject temp = JSONUtil.parseObj(child);
            RealYuzhi realYuzhi = BeanUtil.transRealformArrayToStr(temp);
            realYuzhiService.insertData(realYuzhi);
            //prop是数据库中下划线的字段
            String prop = headMap.get(realYuzhi.getItemIndexId().toString());
            map.put(prop, realYuzhi.getId());
        }
        //获取表名
        //根据itemId来获取对应的数据表
        QueryWrapper<MonitorDynamicTablename> dynamicTableQueryWrapper = new QueryWrapper<>();
        dynamicTableQueryWrapper.eq(SqlConf.itemId, itemIndexId);
        MonitorDynamicTablename jianceDynamicTable = monitorDynamicTablenameService.getOne(dynamicTableQueryWrapper);
        if (jianceDynamicTable == null) {
            return null;
        }
        //需要将驼峰转换成下划线
        HashMap<String, Object> dataMap = new HashMap<>();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String camelKey = (String) entry.getKey();
            String underLineKey = StrUtil.CameltoUnderLine(camelKey);
            dataMap.put(underLineKey, entry.getValue());
        }
        com.suidao.utils.BeanUtil.mapValueToStr(dataMap);
        Boolean respones = dynamicTableService.insertDatas(dataMap, jianceDynamicTable.getTableName());
        if (BooleanUtil.isFalse(respones))
            return null;
        return getData(jianceDynamicTable, current, limit, map);
    }

    @Override
    public Result getYuzhi(Map map) {
        if (map.get(SysConf.firstIndexId) == null)
            return Result.fail("检测项目阈值的firstIndexId不能为空");
        if (map.get(SysConf.secondIndexId) == null)
            return Result.fail("检测项目阈值的secondIndexId不能为空");
        if (map.get(SysConf.itemIndexId) == null)
            return Result.fail("检测项目阈值的itemIndexId不能为空");
        //获取map中的firstIndexId和itemIndexId
        Long firstIndexId = Long.valueOf(map.get(SysConf.firstIndexId).toString());
        Long secondIndexId = Long.valueOf(map.get(SysConf.secondIndexId).toString());
        Long itemIndexId = Long.valueOf(map.get(SysConf.itemIndexId).toString());
        //根据两个去查询阈值表
        QueryWrapper<MonitorYuzhi> jianceYuzhiQueryWrapper = new QueryWrapper<>();
        jianceYuzhiQueryWrapper.eq(SqlConf.firstIndexId, firstIndexId);
        jianceYuzhiQueryWrapper.eq(SqlConf.secondIndexId, secondIndexId);
        jianceYuzhiQueryWrapper.eq(SqlConf.itemId, itemIndexId);
        List<MonitorYuzhi> yuzhiList = monitorYuzhiService.list(jianceYuzhiQueryWrapper);
        if (yuzhiList == null || yuzhiList.size() == 0)
            return Result.ok(Collections.EMPTY_LIST);
        //如果不为空,生成map,key是表头id,value就是阈值,将字符串转换为数组
        HashMap<Long, String[]> yuzhiMap = new HashMap<>();
        for (MonitorYuzhi jianceYuzhi : yuzhiList) {
            if (jianceYuzhi.getTableHeadId() != null)
                yuzhiMap.put(jianceYuzhi.getTableHeadId(), jianceYuzhi.getYuzhi().split(","));
        }
        return Result.ok(yuzhiMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result editData(Map map) {
        if (map.get(SysConf.itemIndexId) == null)
            return Result.fail("检测项目阈值的itemIndexId不能为空");
        //获取map中的firstIndexId和itemIndexId

        //这里要处理一下，因为children数组只有表头id,而map的key是名称，所以需要一个map,key是id,value是名称
        Long itemIndexId = Long.valueOf(map.get("itemIndexId").toString());
        List<MonitorTablehead> tableHead = monitorTableheadService.getTableHeadWithoutMap(itemIndexId);
        HashMap<String, String> headMap = new HashMap<>();
        for (MonitorTablehead jianceTablehead : tableHead) {
            headMap.put(jianceTablehead.getId().toString(), jianceTablehead.getProp());
        }
        //根据map的数据查询获得表头的所有数据
        List children = (List) map.get("children");
        for (Object child : children) {
            JSONObject temp = JSONUtil.parseObj(child);
            RealYuzhi realYuzhi = com.suidao.utils.BeanUtil.transRealformArrayToStr(temp);
            //要获取修改数据的id
            //从headMap中获取prop
            String prop = headMap.get(realYuzhi.getItemIndexId().toString());
            //这里的prop是数据库中的prop,即带下划线的
            //需要将prop下划线转成驼峰
            prop = StrUtil.toCamel(prop, "_");
            Long id = Long.valueOf(map.get(prop).toString());
            realYuzhi.setId(id);
            boolean b = realYuzhiService.updateById(realYuzhi);
            if (BooleanUtil.isFalse(b))
                return Result.fail();
        }
        QueryWrapper<MonitorDynamicTablename> dynamicTableQueryWrapper = new QueryWrapper<>();
        dynamicTableQueryWrapper.eq(SqlConf.itemId, itemIndexId);
        MonitorDynamicTablename jianceDynamicTable = monitorDynamicTablenameService.getOne(dynamicTableQueryWrapper);
        if (jianceDynamicTable == null) {
            return Result.fail();
        }
        //再更新外面的整体,排除children
        //将驼峰转换成下划线
        Map dataMap = com.suidao.utils.BeanUtil.mapCamelToUnderLine(map);
        com.suidao.utils.BeanUtil.mapValueToStr(dataMap);
        boolean updateResult = dynamicTableService.updateTableData(dataMap, jianceDynamicTable.getTableName());
        if (BooleanUtil.isFalse(updateResult))
            return Result.fail();
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result deleteData(List datas) {
        for (Object data : datas) {
            Map map = cn.hutool.core.bean.BeanUtil.beanToMap(data);
            map.remove("children");
            Long itemIndxId = Long.valueOf(map.get("itemIndexId").toString());
            QueryWrapper<MonitorDynamicTablename> dynamicTableQueryWrapper = new QueryWrapper<>();
            dynamicTableQueryWrapper.eq(SqlConf.itemId, itemIndxId);
            MonitorDynamicTablename jianceDynamicTable = monitorDynamicTablenameService.getOne(dynamicTableQueryWrapper);
            if (jianceDynamicTable == null) {
                return Result.fail();
            }
            //首先获取阈值中的值,这里只需要获取对应id
            ArrayList<Long> yuzhiIds = new ArrayList<>();
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                //有阈值
                if (!Constant.exculdeCheckItem.contains((String) entry.getKey())) {
                    //获取阈值表中的id
                    Long id = Long.valueOf(entry.getValue().toString());
                    yuzhiIds.add(id);
                }
            }
            boolean removeRealResult = realYuzhiService.removeByIds(yuzhiIds);
            if (BooleanUtil.isFalse(removeRealResult))
                return Result.fail();
            Long id = Long.valueOf(map.get("id").toString());
            boolean result = dynamicTableService.deleteTableDataById(id, jianceDynamicTable.getTableName());
            if (BooleanUtil.isFalse(result))
                return Result.fail();
        }
        return Result.ok();
    }
}
