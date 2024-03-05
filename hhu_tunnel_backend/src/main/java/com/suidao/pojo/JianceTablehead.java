package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.suidao.base.BaseEntity;
import lombok.Data;

/**
 * 检测项目表头
 * @TableName jiance_tablehead
 */
@TableName(value ="jiance_tablehead")
@Data
public class JianceTablehead   implements Serializable {

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