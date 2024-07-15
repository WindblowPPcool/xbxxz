package com.xbxxz.demo.entity;

import lombok.Data;

@Data
public class Counter {
    private int attackerBmRound;               //本命剩余生效回合数
    private int defenderBmRound;
    private int attackerSprCount;                   //暴击次数
    private int defenderSprCount;
    private int attackerEvaCount;                   //闪避次数
    private int defenderEvaCount;
    private int attackerBmCount;               //本命触发次数
    private int defenderBmCount;
    private int attPetSprCount;                     //宠暴次数
    private int defPetSprCount;
    private int attPetEvaCount;                     //闪宠次数
    private int defPetEvaCount;
    private double roundCount;

    public Counter() {
        this.attackerBmRound = 0;
        this.defenderBmRound = 0;
        this.attackerSprCount = 0;
        this.defenderSprCount = 0;
        this.attackerEvaCount = 0;
        this.defenderEvaCount = 0;
        this.attackerBmCount = 0;
        this.defenderBmCount = 0;
        this.attPetSprCount = 0;
        this.defPetSprCount = 0;
        this.attPetEvaCount = 0;
        this.defPetEvaCount = 0;
        this.roundCount = 0;
    }

    public void attackerBmRound() {
        this.attackerBmRound--;
    }
    public void defenderBmRound() {
        this.defenderBmRound--;
    }
    public void attackerSpr(){
        this.attackerSprCount++;
    }
    public void defenderSpr(){
        this.defenderSprCount++;
    }
    public void attackerEva(){
        this.attackerEvaCount++;
    }
    public void defenderEva(){
        this.defenderEvaCount++;
    }
    public void attackerBm(){
        this.attackerBmCount++;
    }
    public void defenderBm(){
        this.defenderBmCount++;
    }
    public void attPetSpr(){
        this.attPetSprCount++;
    }
    public void defPetSpr(){
        this.defPetSprCount++;
    }
    public void attPetEva(){
        this.attPetEvaCount++;
    }
    public void defPetEva(){
        this.defPetEvaCount++;
    }
    public void round(){
        this.roundCount+=0.5;
    }
}
