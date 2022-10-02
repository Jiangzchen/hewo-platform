package org.hewo.ucenter.service;

import org.hewo.infrastructure.model.response.PageResponse;
import org.hewo.ucenter.model.dto.AuthDto;
import org.hewo.ucenter.model.dto.UserDto;
import org.hewo.ucenter.model.entity.User;
import org.hewo.ucenter.model.vo.AuthVo;
import org.hewo.ucenter.model.vo.UserVo;

public interface IUserService {

    Long create(User req);

    PageResponse<UserVo> search(int page, int pageSize, UserDto req);
}
