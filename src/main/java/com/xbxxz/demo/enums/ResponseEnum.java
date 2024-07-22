package com.xbxxz.demo.enums;

import com.xbxxz.demo.enums.i.IResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ResponseEnum implements IResponseEnum<Integer, String, ResponseEnum> {

    SUCCESS(200, "成功"),
    NameAlreadyExists(-201, "角色名已存在"),
    NameDoesNotExists(-202, "角色名不存在"),
    NameAlterRejected(-203, "不能修改角色名为已存在的角色名"),
    UserNotExists(-204,"用户名不存在"),
    PasswordMatchFailed(-205,"密码错误"),
    UserAlreadyExists(-206,"已注册"),
    UsernameIsNull(-207,"用户名不能为空"),

    FAILED(500, "失败");

    private final Integer code;
    private final String message;

    @Override
    public ResponseEnum get() {
        return this;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
