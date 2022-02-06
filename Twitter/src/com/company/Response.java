package com.company;

public class Response {
    private boolean result;
    private Object serverResponse;

    public Response(boolean result, Object serverResponse) {
        this.result = result;
        this.serverResponse = serverResponse;
    }
}
