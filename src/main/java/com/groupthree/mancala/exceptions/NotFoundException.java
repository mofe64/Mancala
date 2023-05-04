package com.groupthree.mancala.exceptions;


/**
 * This class represents a generic exception that can be thrown whenever a user is not found
 * in the application
 *
 * @author mofe
 * @version 1.0
 **/
public class NotFoundException extends RuntimeException {
    /**
     * Create a not found exception
     *
     * @param message : The exception message
     **/
    public NotFoundException(String message) {
        super(message);
    }
}
