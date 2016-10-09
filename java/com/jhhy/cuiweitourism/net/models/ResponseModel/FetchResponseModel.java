package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney1 on 16/9/29.
 */


public class FetchResponseModel {
    public enum JSON_TYPE{
        /**JSONObject*/
        JSON_TYPE_OBJECT,
        /**JSONArray*/
        JSON_TYPE_ARRAY,
        /**不是JSON格式的字符串*/
        JSON_TYPE_ERROR
    }

    public class HeadModel{
        public String res_code;
        public String res_msg;
        public String res_arg;

        public HeadModel(String res_code, String res_msg, String res_arg) {
            this.res_code = res_code;
            this.res_msg = res_msg;
            this.res_arg = res_arg;
        }

        public HeadModel() {
        }


        @Override
        public String toString() {
            return "HeadModel{" +
                    "res_code='" + res_code + '\'' +
                    ", res_msg='" + res_msg + '\'' +
                    ", res_arg='" + res_arg + '\'' +
                    '}';
        }
    }

    public FetchResponseModel() {
    }

    public FetchResponseModel(HeadModel head, String body) {

        this.head = head;

        this.body = body;
    }

    public HeadModel getHead() {
        return head;
    }

    public void setHead(HeadModel head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HeadModel head;

    public String body;

    @Override
    public String toString() {
        return "FetchResponseModel{" +
                "head=" + head.toString() +
                ", body=" + body +
                '}';
    }


    public JSON_TYPE getBodyJsonType(){
        char fistChar = this.body.charAt(0);
        if ('{' == fistChar){
            return JSON_TYPE.JSON_TYPE_OBJECT;
        }
        else if ('[' == fistChar){
            return JSON_TYPE.JSON_TYPE_ARRAY;
        }
        else{
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
    }
}
