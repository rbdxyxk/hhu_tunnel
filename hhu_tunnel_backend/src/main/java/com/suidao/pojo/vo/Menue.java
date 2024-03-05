package com.suidao.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class Menue {
    private Long id;
    private String label;
    private List<Menue> children;
    private Integer fuhao;
    private Integer type;
}
