package com.cmcc.dao.project;

import com.cmcc.model.project.UserProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 10:02:32
 * Description: 用户和项目的关系表(UserProject)表数据库访问层
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          2019-05-16 10:02:32         V1.0              描述
 */
@Mapper
@Repository
public interface UserProjectDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserProject queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserProject> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userProject 实例对象
     * @return 对象列表
     */
    List<UserProject> queryAll(UserProject userProject);

    /**
     * 新增数据
     *
     * @param userProject 实例对象
     * @return 影响行数
     */
    int insert(UserProject userProject);

    /**
     * 新增数据
     *
     * @param userProject 实例对象
     * @return 影响行数
     */
    int insertSelective(UserProject userProject);

    /**
     * 修改数据
     *
     * @param userProject 实例对象
     * @return 影响行数
     */
    int update(UserProject userProject);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int deleteByList(@Param("ids") List<Long> ids,@Param("projectId") Long projectId) throws Exception;

    int insertByList(@Param("userProjectList") List<UserProject> userProjects) throws Exception;

}