package com.safra.StockTransfer.services.impl;

import com.safra.StockTransfer.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginServiceImpl implements LoginService {

    public static String COOKIES = "";
    private HttpHeaders headers;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Override
    public HttpHeaders login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(env.getProperty("login.sap"), headers);
        HttpEntity<String> response = restTemplate.exchange(env.getProperty("url.sap") + "Login", HttpMethod.POST,
                entity, String.class);
        String cookies = "";
        for (String str : response.getHeaders().get(HttpHeaders.SET_COOKIE)) {
            cookies = cookies + str;
        }
        HttpHeaders header = new HttpHeaders();
        header.set("Cookie", cookies);
        this.headers = header;
        return header;
    }

    @Override
    public HttpHeaders getAuthorizationHeader() {
        return this.headers;
    }

}
