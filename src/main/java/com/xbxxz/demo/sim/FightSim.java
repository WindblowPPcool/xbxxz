package com.xbxxz.demo.sim;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xbxxz.demo.entity.Fight;
import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.mapper.RoleMapper;
import com.xbxxz.demo.entity.BenMing;
import com.xbxxz.demo.entity.Counter;

public class FightSim {
    private RoleMapper roleMapper;

    private final Role attacker;                    //人物
    private final Role defender;
    private final BenMing attackerBm;          //本命
    private final BenMing defenderBm;
    private int attackerHp;                         //双方剩余血量
    private int defenderHp;
    private Counter counter;
    private Fight fight;
    private String msg;
    private String info;

    public FightSim(Fight fight, RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
        this.fight = fight;
        QueryWrapper<Role> attqueryWrapper = new QueryWrapper<>();
        attqueryWrapper.eq("name", fight.getAttackerName());
        Role attacker = roleMapper.selectOne(attqueryWrapper);
        QueryWrapper<Role> defqueryWrapper = new QueryWrapper<>();
        defqueryWrapper.eq("name", fight.getDefenderName());
        Role defender = roleMapper.selectOne(defqueryWrapper);
        this.attacker = attacker;
        this.defender = defender;
        attackerBm = new BenMing(fight.getAttackerBenMingId(), fight.getAttackerBenMingLevel());
        defenderBm = new BenMing(fight.getDefenderBenMingId(), fight.getDefenderBenMingLevel());
        attackerHp = this.attacker.getHp();
        defenderHp = this.defender.getHp();
        counter = new Counter();
        msg = "";
        info = "";
    }

    public boolean attackerRound() {
        counter.round();
        int damage = 0;
        double tmp;
        boolean benMingSign;
        boolean attackerSign;
        boolean defenderSign;
        msg += "你出手：";
        //本体攻击
        //判断本命是否于本回合开始前触发
        double randomNumber = Math.random();
        benMingSign = randomNumber < this.attackerBm.getProb();
        msg += benMingSign ? "本命触发！" : "";
        if (benMingSign) {
            counter.setAttackerBmRound(attackerBm.getRound());
            counter.attackerBm();
        }
        //若当前本命有效
        if (counter.getAttackerBmRound() > 0) {
            counter.attackerBmRound();
            switch (this.attackerBm.getId()) {
                case 0:
                    tmp = this.attacker.getAtk() * this.attackerBm.getRate();
                    damage += Math.max(this.attacker.getAtk() - this.defender.getDef(), 0);
                    this.attackerHp = Math.min(this.attackerHp + (int) tmp, this.attacker.getHp());
                    msg += "回复自身攻击10%的生命值！仅生效一回合！";
                    break;
                case 1:
                    tmp = this.attacker.getAtk() * this.attackerBm.getRate() - this.defender.getDef();
                    damage += Math.max((int) tmp, 0);
                    msg += "增加自身50%攻击！剩余回合：" + counter.getAttackerBmRound() + "!";
                    break;
                case 2:
                    tmp = this.attacker.getAtk() - this.defender.getDef() * this.defenderBm.getRate();
                    damage += Math.max((int) tmp, 0);
                    msg += "降低敌方50%防御！剩余回合：" + counter.getAttackerBmRound() + "!";
                    break;
                case 3:
                    tmp = this.defender.getHp() * this.attackerBm.getRate();
                    damage += Math.max(this.attacker.getAtk() - this.defender.getDef(), 0) + (int) tmp;
                    msg += "敌方失去最大生命值的10%！剩余回合：" + counter.getAttackerBmRound() + "!";
                    break;
                default:
            }
        } else {
            damage += Math.max(this.attacker.getAtk() - this.defender.getDef(), 0);
        }
        randomNumber = Math.random();
        attackerSign = randomNumber < fight.getAttackerSprChance();
        if (attackerSign) {
            tmp = damage * 2;
            damage = (int) tmp;
            counter.attackerSpr();
        }
        //附加职业伤害
        switch (this.attacker.getOcc()) {
            case 1:
                tmp = this.defender.getOcc() == 4 ? this.attacker.getAtk1() * 1.3 - this.defender.getDef1() : this.attacker.getAtk1() - this.defender.getDef1();
                break;
            case 2:
                tmp = this.defender.getOcc() == 3 ? this.attacker.getAtk2() * 1.3 - this.defender.getDef2() : this.attacker.getAtk2() - this.defender.getDef2();
                break;
            case 3:
                tmp = this.defender.getOcc() == 5 ? this.attacker.getAtk3() * 1.3 - this.defender.getDef3() : this.attacker.getAtk3() - this.defender.getDef3();
                break;
            case 4:
                tmp = this.defender.getOcc() == 2 ? this.attacker.getAtk4() * 1.3 - this.defender.getDef4() : this.attacker.getAtk4() - this.defender.getDef4();
                break;
            case 5:
                tmp = this.defender.getOcc() == 1 ? this.attacker.getAtk5() * 1.3 - this.defender.getDef5() : this.attacker.getAtk5() - this.defender.getDef5();
                break;
            default:
                tmp = 0;
        }
        damage += Math.max((int) tmp, 0);
        randomNumber = Math.random();
        defenderSign = randomNumber < fight.getDefenderEvaChance();
        if (defenderSign) {
            counter.defenderEva();
            damage = 0;
            msg += "攻击被闪避！";
        } else {
            if (attackerSign) {
                msg += "暴击";
            }
            msg += "造成" + damage + "(+职业伤害" + Math.max((int) tmp, 0) + ")" + "伤害！";
        }
        //宠物攻击
        randomNumber = Math.random();
        attackerSign = randomNumber < fight.getAttackerSprChance();
        tmp = attackerSign ? this.attacker.getPetAtk() * 2 : this.attacker.getPetAtk();
        if (attackerSign) { counter.attPetSpr(); }
        randomNumber = Math.random();
        defenderSign = randomNumber < fight.getDefenderEvaChance();
        damage += defenderSign ? 0 : (int) tmp;
        if (defenderSign) {
            counter.defPetEva();
            msg += "宠物攻击被闪避！";
        } else {
            msg += "宠物";
            if (attackerSign) {
                msg += "暴击";
            }
            msg += "造成" + (int) tmp + "伤害！";
        }
        this.defenderHp -= damage;
        msg += "敌方受到" + damage + "伤害！敌方血量：" + this.defenderHp + ";你的血量：" + this.attackerHp + "！" + '\n';
        return this.defenderHp < 0;
    }

    public boolean defenderRound() {
        counter.round();
        int damage = 0;
        double tmp;
        boolean benMingSign;
        boolean attackerSign;
        boolean defenderSign;
        msg += "敌方出手：";
        //本体攻击
        //判断本命是否于本回合开始前触发
        double randomNumber = Math.random();
        benMingSign = randomNumber < this.defenderBm.getProb();
        msg += benMingSign ? "本命触发！" : "";
        if (benMingSign) {
            counter.setDefenderBmRound(defenderBm.getRound());
            counter.defenderBm();
        }
        //若当前本命有效
        if (counter.getDefenderBmRound() > 0) {
            counter.defenderBmRound();
            switch (this.defenderBm.getId()) {
                case 0:
                    tmp = this.defender.getAtk() * this.defenderBm.getRate();
                    damage += Math.max(this.defender.getAtk() - this.attacker.getDef(), 0);
                    this.defenderHp = Math.min(this.defenderHp + (int) tmp, this.defender.getHp());
                    msg += "敌方回复其攻击10%的生命值！仅生效一回合！";
                    break;
                case 1:
                    tmp = this.defender.getAtk() * this.defenderBm.getRate() - this.attacker.getDef();
                    damage += Math.max((int) tmp, 0);
                    msg += "增加敌方50%攻击！剩余回合：" + counter.getDefenderBmRound() + "!";
                    break;
                case 2:
                    tmp = this.defender.getAtk() - this.attacker.getDef() * this.attackerBm.getRate();
                    damage += Math.max((int) tmp, 0);
                    msg += "降低你50%防御！剩余回合：" + counter.getDefenderBmRound() + "!";
                    break;
                case 3:
                    tmp = this.attacker.getHp() * this.defenderBm.getRate();
                    damage += Math.max(this.defender.getAtk() - this.attacker.getDef(), 0) + (int) tmp;
                    msg += "你失去最大生命值的10%！剩余回合：" + counter.getDefenderBmCount() + "!";
                    break;
                default:
            }
        } else {
            damage += Math.max(this.defender.getAtk() - this.attacker.getDef(), 0);
        }
        randomNumber = Math.random();
        defenderSign = randomNumber < fight.getDefenderSprChance();
        if (defenderSign) {
            tmp = damage * 2;
            damage = (int) tmp;
            counter.defenderSpr();
        }
        //附加职业伤害
        switch (this.defender.getOcc()) {
            case 1:
                tmp = this.attacker.getOcc() == 4 ? this.defender.getAtk1() * 1.3 - this.attacker.getDef1() : this.defender.getAtk1() - this.attacker.getDef1();
                break;
            case 2:
                tmp = this.attacker.getOcc() == 3 ? this.defender.getAtk2() * 1.3 - this.attacker.getDef2() : this.defender.getAtk2() - this.attacker.getDef2();
                break;
            case 3:
                tmp = this.attacker.getOcc() == 5 ? this.defender.getAtk3() * 1.3 - this.attacker.getDef3() : this.defender.getAtk3() - this.attacker.getDef3();
                break;
            case 4:
                tmp = this.attacker.getOcc() == 2 ? this.defender.getAtk4() * 1.3 - this.attacker.getDef4() : this.defender.getAtk4() - this.attacker.getDef4();
                break;
            case 5:
                tmp = this.attacker.getOcc() == 1 ? this.defender.getAtk5() * 1.3 - this.attacker.getDef5() : this.defender.getAtk5() - this.attacker.getDef5();
                break;
            default:
                tmp = 0;
        }
        damage += Math.max((int) tmp, 0);
        randomNumber = Math.random();
        attackerSign = randomNumber < fight.getAttackerEvaChance();
        if (attackerSign) {
            counter.attackerEva();
            damage = 0;
            msg += "攻击被闪避！";
        } else {
            if (defenderSign) {
                msg += "暴击";
            }
            msg += "造成" + damage + "(+职业伤害" + Math.max((int) tmp, 0) + ")" + "伤害！";
        }
        //宠物攻击
        tmp = this.defender.getPetAtk() * 2;
        counter.defPetSpr();
        randomNumber = Math.random();
        attackerSign = randomNumber < fight.getAttackerEvaChance();
        damage += attackerSign ? 0 : (int) tmp;
        if (attackerSign) {
            counter.attPetEva();
            msg += "宠物攻击被闪避！";
        } else {
            msg += "宠物暴击造成" + (int) tmp + "伤害！";
        }
        this.attackerHp -= damage;
        msg += "你受到" + damage + "伤害！敌方血量：" + this.defenderHp + ";你的血量：" + this.attackerHp + "！" + '\n';
        return this.attackerHp < 0;
    }

    public void SimOnce() {
        if (attacker.getSpe() > defender.getSpe()) {
            for (int i = 1; i < 51; i++) {
                msg += "---第" + i + "回合---" + '\n';
                if (attackerRound()) {
                    break;
                }
                if (defenderRound()) {
                    break;
                }
            }
        } else {
            for (int i = 1; i < 51; i++) {
                msg += "---第" + i + "回合---" + '\n';
                if (defenderRound()) {
                    break;
                }
                if (attackerRound()) {
                    break;
                }
            }
        }
    }

    public double SimMul(Integer loop) {
        if (loop == 1) {
            this.SimOnce();
            if (defenderHp < 0) { return 1.0; } else { return 0; }
        }
        else {
            double winRound = 0;
            for (int i = 0; i < loop; i++) {
                this.SimOnce();
                if (defenderHp < 0) { winRound+=1; }
                attackerHp = attacker.getHp();
                defenderHp = defender.getHp();
                msg = "";
            }
            info = "战后统计：共模拟" + loop + "次," + (int) counter.getRoundCount() + "个回合，胜利" + winRound + "次。";
            info += '\n';
            info += "你暴击" + counter.getAttackerSprCount() + "次，闪避" + counter.getAttackerEvaCount() + "次，闪宠" + counter.getAttPetEvaCount() + "次，宠暴" + counter.getAttPetSprCount() + "次，本命触发" + counter.getAttackerBmCount() + "次。" + '\n';
            info += "暴击率：" + counter.getAttackerSprCount() / counter.getRoundCount() + "闪避率：" + counter.getAttackerEvaCount() / counter.getRoundCount() + "闪宠率：" + counter.getAttPetEvaCount() / counter.getRoundCount() + "宠暴率：" + counter.getAttPetSprCount() / counter.getRoundCount() + "本命率：" + counter.getAttackerBmCount() / counter.getRoundCount() + "。" + "剩余血量：" + attackerHp + '\n';
            info += "敌方暴击" + counter.getDefenderSprCount() + "次，闪避" + counter.getDefenderEvaCount() + "次，闪宠" + counter.getDefPetEvaCount() + "次，宠暴" + counter.getDefPetSprCount() + "次，本命触发" + counter.getDefenderBmCount() + "次。" + '\n';
            info += "暴击率：" + counter.getDefenderSprCount() / counter.getRoundCount() + "闪避率：" + counter.getDefenderEvaCount() / counter.getRoundCount() + "闪宠率：" + counter.getDefPetEvaCount() / counter.getRoundCount() + "宠暴率：" + counter.getDefPetSprCount() / counter.getRoundCount() + "本命率：" + counter.getDefenderBmCount() / counter.getRoundCount() + "。" + "剩余血量：" + defenderHp + '\n';
            //System.out.println(info);
            return winRound / counter.getRoundCount();
        }
    }

    public String showFightMsg() {
        System.out.println(msg);
        return msg;
    }

    public String showFinalMsg() {
        if (info == "") {
            info += "战后统计：" + "共" + (int) counter.getRoundCount() + "个回合！";

            if (this.attackerHp < 0) {
                info += "你被击杀，挑战失败！";
            } else if (this.defenderHp < 0) {
                info += "成功击败敌人，挑战成功！";
            } else {

                info += "时间用尽，挑战失败！";
            }
            info += '\n';

            info += "你暴击" + counter.getAttackerSprCount() + "次，闪避" + counter.getAttackerEvaCount() + "次，闪宠" + counter.getAttPetEvaCount() + "次，宠暴" + counter.getAttPetSprCount() + "次，本命触发" + counter.getAttackerBmCount() + "次。" + '\n';
            info += "暴击率：" + counter.getAttackerSprCount() / counter.getRoundCount() + "闪避率：" + counter.getAttackerEvaCount() / counter.getRoundCount() + "闪宠率：" + counter.getAttPetEvaCount() / counter.getRoundCount() + "宠暴率：" + counter.getAttPetSprCount() / counter.getRoundCount() + "本命率：" + counter.getAttackerBmCount() / counter.getRoundCount() + "。" + "剩余血量：" + attackerHp + '\n';
            info += "敌方暴击" + counter.getDefenderSprCount() + "次，闪避" + counter.getDefenderEvaCount() + "次，闪宠" + counter.getDefPetEvaCount() + "次，宠暴" + counter.getDefPetSprCount() + "次，本命触发" + counter.getDefenderBmCount() + "次。" + '\n';
            info += "暴击率：" + counter.getDefenderSprCount() / counter.getRoundCount() + "闪避率：" + counter.getDefenderEvaCount() / counter.getRoundCount() + "闪宠率：" + counter.getDefPetEvaCount() / counter.getRoundCount() + "宠暴率：" + counter.getDefPetSprCount() / counter.getRoundCount() + "本命率：" + counter.getDefenderBmCount() / counter.getRoundCount() + "。" + "剩余血量：" + defenderHp + '\n';
        }
        System.out.println(info);
        return info;
    }
}
