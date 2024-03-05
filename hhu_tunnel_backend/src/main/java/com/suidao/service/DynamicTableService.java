package com.suidao.service;

import com.suidao.pojo.JianceDynamicTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【dynamic_table】的数据库操作Service
* @createDate 2023-03-30 14:32:00
*/
public interface DynamicTableService extends IService<JianceDynamicTable> {
    public List<Map<String,Object>> getDataByTableName(String name, Long begin, Long limit,Long suidaoId,Long firstIndexId,Long secondIndexId,Long itemIndexId);

    Long getTotal(String tablename, Long suidaoId, Long firstIndexId, Long secondIndexId, Long itemIndexId);

    Boolean insertDatas( Map map, String tablename);

    boolean updateTableData(Map map,String tablename);

    boolean deleteTableDataById(Long id, String tablename);

    List<Map> getPredictDatas(String tableName, Long suidaoId, Long firstIndexId, Long secondIndexId, Long itemIndxId, String beginTime, String endTIme, String prop,String cdwz);
}
