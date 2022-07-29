package com.mszl.blog.controller;

import com.mszl.blog.utils.QiniuUtils;
import com.mszl.blog.vo.ReturnObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Object upload(@RequestParam("image")MultipartFile file) {
        //原始文件名称
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
       //上传文件
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return ReturnObject.success(QiniuUtils.url + fileName);
        }

        return  ReturnObject.fail(20001,"上传失败");
    }
}
