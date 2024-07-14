package com.xbxxz.demo.enums.i;

public interface IBenMingEnum<K, V, C extends Enum> {
    C get();

    K id();

    V level(int i);
}
