package com.suidao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.pojo.MonitorDict;
import com.suidao.pojo.vo.Menue;
import com.suidao.service.MonitorDictService;
import com.suidao.mapper.MonitorDictMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author zhangqingtian
* @description 针对表【monitor_dict(检测项目字典表)】的数据库操作Service实现
* @createDate 2023-04-01 12:37:14
*/
@Service
public class MonitorDictServiceImpl extends ServiceImpl<MonitorDictMapper, MonitorDict>
    implements MonitorDictService{
    @Override
    public List<Menue> getAllDict() {
        //首先获得所有字典数据
        List<MonitorDict> results = this.list();
        List<MonitorDict> list = results.stream().filter(monitorDict -> monitorDict.getParentId().equals(0L))
                .map(monitorDict -> {
                    monitorDict.setChildren(this.getChildren(monitorDict.getId(), results));
                    return monitorDict;
                }).collect(Collectors.toList());
        //将它转换为menue
        return list.stream().map(monitorDict -> {
            return BeanUtil.copyProperties(monitorDict,Menue.class);
        }).collect(Collectors.toList());
    }

    private List<MonitorDict> getChildren(Long id, List<MonitorDict> results) {
        return results.stream().filter(monitorDict -> monitorDict.getParentId().equals(id))
                .map(monitorDict -> {
                    monitorDict.setChildren(this.getChildren(monitorDict.getId(), results));
                    return monitorDict;
                }).collect(Collectors.toList());
    }

}




