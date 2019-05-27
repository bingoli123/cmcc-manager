package com.cmcc.dao.user;

import com.cmcc.model.user.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleDao {

    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRole(Role record);

    /**
     * 根据用户名查询角色名个数
     *
     * @param roleName
     * @return
     */
    int selectCountRoleByRoleName(String roleName);
}