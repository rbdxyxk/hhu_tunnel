package com.suidao.service;

import com.suidao.pojo.MonitorDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suidao.pojo.vo.Menue;

import java.util.List;

/**
* @author zhangqingtian
* @description 针对表【monitor_dict(检测项目字典表)】的数据库操作Service
* @createDate 2023-04-01 12:37:14
*/
public interface MonitorDictService extends IService<MonitorDict> {

    List<Menue> getAllDict();
}
