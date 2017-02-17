package cn.cassan.pm.model;

/**
 * @author Created by zhihao on 2016/10/27.
 * @describe
 * @version_
 **/
public class ErrorString extends BaseEntity {

    private  String message;
    private  String status;

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
}
