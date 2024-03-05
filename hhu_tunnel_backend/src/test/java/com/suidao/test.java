package com.suidao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suidao.base.SqlConf;
import com.suidao.pojo.JianceFuhaoType;
import com.suidao.service.JianceFuhaoTypeService;
import com.suidao.service.impl.JianceFuhaoTypeServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class test {
    @Autowired
    JianceFuhaoTypeServiceImpl jianceFuhaoTypeService;

    @Test
    public void test(){
        QueryWrapper<JianceFuhaoType> jianceFuhaoTypeQueryWrapper = new QueryWrapper<>();
        jianceFuhaoTypeQueryWrapper.eq(SqlConf.parentId,1);
        List<JianceFuhaoType> list = jianceFuhaoTypeService.list(jianceFuhaoTypeQueryWrapper);
        for (JianceFuhaoType jianceFuhaoType : list) {
            System.out.println(jianceFuhaoType);
        }
    }
}
