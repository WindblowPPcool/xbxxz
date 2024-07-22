package com.xbxxz.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`fight`")
@ApiModel(value="Fight", description="战斗模拟记录表")
public class Fight implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("userId")
    private Integer userId;
    @TableField("attackerName")
    private String attackerName;
    @TableField("defenderName")
    private String defenderName;
    @TableField("attackerBenMingId")
    private String attackerBenMingId;
    @TableField("attackerBenMingLevel")
    private int attackerBenMingLevel;
    @TableField("defenderBenMingId")
    private String defenderBenMingId;
    @TableField("defenderBenMingLevel")
    private int defenderBenMingLevel;
    @TableField("attackerSprChance")
    private double attackerSprChance;
    @TableField("defenderSprChance")
    private double defenderSprChance;
    @TableField("attackerEvaChance")
    private double attackerEvaChance;
    @TableField("defenderEvaChance")
    private double defenderEvaChance;
}
