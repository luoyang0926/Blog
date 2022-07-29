package com.mszl.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnObject {
    private boolean success;

    private Integer code;

    private String msg;

    private Object data;


    public static ReturnObject success(Object data1) {
        return new ReturnObject(true,200,"success",data1);
    }
    public static ReturnObject fail(Integer code, String msg) {
        return new ReturnObject(false,code,msg,null);
    }
}
