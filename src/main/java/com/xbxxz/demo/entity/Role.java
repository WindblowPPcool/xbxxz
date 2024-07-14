package com.xbxxz.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`role`")
@ApiModel(value="Role", description="角色表")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private int atk;
    private int def;
    private int eva;
    private int spr;
    private int spe;
    private int hp;
    private int atk1;
    private int atk2;
    private int atk3;
    private int atk4;
    private int atk5;
    private int def1;
    private int def2;
    private int def3;
    private int def4;
    private int def5;
    private int occ;
    @TableField("petAtk")
    private int petAtk;
}
