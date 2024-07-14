package com.xbxxz.demo.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xbxxz.demo.entity.Fight;
import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.mapper.RoleMapper;

public class FightSim {

    private RoleMapper roleMapper;

    private final Role attacker;
    private final Role defender;
    private int attackerBenMingRound;
    private int defenderBenMingRound;
    private int attackerHitPoint;
    private int defenderHitPoint;
    private final double attackerCriticalHitChance;
    private final double defenderCriticalHitChance;
    private final double attackerEvasionChance;
    private final double defenderEvasionChance;
    private int attackerCriticalHitCount;
    private int defenderCriticalHitCount;
    private int attackerEvasionCount;
    private int defenderEvasionCount;
    private int attackerBenMingCount;
    private int defenderBenMingCount;
    private int attackerPetCriticalHitCount;
    private int defenderPetCriticalHitCount;
    private int attackerPetEvasionCount;
    private int defenderPetEvasionCount;
    private double roundCount;

    public FightSim(Fight fight) {
        QueryWrapper<Role> attqueryWrapper = new QueryWrapper<>();
        attqueryWrapper.eq("name", fight.getAttackerName());
        Role attacker = roleMapper.selectOne(attqueryWrapper);
        QueryWrapper<Role> defqueryWrapper = new QueryWrapper<>();
        defqueryWrapper.eq("name", fight.getAttackerName());
        Role defender = roleMapper.selectOne(defqueryWrapper);
        this.attacker = attacker;
        this.defender = defender;
        this.attackerBenMingRound = 0;
        this.defenderBenMingRound = 0;
        this.attackerHitPoint = this.attacker.getHp();
        this.defenderHitPoint = this.defender.getHp();
        this.attackerCriticalHitChance = fight.getAttackerSprChance();
        this.defenderCriticalHitChance = fight.getDefenderSprChance();
        this.attackerEvasionChance = fight.getAttackerEvaChance();
        this.defenderEvasionChance = fight.getDefenderEvaChance();
        this.attackerCriticalHitCount = 0;
        this.defenderCriticalHitCount = 0;
        this.attackerEvasionCount = 0;
        this.defenderEvasionCount = 0;
        this.attackerBenMingCount = 0;
        this.defenderBenMingCount = 0;
        this.attackerPetCriticalHitCount = 0;
        this.defenderPetCriticalHitCount = 0;
        this.attackerPetEvasionCount = 0;
        this.defenderPetEvasionCount = 0;
        this.roundCount = 0;
    }


    public boolean attackerRound() {
        this.roundCount += 0.5;
        int damage = 0;
        double tmp;
        boolean benMingSign;
        boolean attackerSign;
        boolean defenderSign;
        String msg = "你的回合：";
        //本体攻击
        //判断本命是否于本回合开始前触发
        double randomNumber = Math.random();
        benMingSign = randomNumber < this.attacker.getBenMing().prob();
        msg += benMingSign ? "本命触发！" : "";
        if (benMingSign){ this.attackerBenMingRound = this.attacker.getBenMing().round(); this.attackerBenMingCount++; }
        //若当前本命有效
        if (this.attackerBenMingRound > 0){
            this.attackerBenMingRound--;
            switch (this.attacker.getBenMing()){
                case SHITIAN:
                    tmp = this.attacker.getATT0() - this.defender.getDEF0() * this.defender.getBenMing().rate();
                    damage += Math.max((int)tmp, 0);msg += "降低敌方50%防御！剩余回合：" + this.attackerBenMingRound + "!";break;
                case TAIHUANG:
                    tmp = this.attacker.getATT0() * this.attacker.getBenMing().rate() - this.defender.getDEF0();
                    damage += Math.max((int)tmp, 0);msg += "增加自身50%攻击！剩余回合：" + this.attackerBenMingRound + "!";break;
                case MOHUN:
                    tmp = this.defender.getHIT() * this.attacker.getBenMing().rate();
                    damage += Math.max(this.attacker.getATT0() - this.defender.getDEF0(), 0) + (int)tmp;msg += "敌方失去最大生命值的10%！剩余回合：" + this.attackerBenMingRound + "!";break;
                case QINGMO:
                    tmp = this.attacker.getATT0() * this.attacker.getBenMing().rate();
                    damage += Math.max(this.attacker.getATT0() - this.defender.getDEF0(), 0);
                    this.attackerHitPoint = Math.min(this.attackerHitPoint + (int) tmp, this.attacker.getHIT());
                    msg += "回复自身攻击10%的生命值！仅生效一回合！";break;
            }
        }
        else {
            damage += Math.max(this.attacker.getATT0() - this.defender.getDEF0(), 0);
        }
        randomNumber = Math.random();
        attackerSign = randomNumber < this.attackerCriticalHitChance;
        if (attackerSign){
            tmp = damage * 2;
            damage = (int)tmp;
        }
        //附加职业伤害
        switch (this.attacker.getOCC()){
            case 1:
                tmp = this.defender.getOCC() == 4 ? this.attacker.getATT1() * 1.3 - this.defender.getDEF1() : this.attacker.getATT1() - this.defender.getDEF1();break;
            case 2:
                tmp = this.defender.getOCC() == 3 ? this.attacker.getATT2() * 1.3 - this.defender.getDEF2() : this.attacker.getATT2() - this.defender.getDEF2();break;
            case 3:
                tmp = this.defender.getOCC() == 5 ? this.attacker.getATT3() * 1.3 - this.defender.getDEF3() : this.attacker.getATT3() - this.defender.getDEF3();break;
            case 4:
                tmp = this.defender.getOCC() == 2 ? this.attacker.getATT4() * 1.3 - this.defender.getDEF4() : this.attacker.getATT4() - this.defender.getDEF4();break;
            case 5:
                tmp = this.defender.getOCC() == 1 ? this.attacker.getATT5() * 1.3 - this.defender.getDEF5() : this.attacker.getATT5() - this.defender.getDEF5();break;
            default:
                tmp = 0;
        }
        damage += Math.max((int)tmp, 0);
        randomNumber = Math.random();
        defenderSign = randomNumber < this.defenderEvasionChance;
        if (defenderSign){
            this.defenderEvasionCount++;
            damage = 0;
            msg += "攻击被闪避！";
        }
        else{
            if (attackerSign){
                this.attackerCriticalHitCount++;
                msg += "暴击";
            }
            msg += "造成" + damage + "(+职业伤害" + Math.max((int)tmp, 0) +")" + "伤害！";
        }
        //宠物攻击
        randomNumber = Math.random();
        attackerSign = randomNumber < this.attackerCriticalHitChance;
        tmp = attackerSign ? this.attacker.getPetATT() * 2 : this.attacker.getPetATT();
        randomNumber = Math.random();
        defenderSign = randomNumber < this.defenderEvasionChance;
        damage += defenderSign ? 0 : (int)tmp;
        if (defenderSign){
            this.defenderPetEvasionCount++;
            msg += "宠物攻击被闪避！";
        }
        else {
            msg += "宠物";
            if (attackerSign){
                this.attackerPetCriticalHitCount++;
                msg += "暴击";
            }
            msg += "造成" + (int)tmp + "伤害！";
        }
        this.defenderHitPoint -= damage;
        msg += "敌方受到" + damage + "伤害！敌方血量：" + this.defenderHitPoint + ";你的血量：" + this.attackerHitPoint + "！";
        //System.out.println(msg);
        return this.defenderHitPoint < 0;
    }

    public boolean defenderRound() {
        this.roundCount += 0.5;
        int damage = 0;
        double tmp;
        boolean benMingSign;
        boolean attackerSign;
        boolean defenderSign;
        String msg = "敌方的回合：";
        //本体攻击
        //判断本命是否于本回合开始前触发
        double randomNumber = Math.random();
        benMingSign = randomNumber < this.defender.getBenMing().prob();
        msg += benMingSign ? "本命触发！" : "";
        if (benMingSign){ this.defenderBenMingRound = this.defender.getBenMing().round(); this.defenderBenMingCount++; }
        //若当前本命有效
        if (this.defenderBenMingRound > 0){
            this.defenderBenMingRound--;
            switch (this.defender.getBenMing()){
                case SHITIAN:
                    tmp = this.defender.getATT0() - this.attacker.getDEF0() * this.attacker.getBenMing().rate();
                    damage += Math.max((int)tmp, 0);msg += "降低你50%防御！剩余回合：" + this.attackerBenMingRound + "!";break;
                case TAIHUANG:
                    tmp = this.defender.getATT0() * this.defender.getBenMing().rate() - this.attacker.getDEF0();
                    damage += Math.max((int)tmp, 0);msg += "增加敌方50%攻击！剩余回合：" + this.defenderBenMingRound + "!";break;
                case MOHUN:
                    tmp = this.attacker.getHIT() * this.defender.getBenMing().rate();
                    damage += Math.max(this.defender.getATT0() - this.attacker.getDEF0(), 0) + (int)tmp;msg += "你失去最大生命值的10%！剩余回合：" + this.defenderBenMingRound + "!";break;
                case QINGMO:
                    tmp = this.defender.getATT0() * this.defender.getBenMing().rate();
                    damage += Math.max(this.defender.getATT0() - this.attacker.getDEF0(), 0);
                    this.defenderHitPoint = Math.min(this.defenderHitPoint + (int) tmp, this.defender.getHIT());
                    msg += "敌方回复其攻击10%的生命值！仅生效一回合！";break;
            }
        }
        else {
            damage += Math.max(this.defender.getATT0() - this.attacker.getDEF0(), 0);
        }
        randomNumber = Math.random();
        defenderSign = randomNumber < this.defenderCriticalHitChance;
        if (defenderSign){
            tmp = damage * 2;
            damage = (int)tmp;
        }
        //附加职业伤害
        switch (this.defender.getOCC()){
            case 1:
                tmp = this.attacker.getOCC() == 4 ? this.defender.getATT1() * 1.3 - this.attacker.getDEF1() : this.defender.getATT1() - this.attacker.getDEF1();break;
            case 2:
                tmp = this.attacker.getOCC() == 3 ? this.defender.getATT2() * 1.3 - this.attacker.getDEF2() : this.defender.getATT2() - this.attacker.getDEF2();break;
            case 3:
                tmp = this.attacker.getOCC() == 5 ? this.defender.getATT3() * 1.3 - this.attacker.getDEF3() : this.defender.getATT3() - this.attacker.getDEF3();break;
            case 4:
                tmp = this.attacker.getOCC() == 2 ? this.defender.getATT4() * 1.3 - this.attacker.getDEF4() : this.defender.getATT4() - this.attacker.getDEF4();break;
            case 5:
                tmp = this.attacker.getOCC() == 1 ? this.defender.getATT5() * 1.3 - this.attacker.getDEF5() : this.defender.getATT5() - this.attacker.getDEF5();break;
            default:
                tmp = 0;
        }
        damage += Math.max((int)tmp, 0);
        randomNumber = Math.random();
        attackerSign = randomNumber < this.attackerEvasionChance;
        if (attackerSign){
            this.attackerEvasionCount++;
            damage = 0;
            msg += "攻击被闪避！";
        }
        else{
            if (defenderSign){
                this.defenderCriticalHitCount++;
                msg += "暴击";
            }
            msg += "造成" + damage + "(+职业伤害" + Math.max((int)tmp, 0) +")" + "伤害！";
        }
        //宠物攻击
        randomNumber = Math.random();
        defenderSign = randomNumber < this.defenderCriticalHitChance;
        tmp = attackerSign ? this.defender.getPetATT() * 2 : this.defender.getPetATT();
        randomNumber = Math.random();
        attackerSign = randomNumber < this.attackerEvasionChance;
        damage += defenderSign ? 0 : (int)tmp;
        if (attackerSign){
            this.attackerPetEvasionCount++;
            msg += "宠物攻击被闪避！";
        }
        else {
            msg += "宠物";
            if (defenderSign){
                this.defenderPetCriticalHitCount++;
                msg += "暴击";
            }
            msg += "造成" + (int)tmp + "伤害！";
        }
        this.attackerHitPoint -= damage;
        msg += "你受到" + damage + "伤害！敌方血量：" + this.defenderHitPoint + ";你的血量：" + this.attackerHitPoint + "！";
        //System.out.println(msg);
        return this.attackerHitPoint < 0;
    }

    public void showFinalMsg() {
        String msg = "战后统计：" + "共" + (int)this.roundCount + "个回合！";

        if (this.attackerHitPoint < 0){
            msg += "你被击杀，挑战失败！";
        }
        else if (this.defenderHitPoint < 0){
            msg += "成功击败敌人，挑战成功！";
        }
        else {

            msg += "时间用尽，挑战失败！";
        }
        msg += '\n';

        msg += "你暴击" + this.attackerCriticalHitCount + "次，闪避" + this.attackerEvasionCount + "次，闪宠" + this.attackerPetEvasionCount + "次，宠暴" + this.attackerPetCriticalHitCount + "次，本命触发" + this.attackerBenMingCount + "次。" + '\n';
        msg += "暴击率：" + this.attackerCriticalHitCount / this.roundCount + "闪避率：" + this.attackerEvasionCount / this.roundCount + "闪宠率：" + this.attackerPetEvasionCount / this.roundCount + "宠暴率：" + this.attackerPetCriticalHitCount / this.roundCount + "本命率：" + this.attackerBenMingCount / this.roundCount + "。" + "剩余血量：" + this.attackerHitPoint + '\n';
        msg += "敌方暴击" + this.defenderCriticalHitCount + "次，闪避" + this.defenderEvasionCount + "次，闪宠" + this.defenderPetEvasionCount + "次，宠暴" + this.defenderPetCriticalHitCount + "次，本命触发" + this.defenderBenMingCount + "次。" + '\n';
        msg += "暴击率：" + this.defenderCriticalHitCount / this.roundCount + "闪避率：" + this.defenderEvasionCount / this.roundCount + "闪宠率：" + this.defenderPetEvasionCount / this.roundCount + "宠暴率：" + this.defenderPetCriticalHitCount / this.roundCount + "本命率：" + this.defenderBenMingCount / this.roundCount + "。" + "剩余血量：" + this.defenderHitPoint + '\n';

        System.out.println(msg);
    }
}
