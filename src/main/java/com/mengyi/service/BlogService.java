package com.mengyi.service;

import com.github.pagehelper.PageInfo;
import com.mengyi.annotation.ClearRedisCache;
import com.mengyi.entity.Blog;
import com.mengyi.queryvo.*;
import com.mengyi.service.impl.TypeServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/27
 **/
public interface BlogService {

    @Cacheable(cacheNames = "cache")
    ShowBlog getBlogById(Long id);

    @Cacheable(cacheNames = "cache")
    PageInfo<BlogQuery> getAllBlog(Integer currentPage, Integer pageSize);

//    得到时间轴的所有博客信息
    @Cacheable(cacheNames = "cache")
    List<BlogQuery> getTimeBlog();

//    新增博客的时候，级联删除分类中的缓存
    @ClearRedisCache(cascade = {TypeServiceImpl.class})
    Integer saveBlog(Blog blog);

//    更新时候，级联删除分类中的缓存
    @ClearRedisCache(cascade = {TypeServiceImpl.class})
    Integer updateBlog(ShowBlog showBlog);

//    删除时候，级联删除分类中的缓存
    @ClearRedisCache(cascade = {TypeServiceImpl.class})
    void deleteBlog(Long id);

    @Cacheable(cacheNames = "cache")
    PageInfo<BlogQuery> getBlogBySearch(Integer currentPage, Integer pageSize, SearchBlog searchBlog);

    @Cacheable(cacheNames = "cache")
    PageInfo<FirstPageBlog> getAllFirstPageBlog(Integer currentPage, Integer pageSize);

    @Cacheable(cacheNames = "cache")
    List<RecommendBlog> getRecommendedBlog();

    @Cacheable(cacheNames = "cache")
    PageInfo<FirstPageBlog> getSearchBlog(String query, Integer currentPage, Integer pageSize);

    @Cacheable(cacheNames = "cache")
    DetailedBlog getDetailedBlog(Long id);

    //根据TypeId获取博客列表，在分类页进行的操作
    @Cacheable(cacheNames = "cache")
    List<FirstPageBlog> getByTypeId(Long typeId);

    Integer getBlogTotal();

    Integer getBlogViewTotal();

    Integer getBlogCommentTotal();

    Integer getBlogMessageTotal();

}
