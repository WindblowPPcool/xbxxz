package com.xbxxz.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbxxz.demo.entity.Fight;
import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.mapper.FightMapper;
import com.xbxxz.demo.mapper.RoleMapper;
import com.xbxxz.demo.service.FightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FightServiceImpl extends ServiceImpl<FightMapper, Fight> implements FightService {
    private FightMapper fightMapper;
    private RoleMapper roleMapper;

    public FightServiceImpl(FightMapper fightMapper, RoleMapper roleMapper) {
        this.fightMapper = fightMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean newFight(Fight fight) {
        QueryWrapper<Role> attackerQueryWrapper = new QueryWrapper<>();
        attackerQueryWrapper.eq("name", fight.getAttackerName());
        Long i = roleMapper.selectCount(attackerQueryWrapper);
        QueryWrapper<Role> defenderQueryWrapper = new QueryWrapper<>();
        defenderQueryWrapper.eq("name", fight.getDefenderName());
        Long j = roleMapper.selectCount(defenderQueryWrapper);
        if (i == 0 || j == 0) {
            return false;
        }
        if(fight.getAttackerEvaChance()==0){fight.setAttackerEvaChance(0.5);}
        if(fight.getAttackerSprChance()==0){fight.setAttackerSprChance(0.5);}
        if(fight.getDefenderEvaChance()==0){fight.setDefenderEvaChance(0.5);}
        if(fight.getDefenderSprChance()==0){fight.setDefenderSprChance(0.5);}
        if(fight.getAttackerBenMingLevel()==0){fight.setAttackerBenMingLevel(9);}
        if(fight.getDefenderBenMingLevel()==0){fight.setDefenderBenMingLevel(9);}
        fightMapper.insert(fight);
        return true;
    }
}
