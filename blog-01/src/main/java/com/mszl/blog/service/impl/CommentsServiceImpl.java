package com.mszl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mszl.blog.mapper.ArticleMapper;
import com.mszl.blog.mapper.CommentMapper;
import com.mszl.blog.pojo.Article;
import com.mszl.blog.pojo.Comment;
import com.mszl.blog.pojo.SysUser;
import com.mszl.blog.service.CommentsService;
import com.mszl.blog.service.SysUserService;
import com.mszl.blog.utils.UserThreadLocal;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.CommentParam;
import com.mszl.blog.vo.params.CommentVo;
import com.mszl.blog.vo.params.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public ReturnObject commentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);

        return ReturnObject.success(commentVoList);
    }

    @Override
    public ReturnObject comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();

        comment.setArticleId(commentParam.getArticleId());

        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);

        UpdateWrapper<Article> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", comment.getArticleId());
        updateWrapper.setSql(true, "comment_counts=comment_counts+1");
        this.articleMapper.update(null, updateWrapper);

        CommentVo commentVo = copy(comment);
        return ReturnObject.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList=new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
       //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (level == 1) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentVoByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);

        }
    return commentVo;

    }

    private List<CommentVo> findCommentVoByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        return copyList(commentMapper.selectList(queryWrapper));
    }
}
