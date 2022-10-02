package org.hewo.ucenter.service;

import org.hewo.ucenter.model.dto.AuthDto;
import org.hewo.ucenter.model.vo.AuthVo;

public interface IAuthService {

    AuthVo accessToken(AuthDto req);

    AuthVo refreshToken(String refreshToken);
}
