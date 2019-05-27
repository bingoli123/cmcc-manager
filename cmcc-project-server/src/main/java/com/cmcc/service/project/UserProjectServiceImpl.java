package com.cmcc.service.project;

import com.cmcc.dao.project.UserProjectDao;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.project.UserProject;
import com.cmcc.utils.ArrayListUtitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 10:01:18
 * Description: 用户和项目的关系表(UserProject)表服务实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          2019-05-16 10:01:18         V1.0              描述
 */
@Service
public class UserProjectServiceImpl implements UserProjectService {
    @Autowired
    private UserProjectDao userProjectDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Transactional(readOnly = true)
    @Override
    public UserProject queryById(Long id) {
        return this.userProjectDao.queryById(id);
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
    public List<UserProject> queryAllByLimit(int offset, int limit) {
        return this.userProjectDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userProject 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserProject insert(UserProject userProject) {
        this.userProjectDao.insert(userProject);
        return userProject;
    }

    /**
     * 修改数据
     *
     * @param userProject 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserProject update(UserProject userProject) {
        this.userProjectDao.update(userProject);
        return this.queryById(userProject.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Long id) {
        return this.userProjectDao.deleteById(id) > 0;
    }

    /**
     * 修改用户的项目
     *
     * @param userId         被选中用户
     * @param currentUserId  当前用户
     * @param currentProjectList 当前项目列表
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage updateUserProject(Long userId, Long currentUserId, List<Long> currentProjectList) throws Exception {
        //获得当前用户的项目集合
        List<UserProject> userIdProject = userProjectDao.queryAll(new UserProject().setUserId(userId));
        //获得当前用户的项目集合
        List<Long> userProjectLong = userIdProject.stream().map(UserProject::getProjectId).collect(Collectors.toList());
        //需要删除的项目集合
        List<Long> deleteProjectList = ArrayListUtitl.deleteListLong(userProjectLong, currentProjectList);
        //需要新增的项目集合
        List<Long> insertProjectList = ArrayListUtitl.insertListLong(userProjectLong, currentProjectList);
        //循环删除用户项目集合
        for (Long project : deleteProjectList) {
            //通过用户id和项目id查询是否已经有
            List<UserProject> userProjectList = userProjectDao.queryAll(new UserProject().setUserId(userId).setProjectId(project));
            //如果已经没有退出本次循环
            if (userProjectList == null || userProjectList.size() == 0) {
                continue;
            }
            //循环删除存在的用户项目
            for (UserProject userProject : userProjectList) {
                int i = userProjectDao.deleteById(userProject.getId());
                if (i != 1) {
                    throw new Exception("删除用户项目失败");
                }
            }
        }
        //循环需要插入用户项目的集合
        for (Long project : insertProjectList) {
            //根据用户id和项目id查询是否已经有
            List<UserProject> userProjectList = userProjectDao.queryAll(new UserProject().setUserId(userId).setProjectId(project));
            //如果已经有则退出本次循环
            if (userProjectList != null && userProjectList.size() > 0) {
                continue;
            }
            UserProject userProject = new UserProject();
            userProject.setProjectId(project).setUserId(userId).setCreateDate(new Date()).setDeleteFlag("N").setCreator(currentUserId);
            //执行插入
            int i = userProjectDao.insert(userProject);
            if (i != 1) {
                throw new Exception("新增用户项目失败");
            }
        }
        return GeneralReturnMessage.success("更新用户项目成功");
    }
}