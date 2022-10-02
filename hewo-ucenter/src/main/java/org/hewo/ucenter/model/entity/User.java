package org.hewo.ucenter.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rbp_user")
public class User {
    private Long id;
    private String code;
    private String name;
    private String password;
}
