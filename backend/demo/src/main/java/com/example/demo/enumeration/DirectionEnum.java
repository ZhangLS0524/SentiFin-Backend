package com.example.demo.enumeration;

import java.util.Arrays;

public enum DirectionEnum {
    UP,
    DOWN;

    public static DirectionEnum fromString(String direction) {
        return DirectionEnum.valueOf(direction.toUpperCase());
    }

    public static boolean isValidDirection(String direction) {
        return Arrays.stream(DirectionEnum.values())
            .map(DirectionEnum::name)
            .anyMatch(d -> d.equals(direction.toUpperCase()));
    }
}
