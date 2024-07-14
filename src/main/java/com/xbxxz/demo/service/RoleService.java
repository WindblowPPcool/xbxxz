package com.xbxxz.demo.service;


import com.xbxxz.demo.entity.Role;

public interface RoleService {
    boolean addRole(Role role);

    boolean delRole(Role role);

    boolean altRole(Role role);

    Role findRole(Role role);
}
