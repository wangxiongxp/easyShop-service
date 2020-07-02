package com.aliboy.api.center;

import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.dto.center.OrderItemsCommentBO;
import com.aliboy.model.entity.OrderGoods;
import com.aliboy.service.center.MyCommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public ResultDto pending(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {



        List<OrderGoods> list = myCommentsService.queryPendingComment(orderId);

        return getResultDto(list);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public ResultDto saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam Integer userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        myCommentsService.saveComments(orderId, userId, commentList);
        return getResultDto();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public ResultDto query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        PagedGridResult grid = myCommentsService.queryMyComments(userId,
                page,
                pageSize);

        return getResultDto(grid);
    }

}
