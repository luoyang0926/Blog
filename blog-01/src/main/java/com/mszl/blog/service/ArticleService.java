package com.mszl.blog.service;

import com.mszl.blog.pojo.Article;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.ArticleBodyVo;
import com.mszl.blog.vo.params.ArticleParam;
import com.mszl.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {
    //查询文章
    ReturnObject selectArticle(PageParams pageParams);
    //最热文章
    ReturnObject hotArticle(int limit);

    Object selectArticleNew(int limit);

    Object selectlistArchives(int limit);


    Object selectArticleById(Long id);

    Object publishArticle(ArticleParam articleParam);


    Object getArticleBySearch(String search);

}
