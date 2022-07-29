package com.mszl.blog.service;

import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategotyById(Long categoryId);

    Object findAll();

    Object findAllDatail();

    ReturnObject categoriesDetailById(Long id);

}
