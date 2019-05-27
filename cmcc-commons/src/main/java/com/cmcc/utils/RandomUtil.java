package com.cmcc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 说明:
 * 随机数工具
 * @author hock
 * @date 2018/4/18
 */
public class RandomUtil {
    private static final Logger logger = LoggerFactory.getLogger(RandomUtil.class);
    /**
     * 创建一个指定长度的验证码
     * @param length
     * @return
     */
    static public String BuildVerificationCode(int length){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<length;i++){
            builder.append(random.nextInt(9));
        }
        return builder.toString();
    }


}
