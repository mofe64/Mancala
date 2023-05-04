package com.groupthree.mancala.exceptions;

/**
 * This class represents a generic exception that can be thrown within the course of the application
 *
 * @author mofe
 * @version 1.0
 **/
public class ApplicationException extends RuntimeException {
    /**
     * Create an application exception
     *
     * @param message : The exception message
     **/
    public ApplicationException(String message) {
        super(message);
    }
}
