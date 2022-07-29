package com.mszl.blog.pojo;

import lombok.Data;

//文章归档封装实体
@Data
public class Archives {
    private Integer year;

    private Integer month;

    private Integer count;
}
