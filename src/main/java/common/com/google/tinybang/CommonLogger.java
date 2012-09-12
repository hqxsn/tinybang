package com.google.tinybang;

/**
 * Created by TinyBang
 * User: wenzhong
 * Date: 2/26/11
 * Time: 3:34 PM
 */
public class CommonLogger {

    private String name;

    public CommonLogger(Class<?> klass) {
        this.name = klass.getSimpleName();
    }

    public CommonLogger(String name) {
        this.name = name;
    }

    public void error(String message) {

    }


    public void error(String message, Throwable exception) {

    }

    public void info(String message) {

    }

    public void warn(String message) {

    }

}
