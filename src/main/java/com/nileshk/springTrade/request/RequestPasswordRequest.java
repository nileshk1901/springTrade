package com.nileshk.springTrade.request;

import lombok.Data;

@Data
public class RequestPasswordRequest {
    private String otp;
    private String password;
}
