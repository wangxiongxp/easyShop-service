package com.aliboy.api;

import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.security.PrincipalUtils;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.dto.UserAddressDto;
import com.aliboy.model.vo.UserAddressVO;
import com.aliboy.service.AddressService;
import com.aliboy.web.store.UserStoreManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${api}/address")
@RestController
@Api(value = "地址相关", tags = "170000--地址信息相关接口")
public class AddressController extends BaseController {

    @Autowired
    private UserStoreManager userStoreManager;

    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "GET")
    @GetMapping("/list")
    public ResultDto list() {

        int userId = PrincipalUtils.getUserIdInt();

        List<UserAddressVO> list = addressService.queryAllByUserId(userId);
        return getResultDto(list);
    }

    @ApiOperation(value = "用户保存地址", notes = "用户保存地址", httpMethod = "POST")
    @PostMapping("/save")
    public ResultDto save(@RequestBody UserAddressDto userAddressDto) {

        userAddressDto.setCountry("CN");
        userAddressDto.setUserId(PrincipalUtils.getUserIdInt());
        addressService.addNewUserAddress(userAddressDto);

        return getResultDto();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public ResultDto update(@RequestBody UserAddressDto userAddressDto) {

        addressService.updateUserAddress(userAddressDto);

        return getResultDto();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public ResultDto delete(@RequestParam Integer id) {

        addressService.deleteUserAddress(id);
        return getResultDto();
    }

    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public ResultDto setDefalut(@RequestParam Integer addressId) {

        addressService.updateUserAddressToDefault(PrincipalUtils.getUserIdInt(), addressId);
        return getResultDto();
    }

}
