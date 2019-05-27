package com.cmcc.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserRole)实体类
 *
 * @author makejava
 * @since 2019-05-14 10:44:18
 */
@Setter
@Getter
@Accessors(chain = true)
public class UserRole implements Serializable {
    private static final long serialVersionUID = -24593648650142885L;
    //主键ID
    private Long userRoleId;
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;
    //创建时间
    private Date createTime;
    //创建人
    private Long creator;
    //修改时间
    private Date modifyTime;
    //修改人
    private Long modifier;
    //删除标识  N未删除 Y已删除
    private String deleteFlag;

}