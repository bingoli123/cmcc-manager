package com.cmcc.controller.user;

import com.cmcc.annotation.Log;
import com.cmcc.controller.base.BaseController;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Menu;
import com.cmcc.service.user.MenuService;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C), 2019-2019,
 * FileName: MenuController
 * Author:   陈腾帅
 * Date:     2019 5 10 0010 16:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuServiceImpl;

    /**
     * 获取用户菜单
     *
     * @param userId
     * @return
     */
    @RequiresPermissions("menu:query")
    @GetMapping("/queryUserMenu")
    public ResponseEntity queryUserMenu(@RequestParam(value = "userId") Long userId) {
        try {
            log.info("查询用户菜单", userId);
            if (userId == null) {
                return ResponseEntity.fail("", "用户ID不能为空");
            }
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.getUserMenu(userId);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("获取用户菜单失败:{}", e.toString());
            return ResponseEntity.fail("", "获取用户菜单失败");
        }
    }

    /**
     * 获取用户菜单没有按钮
     *
     * @param userId
     * @return
     */
    @RequiresPermissions("menu:query")
    @GetMapping("/queryUserMenuNoButton")
    public ResponseEntity queryUserMenuNoButton(@RequestParam(value = "userId") Long userId) {
        try {
            log.info("获取用户菜单没有按钮", userId);
            if (userId == null) {
                return ResponseEntity.fail("", "用户ID不能为空");
            }
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.gerUserMenuNotButton(userId);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("获取用户菜单失败:{}", e.toString());
            return ResponseEntity.fail("", "获取用户菜单失败");
        }
    }

    /**
     * 根据菜单ID获得菜单
     *
     * @param menuId
     * @return
     */
    @RequiresPermissions("menu:query")
    @GetMapping("/queryMenuById")
    public ResponseEntity queryMenuById(@RequestParam(value = "menuId") Long menuId) {
        try {
            log.info("查询菜单,{}", menuId);
            if (menuId == null) {
                return ResponseEntity.fail("", "菜单ID不能为空");
            }
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.findById(menuId);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("根据菜单ID获得菜单失败:{}", e.toString());
            return ResponseEntity.fail("", "根据菜单ID获得菜单");
        }
    }

    /**
     * 获取菜单按钮树形列表
     * @return
     */
    @RequiresPermissions("menu:query")
    @GetMapping("/queryMenuButtonTree")
    public ResponseEntity queryMenuButtonTree() {
        log.info("获取菜单按钮树形列表");
        try {
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.getMenuButtonTree();
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("根据菜单ID获得菜单:{}", e.toString());
            return ResponseEntity.fail("", "根据菜单ID获得菜单");
        }
    }

    /**
     * @return
     */
    @RequiresPermissions("menu:query")
    @GetMapping("/queryMenuTree")
    public ResponseEntity queryMenuTree() {
        try {
            log.info("获取菜单树形列表");
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.getMenuTree();
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("获取菜单树失败:{}", e);
            return ResponseEntity.fail("", "获取菜单树失败！");
        }
    }

    @RequiresPermissions("menu:query")
    @GetMapping("/queryMenuList")
    public ResponseEntity queryMenuList(@RequestParam(value = "menu") Menu menu) {
        try {
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.findAllMenus(menu);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("获取菜单集合失败:{}", e);
            return ResponseEntity.fail("", e.toString());
        }
    }

    @RequiresPermissions("menu:query")
    @GetMapping("/checkMenuName")
    public ResponseEntity checkMenuName(@RequestParam(value = "menuName") String menuName,
                                 @RequestParam(value = "type") String type,
                                 @RequestParam(value = "oldMenuName") String oldMenuName) {
        if (StringUtils.isNotBlank(oldMenuName) && menuName.equalsIgnoreCase(oldMenuName)) {
            return ResponseEntity.ok("");
        }
        try {
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.findByNameAndType(menuName, type);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("checkMenuName:{}", e);
            return ResponseEntity.fail("", e.toString());
        }
    }

    @Log("新增菜单/按钮")
    @RequiresPermissions("menu:insert")
    @GetMapping("/insertMenu")
    public ResponseEntity insertMenu(@RequestParam(value = "menuId", required = false) Long menuId,
                                     @RequestParam(value = "parentId") Long parentId,
                                     @RequestParam(value = "menuName") String menuName,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "perms", required = false) String perms,
                                     @RequestParam(value = "icon", required = false) String icon,
                                     @RequestParam(value = "type") String type,
                                     @RequestParam(value = "orderNum", required = false) Long orderNum) {
        String name;
        Menu menu = new Menu().setMenuId(menuId).setMenuName(menuName).setUrl(url).setPerms(perms)
                .setIcon(icon).setType(type).setOrderNum(orderNum);
        if (Menu.TYPE_MENU.equals(menu.getType())) {
            name = "菜单";
        } else {
            name = "按钮";
        }
        try {
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.addMenu(menu);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("新增" + name + "成功");
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("新增{}失败{}", name, e);
            return ResponseEntity.fail("", "新增" + name + "失败，请联系网站管理员！");
        }
    }

    @Log("删除菜单")
    @RequiresPermissions("menu:delete")
    @GetMapping("/deleteMenus")
    public ResponseEntity deleteMenus(@RequestParam(value = "ids") String ids) {
        log.info("删除菜单");
        try {
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.deleteMeuns(ids);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("删除成功");
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("获取菜单失败", e);
            return ResponseEntity.fail("", "删除失败，请联系网站管理员！");
        }
    }

    @Log("修改菜单/按钮")
    @RequiresPermissions("menu:update")
    @GetMapping("/updateMenu")
    public ResponseEntity updateMenu(@RequestParam(value = "menuId", required = true) Long menuId,
                                     @RequestParam(value = "parentId") Long parentId,
                                     @RequestParam(value = "menuName") String menuName,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "perms", required = false) String perms,
                                     @RequestParam(value = "icon", required = false) String icon,
                                     @RequestParam(value = "type") String type,
                                     @RequestParam(value = "orderNum", required = false) Long orderNum) {
        log.info("修改菜单");
        String name;
        Menu menu = new Menu().setMenuId(menuId).setMenuName(menuName).setUrl(url).setPerms(perms)
                .setIcon(icon).setType(type).setOrderNum(orderNum);
        if (Menu.TYPE_MENU.equals(menu.getType()))
            name = "菜单";
        else
            name = "按钮";
        try {
            GeneralReturnMessage generalReturnMessage = menuServiceImpl.updateMenu(menu);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("修改成功");
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("修改{}失败", name, e);
            return ResponseEntity.fail("", "修改" + name + "失败，请联系网站管理员！");
        }
    }
}
