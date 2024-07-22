package com.xbxxz.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.mapper.RoleMapper;
import com.xbxxz.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean addRole(Role role) {
        try {
            int i = roleMapper.insert(role);
            return i == 1;
        } catch (Exception e) {
            return false;
        }
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
        roleMapper.update(role, updateWrapper);
        return true;
    }

    @Override
    public Role findRole(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", role.getName());
        Role r = new Role();
        r = roleMapper.selectOne(queryWrapper);
        return r;
    }

    public List<Role> listRole(Integer userId) {

        // 创建 QueryWrapper 列表
        List<Role> listRole = new ArrayList<>();
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        listRole = roleMapper.selectList(queryWrapper);

        return listRole;
    }
}
