package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName real_yuzhi
 */
@TableName(value ="real_yuzhi")
@Data
public class RealYuzhi implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long itemIndexId;

    /**
     * 
     */
    @TableField("real_data")
    private String realDataStr;

    /**
     * 
     */
    @TableField("yuzhi_data")
    private String yuzhiDataStr;

    @TableField(exist = false)
    private String[] realData;

    @TableField(exist = false)
    private String[] yuzhiData;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}