package com.mszl.blog.service;

import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.CommentParam;

public interface CommentsService {

    ReturnObject commentsByArticleId(Long articleId);

    ReturnObject comment(CommentParam commentParam);

}
