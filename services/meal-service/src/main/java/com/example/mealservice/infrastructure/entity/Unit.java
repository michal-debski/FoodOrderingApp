package com.example.mealservice.infrastructure.entity;

import lombok.Getter;

@Getter
public enum Unit {

    KG(1000), GR(1), L(1000), ML(1);

    private final int factor;

    Unit(int factor) {
        this.factor = factor;
    }

    public int toBaseUnit(int value) {
        return value * this.factor;
    }
}
