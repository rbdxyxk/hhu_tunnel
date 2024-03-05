package com.suidao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suidao.base.BaseEntity;
import lombok.Data;

/**
 * 检测数据表
 * @TableName jiance_data
 */
@TableName(value ="jiance_data")
@Data
public class JianceData  implements Serializable  {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 
     */
    private Long suidaoId;
    /**
     * 父节点id
     */
    private Long parentid;
    /**
     * 当前节点id
     */
    @TableField(value = "currentId")
    private Long currentId;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rq;


    /**
     * 测点编号
     */
    private String cdbh;

    /**
     * 距开挖面距量
距开挖面距量

     */
    private Double jkwm;

    /**
     * 单层厚度
     */
    private Double dchd;

    /**
     * 双层厚度
     */
    private Double schd;

    /**
     * 搭接宽度

     */
    private Double djkd;

    /**
     * 其他要求

     */
    private String qtyq;

    /**
     * 安全状态
     */
    private String auzt;



    /**
     * 剪切状态下的粘合性
     */
    private String jqnhx;

    /**
     * 黏结剥离强度
     */
    private String njblqd;

    /**
     * 与混凝土黏结强度
     */
    private String yhntnjqd;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}