package com.suidao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.pojo.JianceDynamicTable;
import com.suidao.service.DynamicTableService;
import com.suidao.mapper.DynamicTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【dynamic_table】的数据库操作Service实现
* @createDate 2023-03-30 14:32:00
*/
@Service
public class DynamicTableServiceImpl extends ServiceImpl<DynamicTableMapper, JianceDynamicTable>
    implements DynamicTableService{
    @Resource
    DynamicTableMapper dynamicTableMapper;
    @Override
    public List<Map<String, Object>> getDataByTableName(String name, Long begin, Long limit,Long suidaoId,Long firstIndexId,Long secondIndexId,Long itemIndexId) {
        return dynamicTableMapper.getDataByTableName(name,begin,limit,suidaoId,firstIndexId,secondIndexId,itemIndexId);
    }

    @Override
    public Long getTotal(String tablename, Long suidaoId, Long firstIndexId, Long secondIndexId, Long itemIndexId) {
        return dynamicTableMapper.getTotal( tablename,  suidaoId,  firstIndexId,  secondIndexId,  itemIndexId);
    }

    @Override
    public Boolean insertDatas(Map map, String tablename) {
        return dynamicTableMapper.insertDatas(map,tablename);
    }

    @Override
    public boolean updateTableData(Map map, String tablename) {
        return dynamicTableMapper.updateTableData(map,tablename);
    }

    @Override
    public boolean deleteTableDataById(Long id, String tablename) {
        return dynamicTableMapper.deleteTableDataById(id,tablename);
    }

    @Override
    public List<Map> getPredictDatas(String tableName, Long suidaoId, Long firstIndexId, Long secondIndexId, Long itemIndxId, String beginTime, String endTIme, String prop,String cdwz) {
        return dynamicTableMapper.getPredictDatas(tableName,suidaoId,firstIndexId,secondIndexId,itemIndxId,beginTime,endTIme,prop,cdwz);
    }
}




