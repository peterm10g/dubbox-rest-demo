package top.crowley.dubbox.rest.demo.api.pojo;

import top.crowley.dubbox.rest.demo.api.util.Constants;

import com.alibaba.fastjson.JSONObject;

public class RespMessage {

    private int    statusCode;

    private String message;

    public RespMessage() {
    }

    public RespMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public void setAll(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean ifSuccess() {
        return statusCode == Constants.SUCCESS_CODE;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("statusCode", statusCode);
        json.put("message", message);

        return json.toJSONString();
    }

}
