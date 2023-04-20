package com.safra.StockTransfer.exceptions;

/**
 *
 * @author Brayav
 */
public class SQLConnectionException extends RuntimeException {

    public SQLConnectionException(String message) {
        super(message);
    }

}
