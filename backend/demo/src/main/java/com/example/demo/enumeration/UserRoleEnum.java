package com.example.demo.enumeration;

public enum UserRoleEnum {
    USER,
    ADMIN;

    public static UserRoleEnum fromString(String role) {
        return UserRoleEnum.valueOf(role.toUpperCase());
    }
}
