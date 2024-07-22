package com.xbxxz.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user`")
@ApiModel(value="User", description="用户表")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String gender;
    private String phone_number;
    private String city;
    private Date date_of_birth;
    private Integer age;
    private String status;
    private String role;
    private String avatar_url;
    private Timestamp last_login;
    private Integer login_count;
}
