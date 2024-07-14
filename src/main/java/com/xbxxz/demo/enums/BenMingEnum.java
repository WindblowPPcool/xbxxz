package com.xbxxz.demo.enums;

import com.xbxxz.demo.enums.i.IBenMingEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum BenMingEnum implements IBenMingEnum<Integer, Void, BenMingEnum> {

    SUCCESS(0, null);

    private Integer id;
    private Integer level;

    @Override
    public BenMingEnum get() {
        return this;
    }

    @Override
    public Integer id() {
        return this.id;
    }

    @Override
    public Void level(int i) {
        this.level = i;
        return null;
    }
}
