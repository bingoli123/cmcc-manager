package com.cmcc.enums;

/**
 * <p>Create Time: 2019年05月10日 15:18          </p>
 * <p>C@author lidongbin               </p>
 **/
public enum RedisKeyEnum {

    SMS_CODE("sms_code_","短信验证码key"),
    CAPTCHA_CODE("captcha","图片验证码key");


    private String key;
    private String summary;

    private RedisKeyEnum(String key, String summary) {
        this.key = key;
        this.summary = summary;
    }

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }
}
