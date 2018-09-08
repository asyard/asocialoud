package org.yardimci.web.asocialoud.web.controller.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {

    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
