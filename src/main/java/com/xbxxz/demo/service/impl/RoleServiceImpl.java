package com.xbxxz.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.mapper.RoleMapper;
import com.xbxxz.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean addRole(Role role) {
        int i = roleMapper.insert(role);
        return i == 1;
    }

    @Override
    public boolean delRole(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", role.getName());
        int i = roleMapper.delete(queryWrapper);
        return i == 1;
    }

    @Override
    public boolean altRole(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", role.getName());
        Long i = roleMapper.selectCount(queryWrapper);
        if(i == 0) {
            return false;
        }
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", role.getName());
        if (role.getAtk() != 0) { updateWrapper.set("atk", role.getAtk()); }
        if (role.getDef() != 0) { updateWrapper.set("def", role.getDef()); }
        if (role.getEva() != 0) { updateWrapper.set("eva", role.getEva()); }
        if (role.getSpr() != 0) { updateWrapper.set("spr", role.getSpr()); }
        if (role.getSpe() != 0) { updateWrapper.set("spe", role.getSpe()); }
        if (role.getHp() != 0) { updateWrapper.set("hp", role.getHp()); }
        if (role.getAtk1() != 0) { updateWrapper.set("atk1", role.getAtk1()); }
        if (role.getAtk2() != 0) { updateWrapper.set("atk2", role.getAtk2()); }
        if (role.getAtk3() != 0) { updateWrapper.set("atk3", role.getAtk3()); }
        if (role.getAtk4() != 0) { updateWrapper.set("atk4", role.getAtk4()); }
        if (role.getAtk5() != 0) { updateWrapper.set("atk5", role.getAtk5()); }
        if (role.getDef1() != 0) { updateWrapper.set("def1", role.getDef1()); }
        if (role.getDef2() != 0) { updateWrapper.set("def2", role.getDef2()); }
        if (role.getDef3() != 0) { updateWrapper.set("def3", role.getDef3()); }
        if (role.getDef4() != 0) { updateWrapper.set("def4", role.getDef4()); }
        if (role.getDef5() != 0) { updateWrapper.set("def5", role.getDef5()); }
        if (role.getOcc() != 0) { updateWrapper.set("occ", role.getOcc()); }
        if (role.getPetAtk() != 0) { updateWrapper.set("petAtk", role.getPetAtk()); }
        roleMapper.update(null, updateWrapper);
        return true;
    }

    @Override
    public Role findRole(Role role) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", role.getName());
        Role r = new Role();
        r = roleMapper.selectOne(queryWrapper);
        return r;
    }
}
