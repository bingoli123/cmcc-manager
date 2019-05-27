package com.cmcc.service.user;

import com.cmcc.dao.user.UserRoleDao;
import com.cmcc.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (UserRole)表服务实现类
 *
 * @author makejava
 * @since 2019-05-14 10:59:12
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userRoleId 主键
     * @return 实例对象
     */
    @Transactional(readOnly = true)
    @Override
    public UserRole queryById(Long userRoleId) {
        return userRoleDao.queryById(userRoleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserRole> queryRoleList(Long userId) {
        return userRoleDao.queryRoleList(userId);
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
    public List<UserRole> queryAllByLimit(int offset, int limit) {
        return userRoleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param UserRole 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserRole insert(UserRole UserRole) {
        userRoleDao.insert(UserRole);
        return UserRole;
    }

    /**
     * 修改数据
     *
     * @param userRole 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserRole update(UserRole userRole) {
        userRoleDao.update(userRole);
        return this.queryById(userRole.getUserRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userRoleId 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Long userRoleId) {
        return userRoleDao.deleteById(userRoleId) > 0;
    }
}