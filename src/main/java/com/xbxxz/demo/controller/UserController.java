package com.xbxxz.demo.controller;

import com.xbxxz.demo.entity.User;
import com.xbxxz.demo.enums.ResponseEnum;
import com.xbxxz.demo.result.Result;
import com.xbxxz.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(value = "UserController", tags = "用户控制层")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result<Integer> register(@RequestBody User user) {
        int b = userService.register(user);
        if (b > 0) {
            return Result.ok(b);
        } else if (b == 0) {
            return Result.error(ResponseEnum.UserAlreadyExists);
        } else {
            return Result.error(ResponseEnum.UsernameIsNull);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<String> login(@RequestBody User user) {
        int b = userService.login(user);
        if (b > 0) {
            return Result.ok();
        }
        else if (b == 0) {
            return Result.error(ResponseEnum.PasswordMatchFailed);
        }
        else {
            return Result.error(ResponseEnum.UserNotExists);
        }
    }
}

