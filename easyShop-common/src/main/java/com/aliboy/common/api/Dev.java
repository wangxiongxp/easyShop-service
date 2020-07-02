package com.aliboy.common.api;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.LoginDto;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.exception.BizException;
import com.aliboy.common.utils.PasswordUtils;
import com.aliboy.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev")
@Api(tags = "000000－Dev")
public class Dev extends BaseController {

    final static Logger logger = LoggerFactory.getLogger(Dev.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDto serverlogin(@RequestBody LoginDto loginDto) throws BizException {
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getUsername(), loginDto.getPassword());
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        try {
            logger.info("对用户[" + loginDto.getUsername() + "]进行登录验证..验证开始");
            subject.login(token);
            logger.info("对用户[" + loginDto.getUsername() + "]进行登录验证..验证通过");
        } catch (UnknownAccountException err){
            return getFailDto(ErrorCodes.ERROR_COMMON, "未知账户");
        } catch (IncorrectCredentialsException err) {
            //统计密码输入错误后剩余的输入机会次数给客户端
            int leftInputCount = (int) SecurityUtils.getSubject().getSession().getAttribute("leftInputCount");
            return getFailDto(ErrorCodes.ERROR_COMMON, "还剩余:"+leftInputCount+"次");
        } catch (LockedAccountException err) {
            return getFailDto(ErrorCodes.ERROR_COMMON,"账户已锁定");
        } catch (ExcessiveAttemptsException err){
            return getFailDto(ErrorCodes.ERROR_COMMON, "错误次数过多");
        } catch (AuthenticationException err) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.warn("对用户[" + loginDto.getUsername() + "]进行登录验证..验证未通过,堆栈轨迹如下" + err.toString());
            return getFailDto(ErrorCodes.ERROR_COMMON, "用户名或密码错误");
        }

        return getResultDto(subject.getPrincipal());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultDto serverlogout() throws BizException {
        SecurityUtils.getSubject().logout();
        return new ResultDto();
    }

    /**
     * 通用tool,根据给定密码明文，盐值返回加密后的密文
     */
    @ApiOperation(value = "根据给定密码明文，盐值返回加密后的密文", notes = "根据给定密码明文，盐值返回加密后的密文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plainPass", value = "密码明文", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "salt", value = "加密所需的盐值", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/tools/md5encryption", method = RequestMethod.GET)
    public ResultDto encryptPass( @RequestParam() String  plainPass,
                                  @RequestParam() String salt) throws BizException {
        return new ResultDto(PasswordUtils.encryptPassword(plainPass, salt));
    }

}
