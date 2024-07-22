package com.xbxxz.demo.controller;

import com.xbxxz.demo.entity.AlterRole;
import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.result.Result;
import com.xbxxz.demo.service.RoleService;
import com.xbxxz.demo.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Api(value = "RoleController",tags = "角色控制层")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    public Result<Role> addRole(@RequestBody Role role) {
        List<String> occupation = Arrays.asList("仙", "魔", "鬼", "释", "妖");
        if (!occupation.contains(role.getOcc())) {role.setOcc("无");}
        boolean b = roleService.addRole(role);
        if (b) {
            return Result.ok();
        }
        else {
            return Result.setResult(ResponseEnum.NameAlreadyExists);
        }
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除角色")
    public Result<Role> delRole(@RequestBody Role role) {
        boolean b = roleService.delRole(role);
        if (b) {
            return Result.ok();
        }
        else {
            return Result.setResult(ResponseEnum.NameAlreadyExists);
        }
    }

    @PostMapping("/alt")
    @ApiOperation(value = "修改角色")
    public Result<AlterRole> altRole(@RequestBody AlterRole alterRole) {
        Role role = alterRole.getRole();
        List<String> occupation = Arrays.asList("仙", "魔", "鬼", "释", "妖");
        if (!occupation.contains(role.getOcc())) {role.setOcc("无");}
        boolean b;
        if (alterRole.getNewRoleName().equals(role.getName())) {
            b=roleService.altRole(role);
        }
        else {
            Role lastRole = new Role();
            lastRole.setName(role.getName());
            role.setName(alterRole.getNewRoleName());
            b=roleService.addRole(role);
            if (b) {
                b=roleService.delRole(lastRole);
            }
        }
        if (b) {
            return Result.ok();
        }
        else {
            return Result.setResult(ResponseEnum.NameAlterRejected);
        }
    }

    @PostMapping("/find")
    @ApiOperation(value = "查找角色")
    public Result<Role> findRole(@RequestBody Role role) {
        Role r = roleService.findRole(role);
        if (r.getName() != null) {
            return Result.ok(r);
        }
        else {
            return Result.setResult(ResponseEnum.NameDoesNotExists);
        }
    }

    @PostMapping("/list")
    @ApiOperation(value = "角色列表")
    public Result<List<Role>> listRole(@RequestBody Role role) {
        List<Role> res = roleService.listRole(role.getUserId());
        return Result.ok(res);
    }
}
