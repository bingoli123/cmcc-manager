package com.cmcc.service.user;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.dao.user.MenuDao;
import com.cmcc.dao.user.RoleDao;
import com.cmcc.dao.user.RoleMenuDao;
import com.cmcc.dao.user.UserRoleDao;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Menu;
import com.cmcc.model.user.Role;
import com.cmcc.model.user.RoleMenu;
import com.cmcc.model.user.UserRole;
import com.cmcc.utils.ArrayListUtitl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2019-2019,
 * FileName: RoleServiceImpl
 * @author:   陈腾帅
 * Date:     2019 5 13 0013 9:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuDao menuDao;

    /**
     * 查询系统所有的角色
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage getALLRole(Role role) {
        log.info("查询系统所有的角色，无分页");
        try {
            List<Role> list = roleDao.selectRole(role);
            return GeneralReturnMessage.success(list);
        } catch (Exception e) {
            log.error("查询所有角色失败：{}", e.toString());
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    @Override
    public GeneralReturnMessage queryRoleByPage(Role role, int pageNum, int pageSize) {
        log.info("分页查询角色列表");
        Page page = PageHelper.startPage(pageNum, pageSize);
        try {
            List<Role> list = roleDao.selectRole(role);
            return GeneralReturnMessage.success(list, page.getTotal());
        } catch (Exception e) {
            log.error("查询所有角色失败：{}", e.toString());
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 插入角色
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralReturnMessage insertRole(Role role) throws Exception {
        if (StringUtils.isBlank(role.getRoleName())) {
            return GeneralReturnMessage.fail(EnumErrCode.RoleNameIsBlank);
        }
        int roleCount = roleDao.selectCountRoleByRoleName(role.getRoleName());
        if (roleCount == 0) {
            int i = roleDao.insert(role);
            if (i == 1) {
                return GeneralReturnMessage.success("插入角色成功");
            } else {
                log.error("新增角色失败:{}", JSONObject.toJSONString(role));
                throw new Exception("插入角色失败");
            }
        } else {
            log.error("新增角色失败:{}", JSONObject.toJSONString(role));
            return GeneralReturnMessage.fail(EnumErrCode.RoleNameHaveExists);
        }
    }

    /**
     * 删除角色
     *
     * @param role 角色对象
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage deleteRole(Role role) throws Exception {
        try {
            List<Role> roleCount = roleDao.selectRole(role);
            if (roleCount == null || roleCount.size() == 0) {
                return GeneralReturnMessage.fail(EnumErrCode.RoleNotExists);
            } else if (roleCount.size() > 1) {
                return GeneralReturnMessage.fail(EnumErrCode.RoleHaveExistsSame);
            } else {
                //查询是否存在用户角色关系
                UserRole userRoleQuery = new UserRole();
                userRoleQuery.setRoleId(roleCount.get(0).getRoleId());
                List<UserRole> userRoleList = userRoleDao.queryAll(userRoleQuery);
                //加入存在用户角色关系则不能删除
                if (userRoleList != null && userRoleList.size() > 0) {
                    return GeneralReturnMessage.fail(EnumErrCode.RoleHaveBindUser);
                }
                int i = roleDao.deleteByPrimaryKey(roleCount.get(0).getRoleId());
                if (i == 1) {
                    return GeneralReturnMessage.success("删除角色成功");
                } else {
                    log.error("删除角色失败:{}", JSONObject.toJSONString(role));
                    throw new Exception("删除角色失败");
                }
            }
        } catch (Exception e) {
            log.error("删除角色失败:{}", JSONObject.toJSONString(role));
            return GeneralReturnMessage.fail(EnumErrCode.RoleDeleteFail);
        }
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage updateRole(Role role) throws Exception {
        try {
            Role roleCount = roleDao.selectByPrimaryKey(role.getRoleId());
            if (roleCount == null) {
                return GeneralReturnMessage.fail(EnumErrCode.RoleNotExists);
            } else {
                int i = roleDao.updateByPrimaryKeySelective(role);
                if (i > 0) {
                    return GeneralReturnMessage.success("修改角色成功");
                } else {
                    log.error("修改角色失败:{}", JSONObject.toJSONString(role));
                    throw new Exception("修改角色失败");
                }
            }
        } catch (Exception e) {
            log.error("修改角色失败:{}", JSONObject.toJSONString(role));
            return GeneralReturnMessage.fail(EnumErrCode.RoleUpdateFail);
        }
    }

    /**
     * 查询角色的菜单
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryRoleMenu(Long roleId) {
        try {
            //角色id不能为空
            if (roleId == null) {
                return GeneralReturnMessage.fail(EnumErrCode.RoleIdIsBlank);
            }
            List<Menu> roleMenuList = menuDao.queryMenuByRoleId(roleId);
            return GeneralReturnMessage.success(roleMenuList);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 修改角色的菜单权限
     *
     * @param roleId         被选中角色
     * @param currentUserId  当前用户
     * @param currentMenuIdList 当前菜单列表
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage updateRoleMenu(Long roleId, Long currentUserId, List<Long> currentMenuIdList) throws Exception {
        //获得当前角色的菜单集合
        List<RoleMenu> roleIdMenuList = roleMenuDao.queryAll(new RoleMenu().setRoleId(roleId));
        //获得当前角色的菜单集合
        List<Long> roleMenuLong = roleIdMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        //需要删除的菜单集合
        List<Long> deleteMenuList = ArrayListUtitl.deleteListLong(roleMenuLong, currentMenuIdList);
        //需要新增的菜单集合
        List<Long> insertMenuList = ArrayListUtitl.insertListLong(roleMenuLong, currentMenuIdList);
        //循环删除角色菜单集合
        for (Long menuId : deleteMenuList) {
            //通过菜单id和角色id查询是否已经有
            List<RoleMenu> roleMenuList = roleMenuDao.queryAll(new RoleMenu().setMenuId(menuId).setRoleId(roleId));
            //如果已经没有退出本次循环
            if (roleMenuList == null || roleMenuList.size() == 0) {
                continue;
            }
            //循环删除存在的用户角色
            for (RoleMenu roleMenu : roleMenuList) {
                int i = roleMenuDao.deleteById(roleMenu.getRoleMenuId());
                if (i < 0) {
                    throw new Exception("删除角色菜单失败");
                }
            }
        }
        //循环需要插入角色菜单的集合
        for (Long menuId : insertMenuList) {
            //根据用户id和角色id查询是否已经有
            List<RoleMenu> roleMenuList = roleMenuDao.queryAll(new RoleMenu().setMenuId(menuId).setRoleId(roleId));
            //如果已经有则退出本次循环
            if (roleMenuList != null && roleMenuList.size() > 0) {
                continue;
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId).setMenuId(menuId).setCreateTime(new Date()).setCreator(currentUserId).
                    setModifier(currentUserId).setModifyTime(new Date()).setDeleteFlag("N");
            //执行插入
            int i = roleMenuDao.insert(roleMenu);
            if (i != 1) {
                throw new Exception("新增角色菜单失败");
            }
        }
        return GeneralReturnMessage.success("更新角色菜单成功");
    }
}
