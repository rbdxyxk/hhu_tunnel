package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.suidao.pojo.vo.Menue;
import lombok.Data;

/**
 * 检测项目字典表
 * @TableName monitor_dict
 */
@TableName(value ="monitor_dict")
@Data
public class MonitorDict implements Serializable {
    /**
     * 
     */
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
    private List<MonitorDict> children;
}