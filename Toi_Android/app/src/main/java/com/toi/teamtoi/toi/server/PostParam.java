package com.toi.teamtoi.toi.server;

public class PostParam {
    private String key;
    private String value;

    public PostParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
