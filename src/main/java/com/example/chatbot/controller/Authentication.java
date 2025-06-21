package com.example.chatbot.controller;

import com.example.chatbot.dto.LoginDTO;
import com.example.chatbot.dto.RegisterationDTO;
import com.example.chatbot.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Authentication {
    @Autowired
    AuthenticationService authService;

    @PostMapping("/Register")
    public ResponseEntity<?> register(@RequestBody RegisterationDTO newuser) {
        authService.register(newuser);
        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/otpverfication")
    public ResponseEntity<String> verification(@RequestParam String otp, @RequestParam String email, HttpServletResponse response) {
        String jwt = authService.verifyotp(otp, email);
        Cookie cookie = new Cookie("JwtToken", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }

    @PostMapping("/Login")
    public ResponseEntity<String> login(@RequestBody LoginDTO user, HttpServletResponse response) {
        String jwt = authService.verify(user);
        Cookie cookie = new Cookie("JwtToken", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }

    @PostMapping("/Logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JwtToken", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResponseEntity<>("Logout Successfull", HttpStatus.OK);
    }

}
