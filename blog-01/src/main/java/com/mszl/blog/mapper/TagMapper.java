package com.mszl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszl.blog.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long>  findHotsTagsIds(int limit);

    List<Tag> findTagsByTagId(List<Long> hotsTagsIds);
}
