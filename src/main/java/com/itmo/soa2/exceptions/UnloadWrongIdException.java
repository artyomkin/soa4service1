package com.itmo.soa2.exceptions;

public class UnloadWrongIdException extends Exception {
    public UnloadWrongIdException(String msg) {
        super("Invalid fields " + msg);
    }
}
