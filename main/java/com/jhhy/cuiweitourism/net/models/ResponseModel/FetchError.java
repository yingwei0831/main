package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by zhangguang on 16/9/30.
 */
public class FetchError {

    public int errroCode;
    public  String msg;
    public  String info;

    public String exceptionName;

    @Override
    public String toString() {
        return "FetchError{" +
                "errroCode=" + errroCode +
                ", msg='" + msg + '\'' +
                ", info='" + info + '\'' +
                ", exceptionName='" + exceptionName + '\'' +
                '}';
    }
}
