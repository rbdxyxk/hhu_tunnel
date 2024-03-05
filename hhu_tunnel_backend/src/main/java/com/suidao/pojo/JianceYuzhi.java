package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName jiance_yuzhi
 */
@TableName(value ="jiance_yuzhi")
@Data
public class JianceYuzhi implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 隧道类型id
     */
    private Long firstIndexId;

    /**
     * 检测项目id
     */
    private Long itemIndexId;

    /**
     * 具体表头的id
     */
    private Long tableHeadId;

    /**
     * 对应表头的阈值,用,分隔
     */
    private String yuzhi;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}