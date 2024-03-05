package com.suidao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.pojo.RealYuzhi;
import com.suidao.service.RealYuzhiService;
import com.suidao.mapper.RealYuzhiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zhangqingtian
* @description 针对表【real_yuzhi】的数据库操作Service实现
* @createDate 2023-03-30 16:29:45
*/
@Service
public class RealYuzhiServiceImpl extends ServiceImpl<RealYuzhiMapper, RealYuzhi>
    implements RealYuzhiService{

    @Resource
    RealYuzhiMapper realYuzhiMapper;
    @Override
    public int insertData(RealYuzhi realYuzhi) {
            return realYuzhiMapper.insert(realYuzhi);
    }
}




