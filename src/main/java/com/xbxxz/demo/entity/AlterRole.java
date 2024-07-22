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
@TableName("`role`")
@ApiModel(value="Role", description="角色表")
public class AlterRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private String newRoleName;
    private Role role;
}
