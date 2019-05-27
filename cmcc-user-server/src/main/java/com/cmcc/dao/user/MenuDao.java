package com.cmcc.dao.user;

import com.cmcc.model.user.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface MenuDao {

    int deleteByPrimaryKey(Long menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> queryMenuByUserId(Long userId);

    List<Menu> queryMenuByRoleId(Long roleId);

    List<Menu> gerUserMenuNotButton(Long userId);

    List<Menu> findAllMenus(Menu record);
}