package com.mszl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszl.blog.mapper.CategoryMapper;
import com.mszl.blog.pojo.Category;
import com.mszl.blog.service.CategoryService;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategotyById(Long categoryId) {
          Category category = categoryMapper.selectById(categoryId);
          CategoryVo categoryVo=new CategoryVo();
          System.out.println("==================="+category.getCategoryName());

          /*categoryVo.setCategoryName(category.getCategoryName());
          categoryVo.setAvatar(category.getAvatar());
          categoryVo.setId(category.getId());*/
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Object findAll() {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId, Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(categories));
    }

    @Override
    public Object findAllDatail() {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(categories));
    }



    @Override
    public ReturnObject categoriesDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return ReturnObject.success(copy(category));
    }

    private Object copyList(List<Category> categories) {
       List<CategoryVo> categoryVoList=new ArrayList<>();
        for (Category category : categories) {
            categoryVoList.add(copy(category));

        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
