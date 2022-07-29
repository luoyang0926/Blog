package com.mszl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszl.blog.mapper.*;
import com.mszl.blog.pojo.*;
import com.mszl.blog.service.*;
import com.mszl.blog.utils.UserThreadLocal;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.*;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl   implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public ReturnObject selectArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return ReturnObject.success(copyList(articleIPage.getRecords(),true,true));
    }


/*
@Override
    public ReturnObject selectArticle(PageParams pageParams) {
        */
/**
         * 1 分页查勋 article 数据库表
         *//*

        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId() != null) {
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null) {
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper=new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
            List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTagList) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size() > 0) {
                queryWrapper.in(Article::getId, articleIdList);
            }
        }
        //是否置顶排序
        queryWrapper.orderByDesc(Article::getTitle);
        queryWrapper.orderByDesc(Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records,true,true);
        return ReturnObject.success(articleVoList);
    }
*/

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
      List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }

        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor, boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }

        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor, boolean isBody,boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        BeanUtils.copyProperties(article,articleVo);
        //并不是所有的接口，都需要标签，作者信息
        if (isTag) {

            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo=new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setNickname(sysUser.getNickname());
            userVo.setId(sysUser.getId().toString());
            articleVo.setAuthor(userVo);

/*

            Long authorId = article.getAuthorId();
            SysUser user = sysUserService.findUserById(authorId);
            articleVo.setAuthor(user.getNickname());
*/

        }
        if (isBody){
            ArticleBodyVo articleBody = findArticleBody(article.getId());
            articleVo.setBody(articleBody);
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            CategoryVo categoryVo = categoryService.findCategotyById(categoryId);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }




    @Autowired
    private ArticleBodyMapper articleBodyMapper;


    private ArticleBodyVo findArticleBody(Long articleId) {

        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId,articleId);
        //queryWrapper.last("limit 1,1");
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public ReturnObject hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(articleList,false,false));
    }

    @Override
    public Object selectArticleNew(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(articleList, false, false));
    }

    @Override
    public Object selectlistArchives(int limit) {
        List<Archives> archives = articleMapper.listArchives(limit);
        return ReturnObject.success(archives);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public Object selectArticleById(Long id) {
        Article article = this.articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //更新  增加了此次接口的 耗时 如果更新一旦出现异常，不能影响正常阅读
        //线程池，可以把更新操作，扔到线程池中去执行，和主线程就不像管了
        threadService.updateArticleViewCount(articleMapper,article);

        return ReturnObject.success(articleVo);
    }


    @Override
    public Object publishArticle(ArticleParam articleParam) {
        SysUser user = UserThreadLocal.get();
        /**
         * 1.发布文章 目的 构建Article对象
         * 2.作者id 当前的登录用户
         * 3.标签 要将标签加入到 关联标签中
         * 4.body 内容存储
         */
        Article article=new Article();
        boolean isEdit=false;
        if (articleParam.getId() != null) {
            article=new Article();
            article.setId(articleParam.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId( articleParam.getCategory().getId());
            articleMapper.updateById(article);
            isEdit=true;
        }else {
             article = new Article();
            article.setAuthorId(user.getId());
            article.setCategoryId(articleParam.getCategory().getId());
            article.setCreateDate(System.currentTimeMillis());
            article.setCommentCounts(0);
            article.setSummary(articleParam.getSummary());
            article.setTitle(articleParam.getTitle());
            article.setViewCounts(0);
            article.setWeight(Article.Article_Common);
            article.setBodyId(-1L);
            //插入之后，会生成文章id
            articleMapper.insert(article);
        }


        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);

            }

        }
         //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return ReturnObject.success(articleVo);


    }

    @Override
    public Object getArticleBySearch(String search) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.like(Article::getTitle, search);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(articleList, false, false));


    }


}
