package com.aliboy.common.web;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.exception.ErrorCode;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    protected ResultDto getResultDto() {
        return this.getResultDto((Object) ErrorCodes.OPERATE_SUCCESS);
    }

    protected ResultDto getResultDto(Object object) {
        if (object instanceof ErrorCode) {
            return this.getFailDto((ErrorCode)object);
        } else {
            return new ResultDto(object);
        }
    }

    protected ResultDto getFailDto(ErrorCode errorCode) {
        return new ResultDto(errorCode);
    }

    protected ResultDto getFailDto(ErrorCode errorCode, Object... args) {
        return new ResultDto(errorCode, args);
    }

}
