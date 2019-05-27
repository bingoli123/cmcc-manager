package com.cmcc.controller.user;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.annotation.Log;
import com.cmcc.controller.base.BaseController;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.enums.RedisKeyEnum;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.User;
import com.cmcc.service.project.UserProjectService;
import com.cmcc.service.user.OperationLogService;
import com.cmcc.service.user.UserService;
import com.cmcc.utils.MD5Utils;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息controller
 *
 * @Create 2019-01-09
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private OperationLogService operationLogServiceImpl;
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private UserProjectService userProjectServiceImpl;

    @PostMapping(value = "/test")
    public ResponseEntity test(){
        try{
            userServiceImpl.test(111L);
            return ResponseEntity.fail("2222222","2342443224");
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail("000001","查询失败");
        }
    }

    private static final String CODE_KEY = "_code";

    /**
     * @param userName
     * @param passWord
     * @param rememberMe
     * @return
     */
    @Log("通过手机号密码登录")
    @GetMapping("/login")
    public ResponseEntity login(@RequestParam(value = "userName") String userName,
                                @RequestParam(value = "passWord") String passWord,
                                @RequestParam(value = "rememberMe") Boolean rememberMe) {
        try {
            Session session = super.getSession();
            String sessionCode = (String) session.getAttribute(CODE_KEY);
            // 密码 MD5 加密
            passWord = MD5Utils.encrypt(passWord);
            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord, rememberMe);
            Subject subject = getSubject();
            if (subject != null) {
                subject.logout();
            }
            super.login(token);
            return ResponseEntity.ok("登录成功！");
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            return ResponseEntity.fail(EnumErrCode.UserDoesNotExist.getRetCode(), EnumErrCode.UserDoesNotExist.getRetInfo());
        } catch (AuthenticationException e) {
            return ResponseEntity.fail(EnumErrCode.IncorrectPassword.getRetCode(), EnumErrCode.IncorrectPassword.getRetInfo());
        } catch (Exception e) {
            log.error("用户登录失败：{}", e.toString());
            return ResponseEntity.fail("2222222", "登陆失败");
        }
    }

    /**
     * 通过手机号密码登录
     *
     * @param mobile     手机号
     * @param smsCode    验证码啊
     * @param rememberMe 记住我
     * @return
     */
    @Log("通过手机号和验证码登录")
    @GetMapping("/loginBySms")
    public ResponseEntity loginBySms(@RequestParam("mobile") String mobile,
                                     @RequestParam("smsCode") String smsCode,
                                     @RequestParam("rememberMe") Boolean rememberMe) {
        try {
            GeneralReturnMessage generalReturnMessage = userServiceImpl.loginBySms(mobile, smsCode);
            if (generalReturnMessage.isRequestFlag()) {
                User user = (User) generalReturnMessage.getData();
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword(), rememberMe);
                Subject subject = getSubject();
                if (subject != null) {
                    subject.logout();
                }
                super.login(token);
                return ResponseEntity.ok("登录成功");
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return ResponseEntity.fail("", e.getMessage());
        }
    }


    /**
     * 用户退出
     *
     * @return
     */
    @GetMapping("/logout")
    public ResponseEntity logout() {
        try {
            Subject subject = getSubject();
            if (subject != null) {
                subject.logout();
            }
            return ResponseEntity.ok("退出成功");
        } catch (Exception e) {
            return ResponseEntity.fail("2222222", e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @param oldPassword 老密码
     * @param newPassword 新密码
     * @return
     */
    @Log("修改密码")
    @RequiresPermissions("user:updatePassword")
    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestParam(value = "oldPassword") String oldPassword,
                                         @RequestParam(value = "newPassword") String newPassword) {
        log.info("用户在执行修改密码操作");
        try {
            //将新密码加密
            oldPassword = MD5Utils.encrypt(oldPassword);
            //将旧密码加密
            newPassword = MD5Utils.encrypt(newPassword);
            //获得当前用户
            User user = super.getCurrentUser();
            GeneralReturnMessage generalReturnMessage = userServiceImpl.updatePassword(user.getPassword(), oldPassword, newPassword, user.getId());
            if (generalReturnMessage.isRequestFlag()) {
                //修改密码成功执行退出登录
                Subject subject = getSubject();
                if (subject != null) {
                    subject.logout();
                }
                return ResponseEntity.ok("修改密码成功");
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("用户修改密码失败，{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 修改用户的角色
     *
     * @param userId 需要修改的用户ID
     * @param roleIds
     * @return
     */
    @Log("修改用户的角色")
    @PostMapping("/updateUserRole")
    @RequiresPermissions("user:updateUserRole")
    public ResponseEntity updateUserRole(@RequestParam(value = "userId") Long userId,
                                         @RequestParam(value = "roleIds") Long[] roleIds) {
        //当前操作用户不能为空
        if (userId == null) {
            return ResponseEntity.fail(EnumErrCode.UserIdIsBlank);
        }
        //role不能为空
        if (roleIds == null) {
            return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        try {
            //获得当前用户
            User currentUser = super.getCurrentUser();
            GeneralReturnMessage generalReturnMessage = userServiceImpl.updateUserRole(userId, currentUser.getId(), Arrays.asList(roleIds));
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                log.error("修改用户的角色失败,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("修改用户的角色失败,{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }


    /**
     * 修改用户的项目
     *
     * @param userId  用户id
     * @param projectIds
     * @return
     */
    @Log("修改用户的项目")
    @PostMapping("/updateUserProject")
    @RequiresPermissions("user:updateUserProject")
    public ResponseEntity updateUserProject(@RequestParam(value = "userId") Long userId,
                                            @RequestParam(value = "projectIds") Long[] projectIds) {
        //当前操作用户不能为空
        if (userId == null) {
            return ResponseEntity.fail(EnumErrCode.UserIdIsBlank);
        }
        //project不能为空
        if (projectIds == null) {
            return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        try {
            //获得当前用户
            User currentUser = super.getCurrentUser();
            GeneralReturnMessage generalReturnMessage = userProjectServiceImpl.updateUserProject(userId, currentUser.getId(), Arrays.asList(projectIds));
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                log.error("修改用户的项目失败,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("修改用户项目失败,{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }


    /**
     * 用户新增
     *
     * @param userName    用户名
     * @param password    密码
     * @param email       邮箱
     * @param mobile      手机号
     * @param sex         性别
     * @param companyId   公司ID
     * @param description 描述
     * @return
     */
    @Log("用户新增")
    @RequiresPermissions("user:insert")
    @PostMapping("/insertUser")
    public ResponseEntity insertUser(@RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "email") String email,
                                     @RequestParam(value = "mobile") String mobile,
                                     @RequestParam(value = "sex") String sex,
                                     @RequestParam(value = "companyId", required = false) Long companyId,
                                     @RequestParam(value = "description") String description) {
        try {
            //将密码加密
            password = MD5Utils.encrypt(password);
            //获得当前用户
            User userCurrent = (User) getSubject().getPrincipal();
            User userInsert = new User();
            userInsert.setUserName(userName);
            userInsert.setPassword(password);
            userInsert.setEmail(email);
            userInsert.setCreateTime(new Date());
            userInsert.setModifyTime(new Date());
            userInsert.setMobile(mobile);
            userInsert.setSex(sex);
            userInsert.setCompanyId(userCurrent.getCompanyId());
            userInsert.setDeleteFlag("N");
            userInsert.setCreator(userCurrent.getId());
            userInsert.setModifier(userCurrent.getId());
            userInsert.setStatus("1");
            userInsert.setCreator(userCurrent.getId());
            userInsert.setModifier(userCurrent.getId());
            userInsert.setDescription(description);
            GeneralReturnMessage generalReturnMessage = userServiceImpl.insertUser(userInsert);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getRetCode());
            } else {
                log.error("新增用户失败,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("新增用户失败，{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 用户注册
     *
     * @param userName    用户名
     * @param userChName  用户名中文名
     * @param password    密码
     * @param email       邮箱
     * @param mobile      手机号
     * @param smsCode     验证码
     * @param sex         性别
     * @param description 描述
     * @return
     */
    @Log("用户注册")
    @PostMapping("/registerUser")
    public ResponseEntity registerUser(@RequestParam(value = "userName") String userName,
                                       @RequestParam(value = "userChName") String userChName,
                                       @RequestParam(value = "password") String password,
                                       @RequestParam(value = "email", required = false) String email,
                                       @RequestParam(value = "mobile") String mobile,
                                       @RequestParam(value = "smsCode") String smsCode,
                                       @RequestParam(value = "sex", required = false) String sex,
                                       @RequestParam(value = "description", required = false) String description) {
        try {
            //将密码加密
            password = MD5Utils.encrypt(password);
            //获得当前用户
            User userInsert = new User();
            userInsert.setUserName(userName);
            userInsert.setUserChName(userChName);
            userInsert.setCreateTime(new Date());
            userInsert.setModifyTime(new Date());
            userInsert.setPassword(password);
            userInsert.setEmail(email);
            userInsert.setMobile(mobile);
            userInsert.setSex(sex);
            userInsert.setDeleteFlag("N");
            userInsert.setCreator(1L);
            userInsert.setModifier(1L);
            userInsert.setStatus("1");
            userInsert.setDescription(description);
            GeneralReturnMessage generalReturnMessage = userServiceImpl.registerUser(userInsert, smsCode);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getRetCode());
            } else {
                log.error("注册用户失败,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("用户新增失败，{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Log("删除用户")
    @RequiresPermissions("user:delete")
    @GetMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam(value = "userId") Long userId) {
        try {
            GeneralReturnMessage generalReturnMessage = userServiceImpl.deleteUser(userId);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("删除用户成功");
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("用户删除失败，{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }


    //发送短信验证码
    @PostMapping("/sendSmsCode")
    public ResponseEntity sendSmsCode(@RequestParam(value = "mobile") String mobile){
        try{
            log.info("执行发送短信接口：{}",mobile);
            if (StringUtils.isBlank(mobile)){
                return ResponseEntity.fail(EnumErrCode.SMS_CODE_SEND_FAILED);
            }
            GeneralReturnMessage generalReturnMessage = userServiceImpl.sendSmsNormal(mobile);
            log.info("发送结果：{}", JSONObject.toJSONString(generalReturnMessage));
            if(!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }else {
                return ResponseEntity.ok("");
            }
        }catch(Exception e){
          log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.SMS_CODE_SEND_FAILED);
        }

    }

    /**
     * @desc:
     * @param: 获取图片验证码，生成base64验证码
     * @return:
     * @auther: lidongbin
     * @date: 2019-05-10 14:54
     */
    @GetMapping("/getCaptcha")
    public ResponseEntity getCaptchaImg(HttpServletRequest request){
        try{
            log.info("执行生成图片验证码");
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200,100);
            String img = captcha.getImageBase64();
            String code = captcha.getCode();
            String verify = IdUtil.simpleUUID();
            String data = "data:image/png;base64,"+img;
            Map map = new HashMap();
            map.put("captcha",data);
//            log.info("获取图片验证码完成,verify:{}",verify);
            //map.put("verify", verify);
            //template.opsForValue().set(RedisKeyEnum.CAPTCHA_CODE.getKey()+verify,code);
            request.getSession().setAttribute(RedisKeyEnum.CAPTCHA_CODE.getKey(),code);
            log.info("获取图片验证码完成,code:{},img:{}",code,verify);
            return ResponseEntity.ok(map);
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.VERIFY_CHECK_CREATE_FAIL);
        }
    }
    /**
     * @desc: 校验图片验证码
     * @param:  verifyCode 获取图片验证码的verify  code用户输入的验证码
     * @return:
     * @auther: lidongbin
     * @date: 2019-05-10 15:11
     */
    @PostMapping("/checkCaptcha")
    public ResponseEntity checkCaptcha(@RequestParam(value = "code")String code,HttpServletRequest request){
        try{
            log.info("开始校验图片验证码verifyCode:{},sessionId:{}",code,request.getSession().getId());
            if (StringUtils.isBlank(code)){
                return ResponseEntity.fail(EnumErrCode.VERIFY_CHECK_FALSE);
            }
            String redisCode =(String) request.getSession().getAttribute(RedisKeyEnum.CAPTCHA_CODE.getKey());
            log.info("获取session中的验证码",redisCode);
            if (StringUtils.isBlank(redisCode)){
                return ResponseEntity.fail(EnumErrCode.VERIFY_CHECK_FALSE);
            }
            if (redisCode.equalsIgnoreCase(code)){
                return ResponseEntity.ok("校验成功");
            }else {
                return ResponseEntity.fail(EnumErrCode.VERIFY_CHECK_FALSE);
            }
        }catch(Exception e){
          log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.VERIFY_CHECK_FALSE);
        }
    }

//   根据 查询本公司下的所有用户 区分分页与不分页


    /**
     * 查询公司id下的所有用户(无分页)  弹出选择框会用到
     * @return
     * @throws Exception
     */
    @PostMapping("queryUserByCompany")
    public ResponseEntity queryUserByCompany(@RequestParam(value = "userName",required = false) String userName){
        try{

            User user = getCurrentUser();
            log.info("开始查询用户公司下的所有用户 userName：{},userId:{}",userName,user.getId());
            GeneralReturnMessage generalReturnMessage = userServiceImpl.queryUserByCompany(user.getCompanyId(),userName);
            if(!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }else {
                return ResponseEntity.ok(generalReturnMessage.getData());
            }
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }


    /**
     * 查询公司id下的所有用户(分页)
     * @param userName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("queryUserByCompanyByPage")
    public ResponseEntity queryUserByCompanyByPage(@RequestParam(value = "userName",required = false) String userName,
                                                   @RequestParam(value = "pageNum")Integer pageNum,
                                                   @RequestParam(value = "pageSize")Integer pageSize){
        try{
            User user = getCurrentUser();
            log.info("开始查询用户公司下的所有用户 userName：{},userId:{},pageNum:{},pageSize:{}",userName,user.getId(),pageNum,pageSize);
            GeneralReturnMessage generalReturnMessage = userServiceImpl.queryUserByCompanyByPage(user.getCompanyId(),userName,pageNum,pageSize);
            if(!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }else {
                return ResponseEntity.ok(generalReturnMessage.getData(),generalReturnMessage.getPageCount());
            }
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }


}
