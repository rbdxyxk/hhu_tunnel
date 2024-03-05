package com.suidao.service;

import com.suidao.pojo.JianceTablehead;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suidao.pojo.vo.DataVo;

import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【jiance_tablehead(检测项目表头)】的数据库操作Service
* @createDate 2023-03-15 11:02:07
*/
public interface JianceTableheadService extends IService<JianceTablehead> {

    List<JianceTablehead> getTableHead(Map map,Long id);

    List<JianceTablehead> getTableHeadWithoutMap( Long itemIndexId);
}
