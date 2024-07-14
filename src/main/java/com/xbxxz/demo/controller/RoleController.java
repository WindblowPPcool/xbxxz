package com.xbxxz.demo.controller;

import com.xbxxz.demo.entity.Role;
import com.xbxxz.demo.result.Result;
import com.xbxxz.demo.service.RoleService;
import com.xbxxz.demo.mapper.RoleMapper;
import com.xbxxz.demo.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Slf4j
@Api(value = "RoleController",tags = "角色控制层")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    public Result addRole(@RequestBody Role role) {
        boolean b = roleService.addRole(role);
        if (b) {
            return Result.ok();
        }
        else {
            return Result.error(ResponseEnum.ADDROLEFAILD);
        }
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除角色")
    public Result delRole(@RequestBody Role role) {
        boolean b = roleService.delRole(role);
        if (b) {
            return Result.ok();
        }
        else {
            return Result.error(ResponseEnum.DELROLEFAILD);
        }
    }

    @PostMapping("/alt")
    @ApiOperation(value = "修改角色")
    public Result altRole(@RequestBody Role role) {
        boolean b = roleService.altRole(role);
        if (b) {
            return Result.ok();
        }
        else {
            return Result.error(ResponseEnum.ALTROLEFAILD);
        }
    }

    @PostMapping("/find")
    @ApiOperation(value = "查找角色")
    public Result findRole(@RequestBody Role role) {
        Role r = roleService.findRole(role);
        if (r.getName() != null) {
            return Result.ok(r);
        }
        else {
            return Result.error(ResponseEnum.FINDROLEFAILD);
        }
    }
}
