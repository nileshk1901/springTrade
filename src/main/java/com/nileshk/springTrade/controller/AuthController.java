package com.nileshk.springTrade.controller;


import com.nileshk.springTrade.config.JwtProvider;
import com.nileshk.springTrade.model.TwoFactorOtp;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.repository.UserRepository;
import com.nileshk.springTrade.response.AuthResponse;
import com.nileshk.springTrade.service.CustomUserDetailsService;
import com.nileshk.springTrade.service.EmailService;
import com.nileshk.springTrade.service.TwoFactorOtpService;
import com.nileshk.springTrade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist=userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("email already exits with other account");

        }
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        User savedUser=userRepository.save(newUser);
        Authentication auth=new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()

        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt= JwtProvider.generateToken(auth);
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("registration successful");
         return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user ) throws Exception {

        String userName=user.getEmail();
        String password=user.getPassword();
        Authentication auth=authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(auth);
        User authUser=userRepository.findByEmail(userName);
        String jwt= JwtProvider.generateToken(auth);
        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse res=new  AuthResponse();
            res.setMessage("Two factor authentication is enabled");
            res.setTwoFactorAuthEnabled(true);
            String otp= OtpUtils.generateOtp();
            TwoFactorOtp oldTwoFactorOtp=twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOtp!=null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }
            TwoFactorOtp newTwoFactorOtp =twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

            emailService.sendVerificationOtpEmail(userName,otp);

            res.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successful");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    private Authentication authenticate(String userName,String password){
        UserDetails userDetails= customUserDetailsService.loadUserByUsername(userName);
        if(userDetails==null){
            throw new BadCredentialsException("invalid credentials");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }


    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp,
                                                        @RequestParam String id) throws Exception {
        TwoFactorOtp twoFactorOtp=twoFactorOtpService.findById(id);
        if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp,otp)){
            AuthResponse res=new AuthResponse();
            res.setMessage("Two factor authentication verified");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOtp.getJwt());
            return new ResponseEntity<>(res,HttpStatus.OK);

        }
        throw new Exception("Invalid OTP");


    }
}
