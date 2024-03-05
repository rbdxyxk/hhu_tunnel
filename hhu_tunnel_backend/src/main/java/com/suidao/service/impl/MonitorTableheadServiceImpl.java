package com.suidao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.base.SqlConf;
import com.suidao.base.SysConf;
import com.suidao.pojo.JianceFuhaoType;
import com.suidao.pojo.JianceTablehead;
import com.suidao.pojo.MonitorFuhaoType;
import com.suidao.pojo.MonitorTablehead;
import com.suidao.service.MonitorFuhaoTypeService;
import com.suidao.service.MonitorTableheadService;
import com.suidao.mapper.MonitorTableheadMapper;
import com.suidao.utils.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zhangqingtian
* @description 针对表【monitor_tablehead】的数据库操作Service实现
* @createDate 2023-04-01 13:54:46
*/
@Service
public class MonitorTableheadServiceImpl extends ServiceImpl<MonitorTableheadMapper, MonitorTablehead>
    implements MonitorTableheadService{
    @Resource
    MonitorFuhaoTypeService monitorFuhaoTypeService;
    @Override
    public List<MonitorTablehead> getHead(Map map,Long id) {
        QueryWrapper<MonitorTablehead> commonHead = new QueryWrapper<>();
        commonHead.eq(SqlConf.parentId,-1);
        List<MonitorTablehead> headers = this.list(commonHead);;
        QueryWrapper<MonitorTablehead> jianceTableheadQueryWrapper = new QueryWrapper<>();
        jianceTableheadQueryWrapper.eq(SqlConf.parentId,id);
        headers.addAll(this.list(jianceTableheadQueryWrapper));
        QueryWrapper<MonitorTablehead> wrapper = new QueryWrapper<>();
        //加入parentId为0的公共头节点,组合权重之类的。
        wrapper.eq(SqlConf.parentId,0);
        List<MonitorTablehead> list = this.list(wrapper);
        headers.addAll(list);
        for (MonitorTablehead header : headers) {
            //有符号和type位的才查询数据库
            if (!Constant.exculdeFuhaoAndTypeItem.contains(header.getProp())) {
                QueryWrapper<MonitorFuhaoType> jianceFuhaoTypeQueryWrapper = new QueryWrapper<>();
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.firstIndexId, map.get("firstIndexId"));
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.secondIndexId,map.get(SysConf.secondIndexId));
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.itemId, map.get("itemIndexId"));
                jianceFuhaoTypeQueryWrapper.eq(SqlConf.tableHeadId, header.getId());
                MonitorFuhaoType fuhaoType = monitorFuhaoTypeService.getOne(jianceFuhaoTypeQueryWrapper);
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
    public List<MonitorTablehead> getTableHeadWithoutMap(Long itemIndexId) {
        QueryWrapper<MonitorTablehead> commonHead = new QueryWrapper<>();
        commonHead.eq(SqlConf.parentId,-1);
        List<MonitorTablehead> headers = this.list(commonHead);;
        QueryWrapper<MonitorTablehead> jianceTableheadQueryWrapper = new QueryWrapper<>();
        jianceTableheadQueryWrapper.eq(SqlConf.parentId,itemIndexId);
        headers.addAll(this.list(jianceTableheadQueryWrapper));
        QueryWrapper<MonitorTablehead> wrapper = new QueryWrapper<>();
        //加入parentId为0的公共头节点,组合权重之类的。
        wrapper.eq(SqlConf.parentId,0);
        List<MonitorTablehead> list = this.list(wrapper);
        headers.addAll(list);
        return headers;
    }
}




