package com.example.demo.exception;

import org.apache.log4j.Logger;

public class IllegalIdException extends Exception {
    private static final Logger log = Logger.getLogger(CustomException.class);

    public IllegalIdException() {
        super();
        log.error(this.getClass() + " was thrown  because of someone tried to access someone else's data!");
    }
}