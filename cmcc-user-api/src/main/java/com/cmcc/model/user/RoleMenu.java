package com.cmcc.model.user;

import java.util.Date;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = -78887124360403708L;
    //主键ID
    private Long roleMenuId;
    //角色ID
    private Long roleId;
    //菜单/按钮ID
    private Long menuId;
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