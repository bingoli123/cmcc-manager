package com.cmcc.service.user;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Role;

import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * FileName: RoleService
 * Author:   陈腾帅
 * Date:     2019 5 7 0007 10:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅           修改时间           V1.0              描述
 */
public interface RoleService {

    /**
     * 查询系统所有的角色
     *
     * @return
     */
    GeneralReturnMessage getALLRole(Role role);

    /**
     * 查询分页数据
     *
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    GeneralReturnMessage queryRoleByPage(Role role, int pageNum, int pageSize);
    /**
     * 插入角色
     *
     * @param role
     * @return
     * @throws Exception
     */
    GeneralReturnMessage insertRole(Role role) throws Exception;

    /**
     * 删除角色
     *
     * @param role 角色对象
     * @return
     * @throws Exception
     */
    GeneralReturnMessage deleteRole(Role role) throws Exception;

    /**
     * 修改角色
     *
     * @param role
     * @return
     * @throws Exception
     */
    GeneralReturnMessage updateRole(Role role) throws Exception;


    /**
     * 查询角色的菜单
     *
     * @param role
     * @return
     * @throws Exception
     */
    GeneralReturnMessage queryRoleMenu(Long roleId);

    /**
     * 修改角色的菜单权限
     *
     * @param roleId         被选中角色
     * @param currentUserId  当前用户
     * @param currentMenuIdList 当前选择的菜单列表
     * @return
     * @throws Exception
     */
    GeneralReturnMessage updateRoleMenu(Long roleId, Long currentUserId, List<Long> currentMenuIdList) throws Exception;

}
