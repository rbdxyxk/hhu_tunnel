package com.suidao.pojo;

public class Fx {
    double a0;
    double a1;
    double a2;

    public Fx(double a0, double a1, double a2) {
        this.a0 = a0;
        this.a1 = a1;
        this.a2 = a2;
    }

    // 传入时间戳
    public double cal(double timeStamp) {
        return a0 + a1 * timeStamp + a2 * Math.pow(timeStamp, 2.0);
    }
}

