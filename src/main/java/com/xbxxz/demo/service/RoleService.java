package com.xbxxz.demo.service;

import com.xbxxz.demo.entity.Role;
import java.util.List;

public interface RoleService {
    boolean addRole(Role role);

    boolean delRole(Role role);

    boolean altRole(Role role);

    Role findRole(Role role);

    List<Role> listRole(Integer userId);
}
