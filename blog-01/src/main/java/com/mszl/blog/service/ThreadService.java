package com.mszl.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszl.blog.mapper.ArticleMapper;
import com.mszl.blog.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    //期望此操作在线程池 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate=new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        // 设置一个 为了在多线程的环境下 线程安全
        queryWrapper.eq(Article::getViewCounts, viewCounts);
        articleMapper.update(articleUpdate, queryWrapper);

        try {
            Thread.sleep(5000);
            System.out.println("更新完成....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
