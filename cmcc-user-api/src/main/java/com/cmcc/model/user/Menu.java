package com.cmcc.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * base_menu
 *
 * @author
 */
@Setter
@Getter
@Accessors(chain = true)
public class Menu implements Serializable {

    public static final String TYPE_MENU = "0";

    public static final String TYPE_BUTTON = "1";
    /**
     * 菜单/按钮ID
     */
    private Long menuId;

    /**
     * 上级菜单ID
     */

    private Long parentId;

    /**
     * 菜单/按钮名称
     */

    private String menuName;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */

    private String type;

    /**
     * 排序
     */
    private Long orderNum;

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

    private static final long serialVersionUID = 1L;

}