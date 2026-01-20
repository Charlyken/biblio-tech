package com.charlyken.bibliotech.exception;

public class LoanNotFoundException extends BusinessException{
    public LoanNotFoundException (String message){
        super(message);
    }
}
