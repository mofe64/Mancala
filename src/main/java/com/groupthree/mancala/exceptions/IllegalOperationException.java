package com.groupthree.mancala.exceptions;

public class IllegalOperationException extends RuntimeException{
    public IllegalOperationException(String message) {
        super(message);
    }
}
