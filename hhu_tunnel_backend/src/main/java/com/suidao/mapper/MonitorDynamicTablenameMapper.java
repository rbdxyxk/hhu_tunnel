package com.suidao.mapper;

import com.suidao.pojo.MonitorDynamicTablename;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zhangqingtian
* @description 针对表【monitor_dynamic_tablename】的数据库操作Mapper
* @createDate 2023-04-01 15:14:16
* @Entity com.suidao.pojo.MonitorDynamicTablename
*/
public interface MonitorDynamicTablenameMapper extends BaseMapper<MonitorDynamicTablename> {

    Long getTotal(String tablename, Long suidaoId, Long firstIndexId, Long secondIndexId, Long itemIndxId);
}




