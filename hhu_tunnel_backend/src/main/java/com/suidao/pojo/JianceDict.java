package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.suidao.base.BaseEntity;
import lombok.Data;

/**
 * 检测项目字典表
 * @TableName jiance_dict
 */
@TableName(value ="jiance_dict")
@Data
public class JianceDict  implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 
     */
    private String label;

    /**
     * 
     */
    private Long parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<JianceDict> children;
}