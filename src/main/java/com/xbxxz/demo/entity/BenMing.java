package com.xbxxz.demo.entity;

import lombok.Data;

@Data
public class BenMing {
    private int id;
    private double prob;
    private double rate;
    private int level;
    private int round;

    /*
    id:
    青魔=0； 太荒=1； 蚀天=2； 墨魂=3
     */

    public BenMing(int id, int level) {
        this.id = id;
        this.level = level;
        switch(id){
            case 0:
                this.prob = 0.1;
                this.rate = 0.25 + level * 0.05;
                this.round = 1;
                break;
            case 1:
                this.prob = 0.02 + level * 0.005;
                this.rate = 1.5;
                this.round = 3;
                break;
            case 2:
                this.prob = 0.02 + level * 0.005;
                this.rate = 0.5;
                this.round = 3;
                break;
            case 3:
                this.prob = 0.008 + level * 0.002;
                this.rate = 0.1;
                this.round = 3;
                break;
            default:
                this.prob = 0;
                this.rate = 0;
                this.round = 0;
        }
    }
}
