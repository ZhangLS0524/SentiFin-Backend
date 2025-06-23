package com.example.demo.enumeration;

public enum ChatEnum {
    PRIVATE, 
    GROUP;

    public static ChatEnum fromString(String chatType) {
        return ChatEnum.valueOf(chatType.toUpperCase());
    }
}
