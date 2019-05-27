package com.cmcc.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * base_role
 * @author 
 */
@Getter
@Setter
@Accessors(chain = true)
public class Role implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 角色描述
     */
    private String remark;

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
     * 是否管理员;N:否Y:是
     */
    private String isManager;

    private static final long serialVersionUID = 1L;

}