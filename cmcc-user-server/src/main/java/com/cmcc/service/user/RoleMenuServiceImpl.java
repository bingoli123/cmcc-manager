package com.cmcc.service.user;


import com.cmcc.dao.user.RoleMenuDao;
import com.cmcc.model.user.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限关系表(RoleMenu)表服务实现类
 *
 * @author chentengshuai
 * @since 2019-05-15 17:27:35
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleMenuId 主键
     * @return 实例对象
     */
    @Transactional(readOnly = true)
    @Override
    public RoleMenu queryById(Long roleMenuId) {
        return this.roleMenuDao.queryById(roleMenuId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoleMenu> queryAllByLimit(int offset, int limit) {
        return this.roleMenuDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleMenu insert(RoleMenu roleMenu) {
        this.roleMenuDao.insert(roleMenu);
        return roleMenu;
    }

    /**
     * 修改数据
     *
     * @param roleMenu 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleMenu update(RoleMenu roleMenu) {
        this.roleMenuDao.update(roleMenu);
        return this.queryById(roleMenu.getRoleMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleMenuId 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Long roleMenuId) {
        return this.roleMenuDao.deleteById(roleMenuId) > 0;
    }
}