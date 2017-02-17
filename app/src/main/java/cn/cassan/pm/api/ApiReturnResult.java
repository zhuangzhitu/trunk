package cn.cassan.pm.api;

import android.text.TextUtils;

/**
 * API返回结构体
 * Created by chenmaotou on 2016/9/16.
 */

public class ApiReturnResult {

    private String status; //返回状态
    private String data; //数据
    private String message;//消息内容，如错误信息等

    @Override
    public String toString() {
        return "ApiReturnResult{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isOK() {
        if (!TextUtils.isEmpty(this.status))
            return TextUtils.equals(this.status, "2000000");
        return false;

    }

}
