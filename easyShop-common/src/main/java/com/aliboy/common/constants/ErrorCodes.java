package com.aliboy.common.constants;

import com.aliboy.common.exception.ErrorCode;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:30
 */
public interface ErrorCodes {
    ErrorCode OPERATE_SUCCESS = new ErrorCode(0, "message.operate.success");
    ErrorCode OPERATE_FAILED = new ErrorCode(1, "message.operate.failed");

    ErrorCode ERROR_401_MSG = new ErrorCode(401, "error.401.msg");
    ErrorCode ERROR_403_MSG = new ErrorCode(403, "error.403.msg");
    ErrorCode ERROR_500_MSG = new ErrorCode(500, "error.500.msg");

    ErrorCode ERROR_COMMON = new ErrorCode(100000, "error.common");
    ErrorCode ERROR_ARGUMENT_ILLEGAL = new ErrorCode(100001, "error.argument.illegal");
    ErrorCode ERROR_ARGUMENT_EMPTY = new ErrorCode(100002, "error.argument.empty");
    ErrorCode ERROR_ARGUMENT_EXIST = new ErrorCode(100003, "error.argument.exist");
    ErrorCode ERROR_ARGUMENT_NOT_EXIST = new ErrorCode(100004, "error.argument.not.exist");
    ErrorCode ERROR_OBJECT_EMPTY = new ErrorCode(100005, "error.object.empty");
    ErrorCode ERROR_OBJECT_EXIST = new ErrorCode(100006, "error.object.exist");
    ErrorCode ERROR_OBJECT_NOT_EXIST = new ErrorCode(100007, "error.object.not.exist");

    ErrorCode ERROR_MODEL_CODE_EXIST = new ErrorCode(110001, "error.model.code.exist");
    ErrorCode ERROR_MODEL_NAME_EXIST = new ErrorCode(110001, "error.model.name.exist");
    ErrorCode ERROR_MODEL_DATA_NOT_EXIST = new ErrorCode(110002, "error.model.data.not.exist");
    ErrorCode ERROR_MODEL_DATA_HAS_ONLINED = new ErrorCode(110003, "error.model.data.has.onlined");
    ErrorCode ERROR_MODEL_DATA_HAS_OFFLINED = new ErrorCode(110004, "error.model.data.has.offlined");
    ErrorCode ERROR_MODEL_DATA_HAS_AUDITED = new ErrorCode(110005, "error.model.data.has.audited");
    ErrorCode ERROR_MODEL_STATUS_CANNOT_DELETE = new ErrorCode(110006, "error.model.status.cannot.delete");
    ErrorCode ERROR_MODEL_STATUS_CANNOT_UPDATE = new ErrorCode(110007, "error.model.status.cannot.update");
    ErrorCode ERROR_MODEL_DATA_SYSTEM_CANNOT_DELETE = new ErrorCode(110008, "error.model.data.system.cannot.delete");
    ErrorCode ERROR_MODEL_DATA_SYSTEM_CANNOT_UPDATE = new ErrorCode(110009, "error.model.data.system.cannot.update");

    ErrorCode ERROR_LOGIN_USERNAME_PASSWORD_ERROR = new ErrorCode(150001, "error.login.username.password.error");
    ErrorCode ERROR_WECHAT_HAS_BIND = new ErrorCode(150001, "error.wechat.has.bind");
    ErrorCode ERROR_WECHAT_BIND_FAIL = new ErrorCode(150001, "error.wechat.bind.error");
    ErrorCode ERROR_WECHAT_CREDENTIALS_EMPTY = new ErrorCode(150001, "error.wechat.credentials.empty");

    ErrorCode ERROR_CATEGORY_NOT_EXIST = new ErrorCode(200001, "error.category.not.exist");

}
