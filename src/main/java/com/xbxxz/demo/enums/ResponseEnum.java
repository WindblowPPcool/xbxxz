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
    ADDROLEFAILD(-201, "角色名已存在"),
    DELROLEFAILD(-202, "角色名不存在"),
    ALTROLEFAILD(-203, "角色名不存在"),
    FINDROLEFAILD(-204, "角色名不存在"),
    FAILD(500, "失败");

    private Integer code;
    private String message;

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
