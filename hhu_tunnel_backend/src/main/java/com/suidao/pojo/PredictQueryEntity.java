package com.suidao.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class PredictQueryEntity {
    String beginTime;
    String endTime;
    String predictTime;
    String cdwz;
    String firstIndexId;
    String secondIndexId;
    String suidaoId;
    String itemIndexId;
    int method;
    int type;
    String prop;
}
