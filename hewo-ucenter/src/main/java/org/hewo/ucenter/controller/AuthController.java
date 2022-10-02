package org.hewo.ucenter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hewo.infrastructure.annotation.PassToken;
import org.hewo.infrastructure.controller.BaseRestController;
import org.hewo.infrastructure.model.request.ModelRequest;
import org.hewo.infrastructure.model.response.ModelResponse;
import org.hewo.infrastructure.utils.R;
import org.hewo.ucenter.constants.ApiConstants;
import org.hewo.ucenter.model.dto.AuthDto;
import org.hewo.ucenter.model.vo.AuthVo;
import org.hewo.ucenter.service.IAuthService;
import org.hewo.ucenter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户授权")
@RestController
@RequestMapping(ApiConstants.AUTH)
public class AuthController extends BaseRestController {

    @Autowired
    private IAuthService iAuthService;

    @PassToken
    @ApiOperation("登录（获取token）")
    @PostMapping("accessToken")
    private ModelResponse<AuthVo> accessToken(@RequestBody ModelRequest<AuthDto> req){
        AuthVo entity = iAuthService.accessToken(req.getData());
        return entity == null ? R.error(10103, entity) : R.success(entity);
    }

    @PassToken
    @GetMapping("refreshToken")
    @ApiOperation(value = "刷新token")
    public ModelResponse<AuthVo> refreshToken(@RequestParam String refreshToken) {
        AuthVo entity = this.iAuthService.refreshToken(refreshToken);
        return entity == null ? R.error(10003, "RefreshTokenError", entity) : R.success(entity);
    }
}
