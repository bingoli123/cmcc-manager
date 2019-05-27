package com.cmcc.service.user;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Menu;

/**
 * Copyright (C), 2019-2019,
 * FileName: MenuService
 * Author:   陈腾帅
 * Date:     2019 5 7 0007 11:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
public interface MenuService {

    GeneralReturnMessage queryMenuByUserId(Long userId) throws Exception;

    GeneralReturnMessage queryAllMenu();

    /**
     * 获得用户菜单属性列表
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    GeneralReturnMessage getUserMenu(Long userId) throws Exception;

    GeneralReturnMessage gerUserMenuNotButton(Long userid) throws Exception;


    /**
     * 查询菜单
     *
     * @param menu
     * @return
     */
    GeneralReturnMessage findAllMenus(Menu menu);

    /**
     * 获得菜单按钮树形
     *
     * @return
     */
    GeneralReturnMessage getMenuButtonTree();

    /**
     * 获得所有菜单
     *
     * @return
     */
    GeneralReturnMessage getMenuTree();


    /**
     * 根据菜单id获得菜单
     *
     * @param menuId
     * @return
     */
    GeneralReturnMessage findById(Long menuId);

    /**
     * 根据菜单名和类型获得菜单
     *
     * @param menuName
     * @param type
     * @return
     */
    GeneralReturnMessage findByNameAndType(String menuName, String type);

    /**
     * 增加菜单
     *
     * @param menu 菜单实体
     */
    GeneralReturnMessage addMenu(Menu menu);

    /**
     * 修改菜单
     *
     * @param menu 菜单实体
     */
    GeneralReturnMessage updateMenu(Menu menu);

    /**
     * 根据菜单IDs删除菜单
     *
     * @param menuId
     */
    GeneralReturnMessage deleteMeuns(String menuId) throws Exception;
}
