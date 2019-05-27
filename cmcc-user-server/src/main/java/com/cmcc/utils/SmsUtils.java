package com.cmcc.utils;


import cn.hutool.crypto.SecureUtil;

/**
 * <p>Create Time: 2019年05月09日 10:07          </p>
 * <p>C@author lidongbin               </p>
 **/

public class SmsUtils {

    public static String paramCheckMac(String ecName,String apId,String secretKey,
                                       String mobiles,String content,String sign,
                                       String addSerial){
        StringBuffer buffer = new StringBuffer();
        buffer.append(ecName);
        buffer.append(apId);
        buffer.append(secretKey);
        buffer.append(mobiles);
        buffer.append(content);
        buffer.append(sign);
        buffer.append(addSerial);
        return SecureUtil.md5(buffer.toString()).toLowerCase();
    }
}
