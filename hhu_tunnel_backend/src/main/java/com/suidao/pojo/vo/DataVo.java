package com.suidao.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DataVo {
    private Long id;
    /**
     *
     */
    private Long suidaoId;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rq;
    /**
     * 父节点id
     */
    private Long firstIndexId;

    private Long secondIndexId;

    /**
     * 展现数据的节点id
     */
    private Long itemIndexId;
    /**
     * 当前页数
     */
    private Long currentPage;
    /**
     * 每页展示条数
     */
    private Long limit;
}
