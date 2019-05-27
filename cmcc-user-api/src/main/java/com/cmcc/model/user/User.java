package com.cmcc.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * base_user
 * @author 
 */
@Setter
@Getter
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 删除标识  N未删除 Y已删除
     */
    private String deleteFlag;

    /**
     * 最近访问时间
     */
    private Date lastLoginTime;

    /**
     * 性别 1男 2女
     */
    private String sex;
    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 描述
     */
    private String description;

    /**
     * 用户中文姓名
     */
    private String userChName;

    private static final long serialVersionUID = 1L;

}