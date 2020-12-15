package com.example.demo.exception;

import org.apache.log4j.Logger;

public class CustomException extends Exception {
    private static final Logger log = Logger.getLogger(CustomException.class);

    public CustomException() {
        super();
        log.error(this.getClass() + " was thrown  because of some repository couldn't find data!");
    }
}
