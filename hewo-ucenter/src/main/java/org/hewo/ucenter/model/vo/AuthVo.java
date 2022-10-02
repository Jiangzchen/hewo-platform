package org.hewo.ucenter.model.vo;

import lombok.Data;

@Data
public class AuthVo {
    private Long userId;
    private String userCode;
    private String userName;
    private String token;
    private String refreshToken;
}
