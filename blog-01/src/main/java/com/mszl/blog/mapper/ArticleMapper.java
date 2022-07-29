package com.mszl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.mszl.blog.pojo.Archives;
import com.mszl.blog.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    //文章归类
    List<Archives> listArchives(int limit);

    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);
}
