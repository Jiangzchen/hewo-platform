package org.hewo.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.hewo.ucenter.model.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
