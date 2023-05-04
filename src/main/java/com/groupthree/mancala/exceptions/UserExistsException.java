package com.groupthree.mancala.exceptions;


/**
 * This class represents an exception that is thrown whenever a user try to create an account with an
 * already exiting username
 *
 * @author mofe
 * @version 1.0
 **/
public class UserExistsException extends RuntimeException {
    /**
     * Create a user exists exception
     *
     * @param message : The exception message
     **/
    public UserExistsException(String message) {
        super(message);
    }
}
