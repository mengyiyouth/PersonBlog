package com.mengyi.service.impl;

import com.mengyi.dao.TagDao;
import com.mengyi.entity.Tag;
import com.mengyi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;
    @Override
    public Integer saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTag(id);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public List<Tag> getAllTagAndBlog() {
        return tagDao.getAllTagAndBlog();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagDao.listTag(converToList(ids));
    }

    /*将字符串转换为数组*/
    private List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for(int i = 0; i < idarray.length; i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public Integer updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagDao.deleteTag(id);
    }
}
