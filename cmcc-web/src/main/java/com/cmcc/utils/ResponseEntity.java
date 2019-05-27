package com.cmcc.utils;

import com.cmcc.enums.EnumErrCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Create Time: 2018年11月15日 09:08          </p>
 * <p>C@author lidongbin               </p>
 **/
    public class ResponseEntity<T> implements Serializable {
        private static final String SUCCESS = "00000";
        private static final long serialVersionUID = -750644833749014618L;
        private T data;
        private String retCode;
        private String retInfo;
        private Boolean isSuccess;
        private Long pageCount;
        private Date now;

        public Long getPageCount() {
            return pageCount;
        }

        public void setPageCount(Long pageCount) {
            this.pageCount = pageCount;
        }

        public void setRetCode(String retCode) {
                this.retCode = retCode;
            }

        public static long getSerialVersionUID() {
            return -750644833749014618L;
        }

        public Boolean getIsSuccess() {
            return this.isSuccess;
        }

        public void setIsSuccess(Boolean success) {
            this.isSuccess = success;
        }

        public String getRetCode() {
            return this.retCode;
        }

        public ResponseEntity() {
            this.retCode = "0";
            this.retInfo = "请求成功";
            this.setIsSuccess(true);
        }

        public ResponseEntity(String retCode, String msg) {
            this.retCode = retCode;
            this.retInfo = msg;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Date getNow() {
            return this.now;
        }

        public void setNow(Date now) {
            this.now = now;
        }

        public String getRetInfo() {
            return this.retInfo;
        }

        public void setRetInfo(String msg) {
            this.retInfo = msg;
        }

        public static <T> ResponseEntity<T> ok(T data) {
            ResponseEntity resp = new ResponseEntity();
            resp.setRetCode(SUCCESS);
            resp.setData(data);
            resp.setIsSuccess(true);
            return resp;
        }

    public static <T> ResponseEntity<T> ok(T data,String retCode) {
        ResponseEntity resp = new ResponseEntity();
        resp.setRetCode(retCode);
        resp.setData(data);
        resp.setIsSuccess(true);
        return resp;
    }

    public static <T> ResponseEntity<T> ok(T data,Long pageCount) {
        ResponseEntity resp = new ResponseEntity();
        resp.setRetCode(SUCCESS);
        resp.setData(data);
        resp.setPageCount(pageCount);
        resp.setIsSuccess(true);
        return resp;
    }

    public static <T> ResponseEntity<T> ok(T data,Date now) {
        ResponseEntity resp = new ResponseEntity();
        resp.setRetCode(SUCCESS);
        resp.setData(data);
        resp.setNow(now);
        resp.setIsSuccess(true);
        return resp;
    }

        public static <T> ResponseEntity<T> fail(String retCode, String error) {
            ResponseEntity resp = new ResponseEntity();
            resp.setRetCode(retCode);
            resp.setRetInfo(error);
            resp.setIsSuccess(false);
            return resp;
        }

    //增加构造函数 失败时也添加Data信息
    public static <T> ResponseEntity<T> fail(String retCode, String error, T data) {
        ResponseEntity resp = new ResponseEntity();
        resp.setData(data);
        resp.setRetCode(retCode);
        resp.setRetInfo(error);
        resp.setIsSuccess(false);
        return resp;
    }

    public static <T> ResponseEntity<T> fail(EnumErrCode enumErrCode) {
        ResponseEntity resp = new ResponseEntity();
        resp.setRetCode(enumErrCode.getRetCode());
        resp.setRetInfo(enumErrCode.getRetInfo());
        resp.setIsSuccess(false);
        return resp;
    }
}
