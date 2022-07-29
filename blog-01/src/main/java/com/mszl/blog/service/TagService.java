package com.mszl.blog.service;

import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Object selectHot(int limit);

    Object findAll();

    Object findAllDetails();

    ReturnObject findDetailById(Long id);

}
