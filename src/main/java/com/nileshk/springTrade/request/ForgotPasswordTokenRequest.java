package com.nileshk.springTrade.request;

import com.nileshk.springTrade.domain.VerificationType;
import lombok.Data;


@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
