package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName monitor_dynamic_tablename
 */
@TableName(value ="monitor_dynamic_table")
@Data
public class MonitorDynamicTablename implements Serializable {
    /**
     * 
     */
//    private Long firstIndexId;
//
//    private Long secondIndexId;

    private Long itemIndexId;

    /**
     * 
     */

    /**
     * 
     */
    private String tableName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}