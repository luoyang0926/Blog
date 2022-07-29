package com.mszl.blog.controller;

import com.mszl.blog.service.CommentsService;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    public ReturnObject comments(@PathVariable("id") Long articleId){

        return commentsService.commentsByArticleId(articleId);

    }

    @PostMapping("create/change")
    public ReturnObject comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
