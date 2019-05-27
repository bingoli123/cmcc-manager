package com.cmcc.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * t_operation_log
 * @author 
 */
@Setter
@Getter
@Accessors(chain = true)
public class OperationLog implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 操作用户
     */
    private String userName;

    /**
     * 耗时
     */
    private Long operateTime;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}