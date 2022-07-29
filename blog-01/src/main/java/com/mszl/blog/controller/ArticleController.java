package com.mszl.blog.controller;

import com.mszl.blog.common.aop.LoginAnnotation;
import com.mszl.blog.common.cache.Cache;
import com.mszl.blog.service.ArticleService;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.ArticleParam;
import com.mszl.blog.vo.params.PageParams;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     *
     * @param pageParams
     */
    @PostMapping
    //加上次注解，代表要对此接口记录日志
    @LoginAnnotation(module = "文章",operation = "获取文章列表")
    public ReturnObject listArticle(@RequestBody PageParams pageParams) {
        return articleService.selectArticle(pageParams);
    }


    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public ReturnObject hotArticle() {
        int limit=5;
        ReturnObject returnObject = articleService.hotArticle(limit);
        return  returnObject;
    }

    @PostMapping("/new")
    public Object newArticle() {
        int limit=1;
        return articleService.selectArticleNew(limit);
    }

    @PostMapping("/listArchives")
    public Object listArchives() {
        int limit=3;
        return articleService.selectlistArchives(limit);
    }

    @PostMapping("/view/{id}")
    public Object selectArticleById(@PathVariable("id") Long id) {

        return articleService.selectArticleById(id);
    }

    //发布文章
    @PostMapping("/publish")
    public Object publish(@RequestBody ArticleParam articleParam) {

        return articleService.publishArticle(articleParam);
    }

    @PostMapping("/{id}")
    public Object articleById(@PathVariable("id") Long id) {

        return articleService.selectArticleById(id);
    }

    @PostMapping("/search")
    public Object search(@RequestBody ArticleParam articleParam) {
        //搜索接口
        String search = articleParam.getSearch();
        return  articleService.getArticleBySearch(search);

    }

}
