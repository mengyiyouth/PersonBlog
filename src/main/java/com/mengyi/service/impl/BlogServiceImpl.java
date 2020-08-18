package com.mengyi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mengyi.NotFoundException;
import com.mengyi.dao.BlogDao;
import com.mengyi.entity.Blog;
import com.mengyi.queryvo.*;
import com.mengyi.service.BlogService;
import com.mengyi.util.MarkdownUtils;
import com.mengyi.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/27
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public ShowBlog getBlogById(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public PageInfo<BlogQuery> getAllBlog(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize, "update_time desc");
        List<BlogQuery> blogList = blogDao.getAllBlogQuery();
        PageInfo<BlogQuery> blogPageInfo = new PageInfo<>(blogList);
        return blogPageInfo;
    }

    @Transactional
    @Override
    public Integer saveBlog(Blog blog) {
        //设置时间信息，然后存如数据库中
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0L);
//        blog.setCommentCount(0);
        return blogDao.saveBlog(blog);
    }

    @Transactional
    @Override
    public Integer updateBlog(ShowBlog showBlog) {
        showBlog.setUpdateTime(new Date());
        return blogDao.updateBlog(showBlog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlog(id);
    }


    @Override
    public PageInfo<BlogQuery> getBlogBySearch(Integer currentPage, Integer pageSize, SearchBlog searchBlog) {
        PageHelper.startPage(currentPage, pageSize);
        List<BlogQuery> allFindBlog = blogDao.searchByTitleOrTypeOrRecommend(searchBlog);
        PageInfo<BlogQuery> blogQueryPageInfo = new PageInfo<>(allFindBlog);
        return blogQueryPageInfo;
    }

    @Override
    public List<BlogQuery> getTimeBlog() {
        return blogDao.getAllBlogQuery();
    }

    @Override
    public PageInfo<FirstPageBlog> getAllFirstPageBlog(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<FirstPageBlog> allFirstPageBlog = blogDao.getFirstPageBlog();
        PageInfo<FirstPageBlog> blogPageInfo = new PageInfo<>(allFirstPageBlog);
        return blogPageInfo;
    }


    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        List<RecommendBlog> allRecommendBlog = blogDao.getAllRecommendBlog();
        return allRecommendBlog;
    }


    @Override
    public PageInfo<FirstPageBlog> getSearchBlog(String query, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<FirstPageBlog> allSearchBlog = blogDao.getSearchBlog(query);
        PageInfo<FirstPageBlog> blogPageInfo = new PageInfo<>(allSearchBlog);
        return blogPageInfo;
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        DetailedBlog detailedBlog = blogDao.getDetailedBlog(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        //markdown转换
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
//        文章访问数量自增
        blogDao.updateViews(id);
////        文章评论数量更新
//        blogDao.getCommentCountById(id);
        return detailedBlog;
    }


    @Override
    public List<FirstPageBlog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public Integer getBlogTotal() {
        return blogDao.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return blogDao.getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return blogDao.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return blogDao.getBlogMessageTotal();
    }
}
