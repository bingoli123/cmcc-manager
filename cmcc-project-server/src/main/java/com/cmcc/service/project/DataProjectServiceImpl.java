package com.cmcc.service.project;

import cn.hutool.core.date.DateUtil;
import com.cmcc.dao.project.DataProjectDao;
import com.cmcc.dao.project.UserProjectDao;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.project.DataProject;
import com.cmcc.model.project.UserProject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 项目信息(DataProject)表服务实现类
 *
 * @author lidongbin
 * @since 2019-05-15 09:38:11
 */
@Service
public class DataProjectServiceImpl implements DataProjectService {
    @Autowired
    private DataProjectDao dataProjectDao;
    @Autowired
    private UserProjectDao userProjectDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DataProject queryById(Long id) {
        return this.dataProjectDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param dataProject 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralReturnMessage insert(DataProject dataProject) throws Exception {
        if (StringUtils.isBlank(dataProject.getProjectName())){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
//        判断是否存在同名的项目名
        int b = dataProjectDao.queryProjectByName(dataProject.getProjectName(),null);
        if (b>0){
            return GeneralReturnMessage.fail(EnumErrCode.PROJECT_NAME_HAS_SAME);
        }
        dataProject.setCreateTime(DateUtil.date());
        dataProject.setDeleteFlag("N");
        int a = dataProjectDao.insert(dataProject);
//        新增关系表
        UserProject userProject = new UserProject()
                        .setProjectId(dataProject.getId())
                        .setUserId(dataProject
                        .getCreator())
                        .setCreateDate(DateUtil.date())
                        .setDeleteFlag("N");
        int c = userProjectDao.insert(userProject);
        if (a!=1||c!=1){
            throw new Exception("创建项目失败");
        }else {
            return GeneralReturnMessage.success(dataProject);
        }
    }

    /**
     * 修改数据
     *
     * @param dataProject 实例对象
     * @return 实例对象
     */
    @Override
    public GeneralReturnMessage update(DataProject dataProject) throws Exception {
        if (null==dataProject.getId()){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        int b = dataProjectDao.queryProjectByName(dataProject.getProjectName(),dataProject.getId());
        if (b>0){
            return GeneralReturnMessage.fail(EnumErrCode.PROJECT_NAME_HAS_SAME);
        }
        dataProject.setModifyTime(DateUtil.date());
        int a = dataProjectDao.update(dataProject);
        return GeneralReturnMessage.success("修改成功");
    }


    /**
     * 分页查询单个用户下的所有的项目
     * @param dataProject
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public GeneralReturnMessage<List<DataProject>> queryProjectByUserPage(DataProject dataProject,Long userId,Integer pageNum,Integer pageSize)  throws Exception{
        if (null==userId){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<DataProject> result = dataProjectDao.queryProjectByUserId(userId,dataProject.getProjectName());
        return GeneralReturnMessage.success(result,page.getTotal());
    }

    /**
     * 查询单个用户下的所有的项目（设备下拉列表用）
     * @param dataProject
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public GeneralReturnMessage queryProjectByUser(DataProject dataProject,Long userId)  throws Exception{
        if (null==userId){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        List<DataProject> result = dataProjectDao.queryProjectByUserId(userId,dataProject.getProjectName());
        return GeneralReturnMessage.success(result);
    }

    /**
     * 项目授权给用户
     * @param projectId 项目id
     * @param chooseUserId 新选中的用户id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public GeneralReturnMessage projectAuthUser(Long projectId,Long [] chooseUserId,Long loginUserId) throws Exception{
        if (null==projectId){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        List<Long> chooseList = Arrays.asList(chooseUserId);
        List<Long> chooseList2 = new ArrayList<>();
        for (Long a:chooseList){
            chooseList2.add(a);
        }
        List<Long> addUser = new ArrayList<>();
        List<Long> delUserId = new ArrayList<>();
//        查询出此项目当前数据库中已经授权的用户id 排除当前登录用户
        List<DataProject> list = dataProjectDao.queryUserByProject(projectId,loginUserId);
//        项目已授权的id集合
        List<Long> authUserIds = new ArrayList<>();
        for (DataProject dataProject:list){
            authUserIds.add(dataProject.getId());
        }
//       遍历出已经删除的用户
//           当前选中        新选中
//       【1，2，3，4】 【3，4，5，6】
//        两个集合取交集
        chooseList2.retainAll(authUserIds);
//        需要删除的部分
        authUserIds.removeAll(chooseList2);
//        需要插入的部分
        chooseList.removeAll(chooseList2);
        addUser = chooseList;
        delUserId = authUserIds;


//   删除列表
        int a = userProjectDao.deleteByList(delUserId,projectId);
        if (a!=delUserId.size()){
            return GeneralReturnMessage.fail(EnumErrCode.PROJECT_AUTH_USER_FAIL);
        }
        List<UserProject> userProjects  = new ArrayList<>();
        for (Long user:addUser){
            userProjects.add(new UserProject()
                    .setDeleteFlag("N")
                    .setProjectId(projectId)
                    .setCreateDate(DateUtil.date())
                    .setUserId(user)
                    .setCreator(loginUserId));
        }
        int b = userProjectDao.insertByList(userProjects);
        if (b!=addUser.size()){
            throw new Exception("项目授权失败");
        }
        return null;
    }

}