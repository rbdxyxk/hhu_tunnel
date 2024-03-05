package com.suidao.mapper;

import com.suidao.pojo.RealYuzhi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zhangqingtian
* @description 针对表【real_yuzhi】的数据库操作Mapper
* @createDate 2023-03-30 16:29:45
* @Entity com.suidao.pojo.RealYuzhi
*/
public interface RealYuzhiMapper extends BaseMapper<RealYuzhi> {

    int insert(RealYuzhi realYuzhi);
}




