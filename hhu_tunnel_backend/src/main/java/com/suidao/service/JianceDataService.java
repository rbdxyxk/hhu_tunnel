package com.suidao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suidao.base.Result;
import com.suidao.pojo.JianceData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suidao.pojo.vo.DataVo;

import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【jiance_data(检测数据表)】的数据库操作Service
* @createDate 2023-03-15 11:02:07
*/
public interface JianceDataService  {

    Map getTableData(Map map,Long currnt,Long limit);

    Map add(Map map,Long current,Long limit);

    Result editData(Map map);

    Result deleteData(List datas);

    Result getYuzhi(Map map);
}
