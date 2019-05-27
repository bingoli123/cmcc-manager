package com.cmcc.enums;

/**
 * Copyright (C), 2019-2019,
 * FileName: EnumErrCode
 * Author:   a
 * Date:     2019 5 7 0007 15:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public enum EnumErrCode {

    /*用户*/
    UserDoesNotExist("00001", "用户不存在"),
    IncorrectPassword("00002", "密码不正确"),
    AuthenticationFailed("00003", "认证失败"),
    UpdatePasswordFailed("00004", "修改密码失败"),
    InsertUserFailed("00005", "新增用户失败"),
    UserNameIsExisted("00005", "用户已经存在"),
    SMS_CODE_IS_INVALID("00006", "无效的验证码"),
    SMS_CODE_SEND_FAILED("00007", "发送验证码失败"),
    MOBILE_IS_OCCUPIED("00008", "手机号已被占用!"),
    SMS_CODE_IS_EXISTS("00009", "验证码不存在"),
    VERIFY_CHECK_FALSE("00010","图片验证码校验失败"),
    VERIFY_CHECK_CREATE_FAIL("00011","图片验证码生成失败"),
    UserIdIsBlank("00012", "用户ID不能为空"),

    /*公司*/
    COMPANY_NAME_NOT_NULL("01001", "公司名称不能为空"),
    COMPANY_INSERT_FAIL("01002", "新增公司失败"),
    COMPANY_INSERT_SUCCESS("01003", "新增公司成功"),
    COMPANY_UPDATE_FAIL("01004", "修改公司信息失败"),
    COMPANY_UPDATE_SUCCESS("01005", "修改公司信息成功"),
    COMPANY_DELETE_FAIL("01006", "删除公司信息失败"),
    COMPANY_DELETE_SUCCESS("01007", "删除公司信息成功"),
    COMPANY_HAS_SAMENAME("01008", "已存在同名的公司"),

    /*角色*/
    RoleNameHaveExists("02001", "角色名已经存在"),
    RoleNameIsBlank("02002", "角色名不能为空"),
    RoleNameNotExists("02003", "角色名不存在"),
    RoleHaveExistsSame("02004", "存在相同的角色"),
    RoleDeleteFail("02005", "删除角色失败"),
    RoleUpdateFail("02006", "修改角色失败"),
    RoleNameAndRoleIdIsBlank("02007", "角色ID和角色名不能同时为空"),
    RoleIdIsBlank("02008", "角色ID不能为空"),
    RoleNotExists("02009", "角色不存在"),
    RoleHaveBindUser("02010", "角色已经绑定用户"),

    /*项目*/
    PROJECT_NAME_HAS_SAME("03001","存在相同名称的项目"),
    PROJECT_AUTH_USER_FAIL("03002","授权用户失败"),

    CommonError("99999", "请求错误"),
    SOME_PARAM_IS_NULL("99998","参数不全");



    private String retCode;
    private String retInfo;

    private EnumErrCode(String retCode, String retInfo) {
        this.retInfo = retInfo;
        this.retCode = retCode;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public String getRetInfo() {
        return this.retInfo;
    }
}
