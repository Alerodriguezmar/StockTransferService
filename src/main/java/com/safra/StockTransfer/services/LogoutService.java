package com.safra.StockTransfer.services;

import org.springframework.http.HttpHeaders;

public interface LogoutService {
    String logout(HttpHeaders cookies);
}
