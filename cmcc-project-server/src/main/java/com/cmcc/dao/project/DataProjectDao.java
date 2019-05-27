package com.cmcc.dao.project;

import com.cmcc.model.project.DataProject;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 项目信息(DataProject)表数据库访问层
 *
 * @author lidongbin
 * @since 2019-05-15 09:38:09
 */
@Mapper
@Repository
public interface DataProjectDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DataProject queryById(Long id);

    /**
     * 根据项目名查询有无同名的项目
     * @param projectName
     * @return
     * @throws Exception
     */
    int queryProjectByName(@Param("projectName")String projectName,@Param("id")Long  id) throws Exception;

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DataProject> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dataProject 实例对象
     * @return 对象列表
     */
    List<DataProject> queryAll(DataProject dataProject);

    /**
     * 新增数据
     *
     * @param dataProject 实例对象
     * @return 影响行数
     */
    int insert(DataProject dataProject);

    /**
     * 修改数据
     *
     * @param dataProject 实例对象
     * @return 影响行数
     */
    int update(DataProject dataProject);

    /**
     * 根据用户id查询此用户id关联的项目列表
     * @param userId
     * @return
     * @throws Exception
     */
    List<DataProject> queryProjectByUserId(@Param("userId")Long userId,
                                           @Param("projectName") String projectName) throws Exception;

//    查询当前项目授权给了那些用户
    List<DataProject> queryUserByProject(@Param("projectId")Long projectId,
                                         @Param("loginUserId") Long loginUserId) throws Exception;

}