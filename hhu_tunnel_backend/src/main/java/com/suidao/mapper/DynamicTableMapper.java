package com.suidao.mapper;

import com.suidao.pojo.JianceDynamicTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【dynamic_table】的数据库操作Mapper
* @createDate 2023-03-30 14:32:00
* @Entity com.suidao.pojo.DynamicTable
*/
public interface DynamicTableMapper extends BaseMapper<JianceDynamicTable> {
    List<Map<String,Object>> getDataByTableName(@Param("tableName")String tableName,
                                                @Param("begin")Long begin,
                                                @Param("limit")Long limit,
                                                @Param("suidaoId") Long suidaoId,
                                                @Param("firstIndexId") Long firstIndexId,
                                                @Param("secondIndexId") Long secondIndexId,
                                                @Param("itemIndexId") Long itemIndexId );
    Long getTotal(@Param("tableName")String tableName,
                  @Param("suidaoId") Long suidaoId,
                  @Param("firstIndexId") Long firstIndexId,
                  @Param("secondIndexId") Long secondIndexId,
                  @Param("itemIndexId") Long itemIndexId);

    Boolean insertDatas(@Param("params") Map map, @Param("tablename") String tablename);

    boolean updateTableData(@Param("params") Map map,@Param("tablename") String tablename);

    boolean deleteTableDataById(@Param("id") Long id,@Param("tablename") String tablename);

    List<Map> getPredictDatas(@Param("tableName") String tableName, @Param("suidaoId") Long suidaoId, @Param("firstIndexId") Long firstIndexId,
                              @Param("secondIndexId") Long secondIndexId, @Param("itemIndexId") Long itemIndxId, @Param("beginTime") String beginTime,
                              @Param("endTime") String endTIme,@Param("prop") String prop,@Param("cdwz") String cdwz);
}




