package com.mszl.blog.controller;

import com.mszl.blog.service.TagService;
import com.mszl.blog.vo.ReturnObject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Object hot() {
        int limit = 6;
        return tagService.selectHot(limit);
    }


    @GetMapping
    public Object allTags() {
        return tagService.findAll();
    }

    @GetMapping("/detail")
    public Object allTagsDetails() {
        return tagService.findAllDetails();
    }

    @GetMapping("detail/{id}")
    public ReturnObject findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }


}
