package com.itmo.soa2.exceptions;

public class StarshipWrongFieldsException extends Exception{

    public StarshipWrongFieldsException(String msg){
        super("Invalid fields " + msg);
    }
}
