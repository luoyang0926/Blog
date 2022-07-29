package com.mszl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszl.blog.mapper.TagMapper;
import com.mszl.blog.pojo.Tag;
import com.mszl.blog.service.TagService;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tagsByArticleId = tagMapper.findTagsByArticleId(articleId);
        return copyList(tagsByArticleId);
    }

    @Override
    public Object selectHot(int limit) {
        /**
         * 1.标签所拥有的的数量最多 最热标签
         */
        List<Long> hotsTagsIds = tagMapper.findHotsTagsIds(limit);
        if (CollectionUtils.isEmpty(hotsTagsIds)) {
            return ReturnObject.success(Collections.emptyList());
        }
        //需求是tagId和tagName Tag对象
        List<Tag> tagList= tagMapper.findTagsByTagId(hotsTagsIds);
        return ReturnObject.success(tagList);
    }

    @Override
    public Object findAll() {
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(tagList));
    }

    @Override
    public Object findAllDetails() {

        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return ReturnObject.success(copyList(tagList));
    }

    @Override
    public ReturnObject findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return ReturnObject.success(copy(tag));
    }
}
