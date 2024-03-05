package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName dynamic_table
 */
@TableName(value ="jiance_dynamic_table")
@Data
public class JianceDynamicTable implements Serializable {
    /**
     * 
     */


    /**
     * 
     */


    private Long itemIndexId;

    /**
     * 
     */
    private String tableName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}