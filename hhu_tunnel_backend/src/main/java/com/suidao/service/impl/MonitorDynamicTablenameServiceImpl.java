package com.suidao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.pojo.MonitorDynamicTablename;
import com.suidao.service.MonitorDynamicTablenameService;
import com.suidao.mapper.MonitorDynamicTablenameMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author zhangqingtian
* @description 针对表【monitor_dynamic_tablename】的数据库操作Service实现
* @createDate 2023-04-01 15:14:16
*/
@Service
public class MonitorDynamicTablenameServiceImpl extends ServiceImpl<MonitorDynamicTablenameMapper, MonitorDynamicTablename>
    implements MonitorDynamicTablenameService{
    @Resource
    MonitorDynamicTablenameMapper monitorDynamicTablenameMapper;
//    @Override
//    public Long getTotal(String tablename, Long suidaoId, Long firstIndexId, Long secondIndexId, Long itemIndxId) {
//        return monitorDynamicTablenameMapper.getTotal(tablename,  suidaoId,  firstIndexId,  secondIndexId, itemIndxId);
//    }
}




