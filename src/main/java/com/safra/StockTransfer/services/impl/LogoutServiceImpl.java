package com.safra.StockTransfer.services.impl;

import com.safra.StockTransfer.services.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL = "https://192.168.100.193:50000/b1s/v1/Logout";

    @Override
    public String logout(HttpHeaders cookies) {
        HttpEntity<Void> entity = new HttpEntity<>(cookies);
        HttpEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST,entity,String.class);
        return response.getHeaders().toString();
    }
}
