package com.example.chatbot.service;

import com.example.chatbot.Mapper.RegistrationMapper;
import com.example.chatbot.dto.LoginDTO;
import com.example.chatbot.dto.RegisterationDTO;
import com.example.chatbot.entity.User;
import com.example.chatbot.repository.UserRepo;
import com.example.chatbot.util.EmailUtil;
import com.example.chatbot.util.OTP;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    EmailUtil email;
    @Autowired
    RedisService redis;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public void register(RegisterationDTO newuser) {
        User ExistingUser = userRepo.findByEmail(newuser.getEmail());
        if (ExistingUser != null)
            throw new RuntimeException("User already Exist");
        User user = new RegistrationMapper().toEntity(newuser);
        user.setPassword(encoder.encode(user.getPassword()));
        String otp = OTP.generate();
        redis.save("otp:" + user.getEmail(), otp);
        redis.save("user:" + user.getEmail(), user);
        email.sendMail(newuser.getEmail(), "OTP to register", otp);
    }

    @Transactional
    public String verifyotp(String otp, String email) {
        String otpinmemory = (String) redis.get("otp:" + email);
        if (otp == null || !otp.equals(otpinmemory)) {
            throw new RuntimeException("Invalid Otp");
        }
        User user = (User) redis.get("user:" + email);
        if (user == null)
            throw new RuntimeException("User data not found");
        userRepo.save(user);
        redis.delete("otp:" + email);
        redis.delete("user:" + email);
        return jwtService.genrateToken(user.getEmail());
    }

    public String verify(LoginDTO user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.genrateToken(user.getEmail());
        } else {
            throw new RuntimeException("User not Found");
        }
    }
}
