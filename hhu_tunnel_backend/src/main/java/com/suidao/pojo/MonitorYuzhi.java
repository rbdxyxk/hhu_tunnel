package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName monitor_yuzhi
 */
@TableName(value ="monitor_yuzhi")
@Data
public class MonitorYuzhi implements Serializable {
    /**
     * 一级分类id
     */
    private Long firstIndexId;

    /**
     * 二级分类id
     */
    private Long secondIndexId;

    /**
     * 三级分类id
     */
    private Long itemIndexId;

    /**
     * 表头id
     */
    private Long tableHeadId;

    /**
     * 阈值
     */
    private String yuzhi;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}