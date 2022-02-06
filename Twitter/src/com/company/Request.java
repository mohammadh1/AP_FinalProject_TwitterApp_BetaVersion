package com.company;

public class Request {
    private String command;
    private Object value;

    public Request(String command, Object value) {
        this.command = command;
        this.value = value;
    }

    public String getCommand() {
        return command;
    }

    public Object getValue() {
        return value;
    }
}
