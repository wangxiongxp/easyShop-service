package com.aliboy.api.center;

import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.web.BaseController;
import com.aliboy.web.store.UserStore;
import com.aliboy.web.store.UserStoreManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-05 09:32
 */
@RestController
@RequestMapping("${api}/center")
@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
public class CenterController extends BaseController {

    @Autowired
    private UserStoreManager userStoreManager;

    @ApiOperation(value = "获取当前用户的详情", notes = "-")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResultDto getCusUserProfile() {
        UserStore userStore = userStoreManager.getCurrentUserStore();
        return getResultDto(userStore);
    }
}
