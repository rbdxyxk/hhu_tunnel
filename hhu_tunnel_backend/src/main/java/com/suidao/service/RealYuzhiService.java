package com.suidao.service;

import com.suidao.pojo.RealYuzhi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangqingtian
* @description 针对表【real_yuzhi】的数据库操作Service
* @createDate 2023-03-30 16:29:45
*/
public interface RealYuzhiService extends IService<RealYuzhi> {

    int insertData(RealYuzhi realYuzhi);
}
