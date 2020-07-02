package com.aliboy.common.enums;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-02 15:06
 */
public enum  Sex {
    woman(0, "保密"),
    man(1, "男"),
    secret(2, "女");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
