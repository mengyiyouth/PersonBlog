package com.mengyi.service;

import com.mengyi.entity.Tag;

import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
public interface TagService {
    Integer saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> getAllTag();

    List<Tag>getAllTagAndBlog();

    List<Tag> listTag(String ids);

    Tag getTagByName(String name);

    Integer updateTag(Tag tag);

    void deleteTag(Long id);
}
