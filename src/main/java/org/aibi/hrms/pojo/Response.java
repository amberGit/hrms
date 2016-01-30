package org.aibi.hrms.pojo;

/**
 * Created by Administrator on 2016/1/28.
 */
public class Response {

    private Status status;

    private String msg;

    public enum Status {
        OK,
        ERROR
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
