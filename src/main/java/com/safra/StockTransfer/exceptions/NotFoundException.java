package com.safra.StockTransfer.exceptions;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = -3067266362873411250L;

    public NotFoundException(String message) {
        super(message);
    }
}
