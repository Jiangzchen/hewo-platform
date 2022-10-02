package org.hewo.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.hewo.infrastructure.exception.BusinessException;
import org.hewo.infrastructure.utils.JwtUtil;
import org.hewo.infrastructure.utils.RedisUtil;
import org.hewo.infrastructure.utils.StringUtil;
import org.hewo.ucenter.mapper.UserMapper;
import org.hewo.ucenter.model.dto.AuthDto;
import org.hewo.ucenter.model.entity.User;
import org.hewo.ucenter.model.vo.AuthVo;
import org.hewo.ucenter.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public AuthVo accessToken(AuthDto req) {
        AuthVo authVo = new AuthVo();
        if (StringUtils.isEmpty(req.getUserCode())) {
            throw new BusinessException(10103, "NotNull");
        } else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("code",req.getUserCode());
            User user = userMapper.selectOne(wrapper);

            if (null == user) {
                throw new BusinessException(10103, "loginError");
            } else {
                String md5Str = req.getPassword(); // Md5Util.getMD5Str(req.getPassword());
                if (!md5Str.equals(user.getPassword())) {
                    throw new BusinessException(10103, "loginError");
                }

                String refreshToken = StringUtil.getUUID();
                String token = JwtUtil.sign(user.getId(), user.getCode());
                this.redisUtil.hset(refreshToken, "token", token);
                authVo.setUserId(user.getId());
                authVo.setUserCode(user.getCode());
                authVo.setUserName(user.getName());
                authVo.setToken(token);
                authVo.setRefreshToken(refreshToken);
                return authVo;
            }
        }
    }

    @Override
    public AuthVo refreshToken(String refreshToken) {
        Long userId = (Long)this.redisUtil.hget(refreshToken, "userId");
        if (StringUtils.isEmpty(userId)) {
            return null;
        } else {
            AuthVo authVo = new AuthVo();
            String userCode = (String)this.redisUtil.hget(refreshToken, "userCode");
            String userName = (String)this.redisUtil.hget(refreshToken, "userName");
            String newToken = JwtUtil.sign(userId, userCode);
            this.redisUtil.hset(refreshToken, "token", newToken);

            authVo.setUserId(userId);
            authVo.setUserCode(userCode);
            authVo.setUserName(userName);
            authVo.setToken(newToken);
            authVo.setRefreshToken(refreshToken);
            return authVo;
        }
    }
}
