package com.mengyi.dao;

import com.mengyi.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Mapper
@Repository
public interface TagDao {

    Integer saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> getAllTag();

    List<Tag>getAllTagAndBlog();

    List<Tag> listTag(List<Long> list);

    Tag getTagByName(String name);

    Integer updateTag(Tag tag);

    void deleteTag(Long id);



}
