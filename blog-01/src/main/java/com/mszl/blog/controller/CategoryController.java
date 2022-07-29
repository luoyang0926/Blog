package com.mszl.blog.controller;

import com.mszl.blog.service.CategoryService;
import com.mszl.blog.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Object categories() {
        return categoryService.findAll();
    }


    @GetMapping("/detail")
    public Object categoriesDetails() {
        return categoryService.findAllDatail();
    }

    @GetMapping("/detail/{id}")
    public ReturnObject categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }

}
