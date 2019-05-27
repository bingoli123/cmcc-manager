package com.cmcc.service.user;

import com.cmcc.dao.user.MenuDao;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Menu;
import com.cmcc.model.user.Tree;
import com.cmcc.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * FileName: MenuServiceImpl
 * Author:   陈腾帅
 * Date:     2019 5 7 0007 11:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * 根据用户名查询用户菜单
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryMenuByUserId(Long userId) throws Exception {
        try {
            List<Menu> menuList = menuDao.queryMenuByUserId(userId);
            return GeneralReturnMessage.success(menuList);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 根据用户名查询用户菜单
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage gerUserMenuNotButton(Long userId) throws Exception {
        try {
            List<Menu> menuList = menuDao.gerUserMenuNotButton(userId);
            return GeneralReturnMessage.success(menuList);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryAllMenu() {
        try {
            Menu menu = new Menu();
            menu.setDeleteFlag("N");
            List<Menu> menuList = menuDao.findAllMenus(menu);
            return GeneralReturnMessage.success(menuList);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 获得用户菜单属性列表
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage getUserMenu(Long userId) throws Exception {
        List<Tree<Menu>> trees = new ArrayList<>();
        try {
            List<Menu> menus = menuDao.queryMenuByUserId(userId);
            menus.forEach(menu -> {
                Tree<Menu> tree = new Tree<>();
                tree.setId(menu.getMenuId().toString());
                tree.setParentId(menu.getParentId().toString());
                tree.setText(menu.getMenuName());
                tree.setIcon(menu.getIcon());
                tree.setUrl(menu.getUrl());
                trees.add(tree);
            });
            Tree<Menu> menuTree = TreeUtil.build(trees);
            return GeneralReturnMessage.success(menuTree);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 查询菜单
     *
     * @param menu
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage findAllMenus(Menu menu) {
        try {
            List<Menu> menuList = menuDao.findAllMenus(menu);
            return GeneralReturnMessage.success(menuList);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 获得菜单按钮树形
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage getMenuButtonTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        try {
            GeneralReturnMessage generalReturnMessage = this.findAllMenus(new Menu());
            if (generalReturnMessage.isRequestFlag()) {
                List<Menu> menuList = (List<Menu>) generalReturnMessage.getData();
                buildTrees(trees, menuList);
                Tree<Menu> menuTree = TreeUtil.build(trees);
                return GeneralReturnMessage.success(menuTree);
            } else {
                return GeneralReturnMessage.fail(generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 获得所有菜单
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage getMenuTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        try {
            GeneralReturnMessage generalReturnMessage = findAllMenus(new Menu().setType("0"));
            if (generalReturnMessage.isRequestFlag()) {
                List<Menu> menuList = (List<Menu>) generalReturnMessage.getData();
                buildTrees(trees, menuList);
                Tree<Menu> menuTree = TreeUtil.build(trees);
                return GeneralReturnMessage.success(menuTree);
            } else {
                return GeneralReturnMessage.fail(generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 根据菜单id获得菜单
     *
     * @param menuId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage findById(Long menuId) {
        try {
            Menu menu = menuDao.selectByPrimaryKey(menuId);
            return GeneralReturnMessage.success(menu);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 根据菜单名和类型获得菜单
     *
     * @param menuName
     * @param type
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage findByNameAndType(String menuName, String type) {
        Menu menu = new Menu();
        menu.setMenuName(menuName);
        menu.setType(type);
        try {
            GeneralReturnMessage generalReturnMessage = findAllMenus(menu);
            if (generalReturnMessage.isRequestFlag()) {
                List<Menu> menuList = (List<Menu>) generalReturnMessage.getData();
                if (menuList != null) {
                    return GeneralReturnMessage.success(menuList.get(0));
                } else {
                    return GeneralReturnMessage.fail("没有找到");
                }
            } else {
                return GeneralReturnMessage.fail(generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 增加菜单
     *
     * @param menu 菜单实体
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage addMenu(Menu menu) {
        try {
            int i = menuDao.insert(menu);
            if (i > 0) {
                return GeneralReturnMessage.success("新增菜单成功");
            } else {
                return GeneralReturnMessage.fail("新增菜单失败");
            }
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 修改菜单
     *
     * @param menu 菜单实体
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage updateMenu(Menu menu) {
        try {
            int i = menuDao.updateByPrimaryKey(menu);
            if (i > 0) {
                return GeneralReturnMessage.success("更新菜单成功");
            } else {
                return GeneralReturnMessage.fail("新增菜单失败");
            }
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 根据菜单IDs删除菜单
     *
     * @param menuId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage deleteMeuns(String menuId) throws Exception {
        String[] menuIds = menuId.split(",");
        try {
            for (String itemId : menuIds) {
                int i = menuDao.deleteByPrimaryKey(Long.parseLong(itemId));
                if (i < 0) {
                    throw new Exception("删除菜单失败");
                }
            }
            return GeneralReturnMessage.success("删除菜单成功");
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    private void buildTrees(List<Tree<Menu>> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            trees.add(tree);
        });
    }
}
