package com.safra.StockTransfer.controller;

import com.safra.StockTransfer.config.CertificateSSLConfig;
import com.safra.StockTransfer.services.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController

public class LogoutController {

    @Autowired
    private LogoutService logoutService;


    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        new CertificateSSLConfig().trustSelfSignedSSL();
        String logout = logoutService.logout((HttpHeaders) session.getAttribute("cookies"));
        return "Logout: "+logout;
    }
}
