package org.hewo.ucenter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hewo.infrastructure.constant.BaseApiConstants;
import org.hewo.infrastructure.model.request.ModelRequest;
import org.hewo.infrastructure.model.request.PageRequest;
import org.hewo.infrastructure.model.response.BaseResponse;
import org.hewo.infrastructure.model.response.PageResponse;
import org.hewo.infrastructure.utils.R;
import org.hewo.ucenter.constants.ApiConstants;
import org.hewo.ucenter.model.dto.UserDto;
import org.hewo.ucenter.model.entity.User;
import org.hewo.ucenter.model.vo.UserVo;
import org.hewo.ucenter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理")
@RestController
@RequestMapping(ApiConstants.USER)
public class UserController {
    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "查询")
    @PostMapping(BaseApiConstants.SEARCH)
    public PageResponse<UserVo> search(@RequestBody PageRequest<UserDto> req) {
        return iUserService.search(req.getPage(), req.getPageSize(), req.getData());
    }

    @ApiOperation(value = "新增")
    @PostMapping(BaseApiConstants.CREATE)
    public BaseResponse create(@RequestBody ModelRequest<User> req) {
        Long id = iUserService.create(req.getData());
        return null == id ? R.error(10002, "SaveError") : R.success(id);
    }
}
