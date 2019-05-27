package com.cmcc.model;

import com.cmcc.enums.EnumErrCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年11月15日 11:13          </p>
 * <p>C@author lidongbin               </p>
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralReturnMessage<T> implements Serializable {
    private static final long serialVersionUID = -3763590381320338497L;

    private boolean requestFlag = true;
    private String requestMsg = "";
    private T data;
    private Long pageCount;
    private String retCode="";

    /**
     * 请求成功
     * @param data 请求成功传输数据
     * @param <T> 请求成功传输数据泛型
     * @return 请求成功信息
     */
    public static <T> GeneralReturnMessage success(T data) {
        return new GeneralReturnMessage(true, "SUCCESS", data,0L,"00000");
    }
    /**
     * 请求成功
     * @param data 请求成功传输数据
     * @param <T> 请求成功传输数据泛型
     * @param pageCount
     * @return 请求成功信息
     */
    public static <T> GeneralReturnMessage success(T data,Long pageCount) {
        return new GeneralReturnMessage(true, "SUCCESS", data,pageCount,"00000");
    }

    /**
     * 请求失败
     * @param requestMsg 请求失败传输数据
     * @return 请求失败
     */
    public static GeneralReturnMessage fail(String requestMsg) {
        return new GeneralReturnMessage(false, requestMsg, null,0L,"");
    }
    /**
     * 请求失败
     * @param requestMsg 请求失败传输数据
     * @return 请求失败
     */
    public static GeneralReturnMessage fail(String retCode,String requestMsg) {
        return new GeneralReturnMessage(false, requestMsg, null,0L,retCode);
    }

    /**
     * 请求失败
     *
     * @param enumErrCode 错误枚举类型
     * @return
     */
    public static GeneralReturnMessage fail(EnumErrCode enumErrCode) {
        return new GeneralReturnMessage(false, enumErrCode.getRetInfo(), null, 0L, enumErrCode.getRetCode());
    }
}
