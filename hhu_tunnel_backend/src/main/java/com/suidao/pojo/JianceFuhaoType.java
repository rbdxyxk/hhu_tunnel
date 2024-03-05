package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName jiance_fuhao_type
 */
@TableName(value ="jiance_fuhao_type")
@Data
public class JianceFuhaoType implements Serializable {
    /**
     * 隧道类型id
     */
    @TableField("first_index_id")
    private Long firstIndexId;

    /**
     * 项目id
     */
    @TableField("second_index_id")
    private Long secondIndexId;

    /**
     * 
     */
    private Integer fuhao;


    private Integer type;

    /**
     * 表头id
     */
    @TableField("item_index_id")
    private Long itemIndexId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}