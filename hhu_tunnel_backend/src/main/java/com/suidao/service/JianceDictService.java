package com.suidao.service;

import com.suidao.pojo.JianceDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suidao.pojo.vo.Menue;

import java.util.List;

/**
* @author zhangqingtian
* @description 针对表【jiance_dict(检测项目字典表)】的数据库操作Service
* @createDate 2023-03-15 11:02:07
*/
public interface JianceDictService extends IService<JianceDict> {

    List<Menue> getAllDict();
}
