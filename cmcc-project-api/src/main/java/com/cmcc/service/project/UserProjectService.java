package com.cmcc.service.project;


import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.project.UserProject;

import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 09:59:37
 * Description:用户和项目的关系表(UserProject)表数据库sevice层
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          $time.currTime          V1.0              描述
 */
public interface UserProjectService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserProject queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserProject> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userProject 实例对象
     * @return 实例对象
     */
    UserProject insert(UserProject userProject);

    /**
     * 修改数据
     *
     * @param userProject 实例对象
     * @return 实例对象
     */
    UserProject update(UserProject userProject);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 修改用户的项目
     *
     * @param userId            被选中用户
     * @param currentUserId     当前用户
     * @param currentProjectList 当前项目列表
     * @return
     * @throws Exception
     */
    GeneralReturnMessage updateUserProject(Long userId, Long currentUserId, List<Long> currentProjectList) throws Exception;

}