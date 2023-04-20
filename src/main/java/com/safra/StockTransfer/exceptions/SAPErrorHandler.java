package com.safra.StockTransfer.exceptions;

import com.safra.StockTransfer.services.LoginService;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * This class acts as an error handler whenever a request is done to ERP SAP service layer, its main function is to
 * catch 401 error. Whenever this error occurs it will use login service to refresh the value of cookies header present
 * in login service class so that this header is available for all the rest of classes services that may need it.
 *
 */
public class SAPErrorHandler extends DefaultResponseErrorHandler {

    private LoginService loginService;
    private Logger log = LoggerFactory.getLogger(SAPErrorHandler.class);

    @Autowired
    public SAPErrorHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error(response.getStatusCode().toString());
        if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            loginService.login();
        }
    }

}
