package com.aliboy.api;

import com.aliboy.common.cache.CacheService;
import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.LoginDto;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.dto.TokenDto;
import com.aliboy.common.i18n.I18nMessages;
import com.aliboy.common.security.OnlineSessionManager;
import com.aliboy.common.security.PrincipalUtils;
import com.aliboy.common.utils.HttpUtil;
import com.aliboy.common.utils.StringUtils;
import com.aliboy.common.web.BaseController;
import com.aliboy.common.wechat.CodeSessionDto;
import com.aliboy.common.wechat.WeChatConstants;
import com.aliboy.model.entity.User;
import com.aliboy.service.UserService;
import com.aliboy.web.security.PhoneToken;
import com.aliboy.web.security.WechatToken;
import com.aliboy.web.store.UserStoreManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@RestController
@RequestMapping("${api}/auth")
@Api(tags = "100000--安全登录")
public class PassportController extends BaseController {

    @Value("${tenpay.mini.appId}")
    private String MINI_APP_ID;

    @Value("${tenpay.mini.appSecret}")
    private String MINI_APP_SECRET;

    @Value("${tenpay.mini.authUrl}")
    private String MINI_AUTH_URL;

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private UserStoreManager userStoreManager;

    @Autowired
    private OnlineSessionManager onlineSessionManager;

    @Autowired
    protected I18nMessages messageSource;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDto login(@RequestBody LoginDto loginDto, HttpServletRequest request) {

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        return doLogin(username, token, request);
    }

    @RequestMapping(value = "/loginByMobile", method = RequestMethod.POST)
    public ResultDto loginByMobile(@RequestBody LoginDto loginDto, HttpServletRequest request) {

        User user = userService.findUserByMobile(loginDto.getMobile());
        if(ObjectUtils.isEmpty(user)){
            return getFailDto(ErrorCodes.ERROR_COMMON, "未知账户");
        }

        String username = user.getUsername();
        String password = loginDto.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        return doLogin(username, token, request);
    }

    @RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
    public ResultDto phoneLogin(@RequestParam("phone") String phone,
                                @RequestParam("code") String code,
                                HttpServletRequest request) {
        // todo 判断验证码是否正确

        PhoneToken token = new PhoneToken(phone);
        ResultDto resultDto = doLogin(phone, token, request);

        return resultDto;
    }

    @ApiOperation(value = "通过credentials查看用户是否存在")
    @GetMapping("/user/credentials/{credentials}")
    public ResultDto checkUserByUnionId(@PathVariable String credentials) {
        CodeSessionDto sessionDto = (CodeSessionDto) CacheService.getCacheObj(credentials);

        if (null == sessionDto) {
            // 返回凭证不存在或者已过期信息
            return getFailDto(ErrorCodes.ERROR_WECHAT_CREDENTIALS_EMPTY);
        }
        User user = userService.findByOpenId(sessionDto.getUnionId());

        return getResultDto(user);
    }

    @ApiOperation(value = "通过credentials进行登录")
    @PostMapping("/mini/login/{credentials}")
    public ResultDto miniLogin(@PathVariable String credentials, HttpServletRequest request) {
        CodeSessionDto sessionDto = (CodeSessionDto) CacheService.getCacheObj(credentials);

        if (null == sessionDto) {
            // 返回凭证不存在或者已过期信息
            return getFailDto(ErrorCodes.ERROR_WECHAT_CREDENTIALS_EMPTY);
        }

        User user = userService.findByOpenId(sessionDto.getUnionId());

        if (ObjectUtils.isEmpty(user)) {
            return getFailDto(ErrorCodes.ERROR_LOGIN_USERNAME_PASSWORD_ERROR);
        }

        WechatToken token = new WechatToken(user.getUsername(), sessionDto.getUnionId());

        ResultDto resultDto = doLogin(user.getUsername(), token, request);

        // 登录成功时将openId等信息放入session
        if (resultDto.getErrorCode() == ErrorCodes.OPERATE_SUCCESS.getCode()) {
            Session session = SecurityUtils.getSubject().getSession();
            String sessionId = session.getId().toString();
            session.setAttribute(sessionId, sessionDto);
        }
        return resultDto;
    }

    private ResultDto doLogin(String username, UsernamePasswordToken token, HttpServletRequest request) {
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();

        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            subject.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");


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
            logger.warn("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下" + err.toString());
            return getFailDto(ErrorCodes.ERROR_COMMON, "用户名或密码错误");
        }

        TokenDto tokenDto = null;
        if (subject.isAuthenticated()) {
            Session session = subject.getSession();
            logger.info("pass [" + username + "] Login validation, SessionId:" + session.getId());

            //防止多点登陆
            putSessionToCache();
            tokenDto = new TokenDto(PrincipalUtils.getUserName(),
                    subject.getSession().getId().toString(),
                    subject.getSession().getTimeout());
        }else {
            token.clear();
        }

        // todo 登录成功后要同步购物车数据

        return getResultDto(tokenDto);
    }

    @ApiOperation(value = "通过credentials登录来绑定用户")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResultDto bindUser(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        if (StringUtils.isBlank(loginDto.getCredentials())) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_ILLEGAL, "credentials");
        }

        CodeSessionDto sessionDto = (CodeSessionDto) CacheService.getCacheObj(loginDto.getCredentials());
        if (null == sessionDto) {
            // 返回凭证不存在或者已过期信息
            return getFailDto(ErrorCodes.ERROR_WECHAT_CREDENTIALS_EMPTY);
        }

        String username = loginDto.getUsername();
        User user = userService.findUserByName(username);

        // 用户不存在
        if (null == user) {
            return getFailDto(ErrorCodes.ERROR_LOGIN_USERNAME_PASSWORD_ERROR);
        }

        // 判断当前用户是否已经被绑定
        if (!ObjectUtils.isEmpty(user.getWechatOpenId())) {
            return getFailDto(ErrorCodes.ERROR_WECHAT_HAS_BIND);
        }

        User bindUser = userService.findByOpenId(sessionDto.getUnionId());
        boolean needBind = true;

        if (!ObjectUtils.isEmpty(bindUser)) {
            if (bindUser.getUsername().equals(username)) {
                needBind = false;
            } else {
                // 该UnionId已经被其他用户绑定
                return getFailDto(ErrorCodes.ERROR_WECHAT_HAS_BIND);
            }
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, loginDto.getPassword());
        ResultDto resultDto = doLogin(username, token, request);

        if (resultDto.getErrorCode() != ErrorCodes.OPERATE_SUCCESS.getCode()) {
            return resultDto;
        }

        // 绑定处理
        if (needBind) {
            Boolean result = userService.bindOpenId(user.getId(), sessionDto.getUnionId());

            if (!result) {
                // 退出登录
                SecurityUtils.getSubject().logout();
                // 返回绑定失败信息
                return getFailDto(ErrorCodes.ERROR_WECHAT_BIND_FAIL);
            }
        }

        Session session = SecurityUtils.getSubject().getSession();
        String sessionId = session.getId().toString();
        session.setAttribute(sessionId, sessionDto);

        return resultDto;
    }

    /**
     * 检查是否存在同一个账户多点登陆(踢出先登陆者)
     */
    private void putSessionToCache() {
        Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
        String username = PrincipalUtils.getUserName();
        onlineSessionManager.addOnlineSession(username, sessionId);
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultDto logout(HttpServletRequest request) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SecurityUtils.getSubject().logout();
        }

        // todo 登出成功后要清空购物车数据

        return getResultDto();
    }

    @ApiOperation(value = "通过凭证进而换取用户登录态信息，包括用户的唯一标识")
    @GetMapping("/code2Session/{code}")
    public ResultDto code2Session(@PathVariable String code) {
        logger.info("[code2Session] get session_key by code:" + code);
        String res = HttpUtil.sendGet(String.format(MINI_AUTH_URL, MINI_APP_ID, MINI_APP_SECRET, code));

        if (org.apache.commons.lang3.StringUtils.isBlank(res)) {
            return getFailDto(ErrorCodes.OPERATE_FAILED);
        }

        // 需要将session_key 存起来，不需要下发给客户端
        try {
            CodeSessionDto dto = new CodeSessionDto();
            JSONObject jsonObject = new JSONObject(res);

            dto.setCode(code);
            if (jsonObject.has(WeChatConstants.MiniProgram.ERROR_CODE)) {
                dto.setErrCode(jsonObject.getInt(WeChatConstants.MiniProgram.ERROR_CODE));

                if (dto.getErrCode() != 0) {
                    dto.setErrMsg(jsonObject.getString(WeChatConstants.MiniProgram.ERROR_MSG));
                    return getResultDto(dto);
                }
            }

            // 用户唯一标识
            dto.setOpenId(jsonObject.getString(WeChatConstants.MiniProgram.OPEN_ID));
            // 会话密钥
            dto.setSessionKey(jsonObject.getString(WeChatConstants.MiniProgram.SESSION_KEY));

            if (jsonObject.has(WeChatConstants.MiniProgram.UNION_ID)) {
                // 用户在开放平台的唯一标识符
                dto.setUnionId(jsonObject.getString(WeChatConstants.MiniProgram.UNION_ID));
            }

            String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
            dto.setCredentials(sessionId);

            // 将凭证信息放入redis中
            CacheService.setCache(sessionId, dto, 3600);

            return getResultDto(dto);
        } catch (JSONException e) {
            return getFailDto(ErrorCodes.OPERATE_FAILED);
        }
    }

}
