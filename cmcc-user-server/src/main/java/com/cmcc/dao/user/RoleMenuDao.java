package com.cmcc.dao.user;


import com.cmcc.model.user.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 角色权限关系表(RoleMenu)表数据库访问层
 *
 * @author chentengshuai
 * @since 2019-05-15 17:23:00
 */
@Mapper
@Repository
public interface RoleMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param roleMenuId 主键
     * @return 实例对象
     */
    RoleMenu queryById(Long roleMenuId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RoleMenu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param roleMenu 实例对象
     * @return 对象列表
     */
    List<RoleMenu> queryAll(RoleMenu roleMenu);

    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int insert(RoleMenu roleMenu);
    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int insertSelective(RoleMenu roleMenu);
    /**
     * 修改数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int update(RoleMenu roleMenu);

    /**
     * 通过主键删除数据
     *
     * @param roleMenuId 主键
     * @return 影响行数
     */
    int deleteById(Long roleMenuId);

}