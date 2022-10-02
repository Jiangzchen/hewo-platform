package org.hewo.ucenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hewo.infrastructure.exception.BusinessException;
import org.hewo.infrastructure.model.response.PageResponse;
import org.hewo.infrastructure.utils.SnowFlakeUtil;
import org.hewo.ucenter.mapper.UserMapper;
import org.hewo.ucenter.model.dto.UserDto;
import org.hewo.ucenter.model.entity.User;
import org.hewo.ucenter.model.vo.UserVo;
import org.hewo.ucenter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long create(User req) {
        User user = userMapper.selectOne(new QueryWrapper<User>().select("id").eq("code", req.getCode()));
        if (user != null) {
            throw new BusinessException(10101, "UserCodeExist");
        } else {
            user.setId(SnowFlakeUtil.getDefaultSnowFlakeId());
            userMapper.insert(req);
            return req.getId();
        }
    }

    @Override
    public PageResponse<UserVo> search(int page, int pageSize, UserDto req) {
        Page<UserVo> pageModel = new Page((long)page, (long)pageSize);
        // IPage<UserVo> pages = userMapper.search(pageModel, req);
        return null;
    }
}
