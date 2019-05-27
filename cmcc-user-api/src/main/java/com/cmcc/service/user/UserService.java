package com.cmcc.service.user;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.User;

import java.util.List;

/**
 * user service interface
 *
 * @author YL-S
 * @Create 2019-01-09
 */
public interface UserService {

    GeneralReturnMessage test(Long userId) throws Exception;

    User queryByUserName(String userName) throws Exception;

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userId      用户Id
     * @return
     * @throws Exception
     */
    GeneralReturnMessage updatePassword(String password, String oldPassword, String newPassword, Long userId) throws Exception;

    /**
     * 用户新增
     *
     * @param user
     * @return
     * @throws Exception
     */
    GeneralReturnMessage insertUser(User user) throws Exception;

    /**
     * 用户注册
     *
     * @param user
     * @return
     * @throws Exception
     */
    GeneralReturnMessage registerUser(User user, String smsCode) throws Exception;

    /**
     * 用户删除
     *
     * @param user
     * @return
     * @throws Exception
     */
    GeneralReturnMessage deleteUser(Long user) throws Exception;

    /**
     * 通过手机号加验证码登录
     *
     * @param mobile  手机号
     * @param smsCode 验证码
     * @return
     * @throws Exception
     */
    GeneralReturnMessage loginBySms(String mobile, String smsCode) throws Exception;

    /**
     * @desc:发送短信验证码
     * @param:
     * @return:
     * @auther: lidongbin
     * @date: 2019-05-10 14:31
     */

    GeneralReturnMessage sendSmsNormal(String mobile)throws Exception;

    /**
     * 修改用户的角色
     *
     * @param userId         被选中用户
     * @param currentUserId   当前用户
     * @param currentRoleList 当前角色列表
     * @return
     * @throws Exception
     */
    GeneralReturnMessage updateUserRole(Long userId, Long currentUserId, List<Long> currentRoleList) throws Exception;

    /**
     * 查询公司id下的所有用户
     * @param companyId
     * @return
     * @throws Exception
     */
    GeneralReturnMessage queryUserByCompany(Long companyId,String userName) throws Exception;

    /**
     * 查询公司id下的所有用户(分页查询)
     * @param companyId
     * @param userName
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    GeneralReturnMessage queryUserByCompanyByPage(Long companyId,String userName,Integer pageNum,Integer pageSize) throws Exception;


}
