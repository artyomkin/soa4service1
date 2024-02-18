package com.itmo.soa2.exceptions;

import java.util.List;

public class SpaceMarineWrongFieldsException extends Exception{
    public SpaceMarineWrongFieldsException(String msg){
        super("Invalid fields " + msg);
    }
}
