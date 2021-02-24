package com.bytedance.myfirewall.Enum;

import lombok.Getter;

@Getter
public enum IsDefaultRule {
    DEFAULT_RULE("off", "默认规则"),
    USER_DEFINED("on", "用户自定义规则");

    private String status;
    private String message;

    IsDefaultRule(String status, String msg) {
        this.status = status;
        this.message = msg;
    }
}
