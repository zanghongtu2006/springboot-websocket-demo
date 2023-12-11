package com.hongtu.project.im.commom;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:58 19-4-24
 */
@Data
public class BaseResponse<T> implements Serializable {
    private Integer code;

    private String msg;

    private T data;

    public BaseResponse(Builder<T> builder) {
        if (this.code == null) {
            this.code = BaseResponseCode.SUCCESS.getCode();
        } else {
            this.code = builder.code;
        }
        if (this.msg == null) {
            this.msg = BaseResponseCode.SUCCESS.getMessage();
        } else {
            this.msg = builder.msg;
        }
        this.data = builder.data;
    }

    public static class Builder<T> {
        private Integer code;

        private String msg;

        private T data;

        public Builder<T> setCode(Integer code) {
            this.code = code;
            return this;
        }

        public Builder<T> setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public BaseResponse<T> build() {
            return new BaseResponse<T>(this);
        }
    }

}
