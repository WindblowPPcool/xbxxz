package com.xbxxz.demo.entity;

import lombok.Data;

@Data
public class MultiFight {
    Fight fight;
    Integer loop;

    public MultiFight(Fight fight, Integer loop) {
        this.fight = fight;
        this.loop = loop;
    }
}
