package com.nileshk.springTrade.controller;


import com.nileshk.springTrade.request.ForgotPasswordTokenRequest;
import com.nileshk.springTrade.domain.VerificationType;
import com.nileshk.springTrade.model.ForgotPasswordToken;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.model.VerificationCode;
import com.nileshk.springTrade.request.RequestPasswordRequest;
import com.nileshk.springTrade.response.ApiResponse;
import com.nileshk.springTrade.response.AuthResponse;
import com.nileshk.springTrade.service.EmailService;
import com.nileshk.springTrade.service.ForgotPasswordService;
import com.nileshk.springTrade.service.UserService;
import com.nileshk.springTrade.service.VerificationCodeService;
import com.nileshk.springTrade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfileByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable VerificationType verificationType) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
        if(verificationCode==null){
            verificationCode=verificationCodeService.sendVerificationCode(user,verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        return new ResponseEntity<>("Verification code sent Successfully",HttpStatus.OK);


    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
                                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();
        boolean isVerified=verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo,user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }
        throw new Exception("invalid otp");

    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws Exception{

        User user=userService.findUserByEmail(forgotPasswordTokenRequest.getSendTo());
        String otp= OtpUtils.generateOtp();
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();

        ForgotPasswordToken token=forgotPasswordService.findByUser(user.getId());
        if(token==null){
            token=forgotPasswordService.createToken(user,id,otp,
                    forgotPasswordTokenRequest.getVerificationType(),
                    forgotPasswordTokenRequest.getSendTo());
        }
        if(forgotPasswordTokenRequest.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(),token.getOtp());
        }
        AuthResponse res=new AuthResponse();
        res.setSession(token.getId());
        res.setMessage("Password reset otp sent successfully");
        return new ResponseEntity<>(res,HttpStatus.OK);


    }
    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
                                                      @RequestBody RequestPasswordRequest requestPasswordRequest,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{
        ForgotPasswordToken forgotPasswordToken=forgotPasswordService.findById(id);
        boolean isVerified=forgotPasswordToken.getOtp().equals(requestPasswordRequest.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(),requestPasswordRequest.getPassword());
            ApiResponse res=new ApiResponse();
            res.setMessage("Password update successfully");
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

        }
        throw new Exception("Wrong OTP");
    }



}
