package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName monitor_fuhao_type
 */
@TableName(value ="monitor_fuhao_type")
@Data
public class MonitorFuhaoType implements Serializable {
    /**
     * 一级分类(施工期...)
     */
    private Long firstIndexId;

    /**
     * 二级分类(变形监测...)
     */
    private Long secondIndexId;

    /**
     * 具体选择的监测项目id
     */
    private Long itemIndexId;

    /**
     * 表头id
     */
    private Long tableHeadId;

    /**
     * 符号
     */
    private Integer fuhao;

    /**
     * 类型
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}