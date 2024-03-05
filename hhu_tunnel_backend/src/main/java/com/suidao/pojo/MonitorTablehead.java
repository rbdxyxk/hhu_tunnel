package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName monitor_tablehead
 */
@TableName(value ="monitor_tablehead")
@Data
public class MonitorTablehead implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long parentId;

    /**
     * 
     */
    private String prop;

    /**
     * 
     */
    private String label;

    @TableField(exist = false)
    private Integer fuhao;

    @TableField(exist = false)
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}