package com.nileshk.springTrade.model;

import com.nileshk.springTrade.domain.VerificationType;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled=false;
    private VerificationType sendTo;
}
