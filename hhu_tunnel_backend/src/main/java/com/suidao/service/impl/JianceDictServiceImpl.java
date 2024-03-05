package com.suidao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.base.SqlConf;
import com.suidao.pojo.JianceDict;
import com.suidao.pojo.vo.Menue;
import com.suidao.service.JianceDictService;
import com.suidao.mapper.JianceDictMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author zhangqingtian
* @description 针对表【jiance_dict(检测项目字典表)】的数据库操作Service实现
* @createDate 2023-03-15 11:02:07
*/
@Service
public class JianceDictServiceImpl extends ServiceImpl<JianceDictMapper, JianceDict>
    implements JianceDictService{
    @Override
    public List<Menue> getAllDict() {
        //首先获得所有字典数据
        List<JianceDict> results = this.list();
        List<JianceDict> jianceList = results.stream().filter(jianceDict -> jianceDict.getParentId().equals(0L))
                .map(jianceDict -> {
                    jianceDict.setChildren(this.getChildren(jianceDict.getId(), results));
                    return jianceDict;
                }).collect(Collectors.toList());
        //将它转换为menue
        return jianceList.stream().map(jianceDict -> {
            return BeanUtil.copyProperties(jianceDict,Menue.class);
        }).collect(Collectors.toList());
    }

    private List<JianceDict> getChildren(Long id, List<JianceDict> results) {
        return results.stream().filter(jianceDict -> jianceDict.getParentId().equals(id))
                .map(jianceDict -> {
                    jianceDict.setChildren(this.getChildren(jianceDict.getId(), results));
                    return jianceDict;
                }).collect(Collectors.toList());
    }


}




