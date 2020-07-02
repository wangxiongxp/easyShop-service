package com.aliboy.common.api;

import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.security.PrincipalUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 获取上下文信息描述
 * @Author: WangXiong
 * @Date: Created on 2020-04-24 12:31
 */
@RestController
@RequestMapping("${api}")
@Api(tags = "001000－版本说明")
public class About {

    @Value("${easyShop.build.name}")
    private String name;
    @Value("${easyShop.build.version}")
    private String version;
    @Value("${easyShop.build.timestamp}")
    private String timestamp;

    @RequestMapping(value = "/about", method = {RequestMethod.GET})
    @ApiOperation(value = "版本说明", notes = "版本说明")
    public Map<String, String> about() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("version", version);
        map.put("date", formatDate(timestamp));
        return map;
    }

    @RequestMapping(value = "/context", method = {RequestMethod.GET})
    @ApiOperation(value = "上下文", notes = "上下文")
    public ResultDto context() {
        String userName = PrincipalUtils.getUserName();
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        return new ResultDto(map);
    }

    private String formatDate(String date){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date1 = sdf1.parse(date);
            return sdf2.format(date1);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
