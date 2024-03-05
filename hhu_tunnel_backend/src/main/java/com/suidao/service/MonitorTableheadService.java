package com.suidao.service;

import com.suidao.pojo.MonitorTablehead;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【monitor_tablehead】的数据库操作Service
* @createDate 2023-04-01 13:54:46
*/
public interface MonitorTableheadService extends IService<MonitorTablehead> {

    List<MonitorTablehead> getHead(Map map,Long itemIndexId);

    List<MonitorTablehead> getTableHeadWithoutMap(Long itemIndexId);
}
