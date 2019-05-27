package com.cmcc.service.user;

import com.cmcc.model.user.RoleMenu;

import java.util.List;

/***
 * @param
 * @description: 角色权限关系表(RoleMenu)表服务接口
 * @return:
 * @author:陈腾帅
 * @date:2019-05-15
 */
public interface RoleMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleMenuId 主键
     * @return 实例对象
     */
    RoleMenu queryById(Long roleMenuId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<RoleMenu> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 实例对象
     */
    RoleMenu insert(RoleMenu roleMenu);

    /**
     * 修改数据
     *
     * @param roleMenu 实例对象
     * @return 实例对象
     */
    RoleMenu update(RoleMenu roleMenu);

    /**
     * 通过主键删除数据
     *
     * @param roleMenuId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleMenuId);

}