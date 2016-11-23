package com.gearcode.gearpress.vo;

/**
 * Created by jason on 16/11/22.
 */
public class AjaxResult {

    public enum Result {
        SUCCESS, FAILURE, ERROR
    }

    private Result result;
    private String message;
    private Object data;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
