package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName file
 */
@TableName(value ="file")
@Data
public class File implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 文件原始名称
     */
    private String originalname;

    /**
     * 新生成的名称
     */
    private String newname;

    /**
     * 
     */
    private String url;

    private Date createtime;

    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}