package com.cmcc.service.user;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.dao.user.UserDao;
import com.cmcc.dao.user.UserRoleDao;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.sms.SmsNormalEntity;
import com.cmcc.model.user.User;
import com.cmcc.model.user.UserRole;
import com.cmcc.utils.ArrayListUtitl;
import com.cmcc.utils.SmsProperties;
import com.cmcc.utils.SmsUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/***
 * @param
 * @description:
 * @return:
 * @author:陈腾帅
 * @date:2019-05-14
 */
@Slf4j
@Validated
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private UserRoleDao userRoleDao;


    //构造传入验证码的键值
    private static String buildSmsCodeKey(String mobile) {
        return "sms_code_" + mobile;
    }

    @Override
    public GeneralReturnMessage test(Long userId) throws Exception{
        return GeneralReturnMessage.success("123131");
    }

    @Transactional(readOnly = true)
    @Override
    public User queryByUserName(String userName) throws Exception {
        return userDao.queryByUserName(userName);
    }

    /**
     * 修改密码
     *
     * @param password    密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userId      用户Id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage updatePassword(String password, String oldPassword, String newPassword, Long userId) throws Exception {
        if (!password.equals(oldPassword)) {
            return GeneralReturnMessage.fail(EnumErrCode.IncorrectPassword.getRetCode(), EnumErrCode.IncorrectPassword.getRetInfo());
        }
        User user = new User();
        user.setPassword(newPassword);
        user.setId(userId);
        int i = userDao.updateByPrimaryKeySelective(user);
        if (i != 1) {
            throw new Exception(EnumErrCode.UpdatePasswordFailed.getRetInfo());
        } else {
            return GeneralReturnMessage.success("修改密码成功");
        }
    }

    /**
     * 用户新增
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage insertUser(User user) throws Exception {
        //检查用户名是否已经存在
        User userCount = userDao.queryByUserName(user.getUserName());
        if (userCount != null) {
            return GeneralReturnMessage.fail(EnumErrCode.UserNameIsExisted);
        }
        //检查手机号是否被占用
        userCount = userDao.queryByMobile(user.getMobile());
        if (userCount != null) {
            return GeneralReturnMessage.fail(EnumErrCode.MOBILE_IS_OCCUPIED);
        }
        int i = userDao.insert(user);
        if (i != 1) {
            throw new Exception(EnumErrCode.InsertUserFailed.getRetInfo());
        } else {
            return GeneralReturnMessage.success("新增用户成功");
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @param smsCode
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage registerUser(User user, String smsCode) throws Exception {
        //对比验证码
        String sCode = template.opsForValue().get(buildSmsCodeKey(user.getMobile()));
        if (sCode == null) {
            return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_IS_EXISTS);
        }
        if (!smsCode.equals(sCode)) {
            return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_IS_INVALID);
        }
        //检查用户名是否已经存在
        User userCount = userDao.queryByUserName(user.getUserName());
        if (userCount != null) {
            return GeneralReturnMessage.fail(EnumErrCode.UserNameIsExisted);
        }
        //检查手机号是否被占用
        userCount = userDao.queryByMobile(user.getMobile());
        if (userCount != null) {
            return GeneralReturnMessage.fail(EnumErrCode.MOBILE_IS_OCCUPIED);
        }
        int i = userDao.insert(user);
        if (i != 1) {
            throw new Exception(EnumErrCode.InsertUserFailed.getRetInfo());
        } else {
            return GeneralReturnMessage.success("注册用户成功");
        }
    }

    /**
     * 用户删除
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage deleteUser(Long userId) throws Exception {
        User userCount = userDao.selectByPrimaryKey(userId);
        if (userCount == null) {
            return GeneralReturnMessage.fail(EnumErrCode.UserDoesNotExist);
        }
        int i = userDao.deleteByPrimaryKey(userId);
        if (i != 1) {
            throw new Exception("删除用户失败");
        } else {
            return GeneralReturnMessage.success("删除用户成功");
        }
    }

    /**
     * 通过手机号加验证码登录
     *
     * @param mobile
     * @param smsCode
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage loginBySms(String mobile, String smsCode) throws Exception {
        //对比验证码
        String sCode = template.opsForValue().get(buildSmsCodeKey(mobile));
        if (sCode == null) {
            return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_IS_EXISTS);
        }
        if (!smsCode.equals(sCode)) {
            return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_IS_INVALID);
        }
        User user = userDao.queryByMobile(mobile);
        if (user == null) {
            return GeneralReturnMessage.fail(EnumErrCode.UserDoesNotExist);
        }
        //验证成功将用户实列返回
        return GeneralReturnMessage.success(user);
    }

    //发送普通短信
    @Override
    public GeneralReturnMessage sendSmsNormal(String mobile)throws Exception{
        log.info("开始发送短信：{}",mobile);
        String verify = RandomUtil.randomNumbers(6);
        SmsNormalEntity smsNormalEntity = new SmsNormalEntity();
        smsNormalEntity.setApId(smsProperties.getApId());
        smsNormalEntity.setEcName(smsProperties.getEcName());
        smsNormalEntity.setSign(smsProperties.getSign());
        smsNormalEntity.setSecretKey(smsProperties.getSecretKey());
        smsNormalEntity.setMobiles(mobile);
        smsNormalEntity.setAddSerial("");
        smsNormalEntity.setContent("您的短信验证码是"+verify);
        String mac = SmsUtils.paramCheckMac(smsNormalEntity.getEcName(),smsNormalEntity.getApId(),
                smsNormalEntity.getSecretKey(),smsNormalEntity.getMobiles(),
                smsNormalEntity.getContent(),smsNormalEntity.getSign(),smsNormalEntity.getAddSerial());
        if (StringUtils.isBlank(mac)){
            return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_SEND_FAILED);
        }
        smsNormalEntity.setMac(mac);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ecName",smsNormalEntity.getEcName());
        jsonObject.put("apId",smsNormalEntity.getApId());
        jsonObject.put("mobiles",smsNormalEntity.getMobiles());
        jsonObject.put("content",smsNormalEntity.getContent());
        jsonObject.put("sign",smsNormalEntity.getSign());
        jsonObject.put("addSerial",smsNormalEntity.getAddSerial());
        jsonObject.put("mac",smsNormalEntity.getMac());
//        base64
        String encodeString = Base64.encode(JSONObject.toJSONString(jsonObject).getBytes());
        HttpResponse httpResponse = HttpRequest.post(smsProperties.getNormalUrl())
                .body(encodeString)
                .header(Header.CONTENT_TYPE,"utf-8")
                .header(Header.ACCEPT_CHARSET,"utf-8")
                .header(Header.USER_AGENT,"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
                .header(Header.CONNECTION,"Keep-Alive")
                .header(Header.ACCEPT,"*/*")
                .timeout(10000)
                .execute();
        if (httpResponse.getStatus()==200){
            String res = httpResponse.body();
            log.info("发送短信完成，发送结果：{}",res);
            JSONObject object = JSONObject.parseObject(res);
            if (object.getBoolean("success")){
//                发送成功，将验证码存入redis
                template.opsForValue().set("sms_code_"+mobile,verify,30, TimeUnit.MINUTES);
                return GeneralReturnMessage.success("发送成功");
            }else {
                return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_SEND_FAILED.getRetCode(),EnumErrCode.SMS_CODE_SEND_FAILED.getRetInfo());
            }
        }else {
            return GeneralReturnMessage.fail(EnumErrCode.SMS_CODE_SEND_FAILED.getRetCode(),EnumErrCode.SMS_CODE_SEND_FAILED.getRetInfo());
        }
    }

    /**
     * 修改用户的角色
     *
     * @param userId         被选中用户
     * @param currentUserId  当前用户
     * @param currentRoleList 当前选择的角色列表
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage updateUserRole(Long userId, Long currentUserId, List<Long> currentRoleList) throws Exception {
        //获得当前用户的角色集合
        List<UserRole> userIdRole = userRoleDao.queryAll(new UserRole().setUserId(userId));
        //获得当前用户的角色集合
        List<Long> userRoleLong = userIdRole.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //需要删除的角色集合
        List<Long> deleteRoleList = ArrayListUtitl.deleteListLong(userRoleLong, currentRoleList);
        //需要新增的角色集合
        List<Long> insertRoleList = ArrayListUtitl.insertListLong(userRoleLong, currentRoleList);
        //循环删除用户角色集合
        for (Long role : deleteRoleList) {
            //通过用户id和角色id查询是否已经有
            List<UserRole> userRoleList = userRoleDao.queryAll(new UserRole().setRoleId(role).setUserId(userId));
            //如果已经没有退出本次循环
            if (userRoleList == null || userRoleList.size() == 0) {
                continue;
            }
            //循环删除存在的用户角色
            for (UserRole userRole : userRoleList) {
                int i = userRoleDao.deleteById(userRole.getUserRoleId());
                if (i != 1) {
                    throw new Exception("删除用户角色失败");
                }
            }
        }
        //循环需要插入用户角色的集合
        for (Long role : insertRoleList) {
            //根据用户id和角色id查询是否已经有
            List<UserRole> userRoleList = userRoleDao.queryAll(new UserRole().
                    setRoleId(role).setUserId(userId));
            //如果已经有则退出本次循环
            if (userRoleList != null && userRoleList.size() > 0) {
                continue;
            }
            UserRole userRole = new UserRole();
            userRole.setRoleId(role).setUserId(userId).setCreateTime(new Date()).setCreator(currentUserId).
                    setModifier(currentUserId).setModifyTime(new Date()).setDeleteFlag("N");
            //执行插入
            int i = userRoleDao.insert(userRole);
            if (i != 1) {
                throw new Exception("新增用户角色失败");
            }
        }
        return GeneralReturnMessage.success("更新用户角色成功");
    }

    /**
     * 查询公司id下的所有用户
     * @param companyId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public  GeneralReturnMessage queryUserByCompany(Long companyId,String userName) throws Exception{
        if (null==companyId){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        List<User> list = userDao.queryUserByCompany(companyId,userName);
        return GeneralReturnMessage.success(list);
    }

    /**
     * 查询公司id下的所有用户
     * @param companyId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public  GeneralReturnMessage queryUserByCompanyByPage(Long companyId,String userName,Integer pageNum,Integer pageSize) throws Exception{
        if (null==companyId){
            return GeneralReturnMessage.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<User> list = userDao.queryUserByCompany(companyId,userName);
        return GeneralReturnMessage.success(list,page.getTotal());
    }



}
