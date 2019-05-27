package com.cmcc.service.project;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.project.DataProject;

import java.util.List;

/**
 * 项目信息(DataProject)表服务接口
 *
 * @author lidongbin
 * @since 2019-05-15 09:38:10
 */
public interface DataProjectService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DataProject queryById(Long id);

    /**
     * 新增数据
     *
     * @param dataProject 实例对象
     * @return 实例对象
     */
    GeneralReturnMessage insert(DataProject dataProject) throws Exception;

    /**
     * 修改数据
     *
     * @param dataProject 实例对象
     * @return 实例对象
     */
    GeneralReturnMessage update(DataProject dataProject) throws Exception;

    /**
     * 查询用户的全部项目
     * @param dataProject
     * @param userId
     * @return
     * @throws Exception
     */
    GeneralReturnMessage queryProjectByUser(DataProject dataProject, Long userId)  throws Exception;

    /**
     * 分页查询项目列表
     * @param dataProject
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    GeneralReturnMessage queryProjectByUserPage(DataProject dataProject,Long userId,Integer pageNum,Integer pageSize)  throws Exception;

    /**
     * 项目授权给用户
     * @param projectId
     * @param chooseUserId
     * @return
     * @throws Exception
     */
    GeneralReturnMessage projectAuthUser(Long projectId,Long [] chooseUserId,Long loginUserId) throws Exception;


}