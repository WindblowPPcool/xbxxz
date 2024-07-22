package com.xbxxz.demo.controller;

import com.xbxxz.demo.entity.Fight;
import com.xbxxz.demo.entity.MultiFight;
import com.xbxxz.demo.result.Result;
import com.xbxxz.demo.service.FightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Slf4j
@Api(value = "FightController",tags = "战斗模拟控制层")
@RestController
@RequestMapping("/fight")
public class FightController {
    @Resource
    private FightService fightService;

    @PostMapping("/new")
    @ApiOperation(value = "新建战斗")
    public Result<String> newFight(@RequestBody MultiFight multiFight) {
        Fight fight = multiFight.getFight();
        if (fight.getUserId()==null) {fight.setUserId(0);}
        Integer loop = multiFight.getLoop();
        System.out.println(loop);
        String msg = fightService.newFight(fight, loop);
        if (msg != null) {
            return Result.ok(msg);
        }
        else {
            return Result.error();
        }
    }
}
