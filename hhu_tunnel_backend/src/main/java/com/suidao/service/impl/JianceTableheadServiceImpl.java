package com.suidao.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.base.SqlConf;
import com.suidao.pojo.JianceFuhaoType;
import com.suidao.pojo.JianceTablehead;
import com.suidao.pojo.vo.DataVo;
import com.suidao.service.JianceFuhaoTypeService;
import com.suidao.service.JianceTableheadService;
import com.suidao.mapper.JianceTableheadMapper;
import com.suidao.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【jiance_tablehead(检测项目表头)】的数据库操作Service实现
* @createDate 2023-03-15 11:02:07
*/
@Service
@Slf4j
public class JianceTableheadServiceImpl extends ServiceImpl<JianceTableheadMapper, JianceTablehead>
    implements JianceTableheadService{

    @Resource
    JianceFuhaoTypeService fuhaoTypeService;
    @Override
    public List<JianceTablehead> getTableHead(Map map,Long id) {
        if (id.equals(0L) || id == null)
            throw new RuntimeException("必须选择对应的选项");
        QueryWrapper<JianceTablehead> jianceTableheadQueryWrapper = new QueryWrapper<>();
        jianceTableheadQueryWrapper.eq(SqlConf.parentId,id);
        List<JianceTablehead> headers = this.list(jianceTableheadQueryWrapper);
        QueryWrapper<JianceTablehead> wrapper = new QueryWrapper<>();
        //加入parentId为0的公共头节点,组合权重之类的。
        wrapper.eq(SqlConf.parentId,0);
        List<JianceTablehead> list = this.list(wrapper);
        headers.addAll(list);
        //遍历所有表头,获取对应的fuhao和type
        for (JianceTablehead header : headers) {
            //有符号和type位的才查询数据库
            if (!Constant.exculdeFuhaoAndTypeItem.contains(header.getProp())) {
                QueryWrapper<JianceFuhaoType> jianceFuhaoTypeQueryWrapper = new QueryWrapper<>();
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.firstIndexId, map.get("firstIndexId"));
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.secondIndexId, map.get("itemIndexId"));
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.itemId, header.getId());
                JianceFuhaoType fuhaoType = fuhaoTypeService.getOne(jianceFuhaoTypeQueryWrapper);
                if (fuhaoType == null) {
                    log.error(header + "没有存储fuhao和type");
                    continue;
                }
                header.setFuhao(fuhaoType.getFuhao());
                header.setType(fuhaoType.getType());
            }
        }
        return headers;
    }

    @Override
    public List<JianceTablehead> getTableHeadWithoutMap(Long id) {
        if (id.equals(0L) || id == null)
            throw new RuntimeException("必须选择对应的选项");
        QueryWrapper<JianceTablehead> jianceTableheadQueryWrapper = new QueryWrapper<>();
        jianceTableheadQueryWrapper.eq(SqlConf.parentId,id);
        List<JianceTablehead> headers = this.list(jianceTableheadQueryWrapper);
        QueryWrapper<JianceTablehead> wrapper = new QueryWrapper<>();
        //加入parentId为0的公共头节点,组合权重之类的。
        wrapper.eq(SqlConf.parentId,0);
        List<JianceTablehead> list = this.list(wrapper);
        headers.addAll(list);
        return headers;
    }
}




