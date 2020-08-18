package com.mengyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mengyi.NotFoundException;
import com.mengyi.entity.Blog;
import com.mengyi.queryvo.DetailedBlog;
import com.mengyi.queryvo.FirstPageBlog;
import com.mengyi.queryvo.RecommendBlog;
import com.mengyi.service.BlogService;
import com.mengyi.service.TypeService;
import com.mengyi.util.IpAddressUtils;
import com.mengyi.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/25
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private RedisUtils redisUtils;


    //    分页查询博客列表
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageInfo<FirstPageBlog> homeBlogList = blogService.getAllFirstPageBlog(pageNum, 3);

        homeBlogList.getList().forEach(blog -> {
            String key = "views::blogId-" + blog.getId();
            blog.setViews(redisUtils.pfcount(key));
        });

        List<RecommendBlog> recommendedBlog = blogService.getRecommendedBlog();
        model.addAttribute("pageInfo",homeBlogList);
        /*拿到所有的分类*/
        model.addAttribute("types", typeService.getAllTypeAndBlog());
        model.addAttribute("recommendedBlogs", recommendedBlog);

        return "index";
    }

    //    搜索博客
    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam(value = "query", defaultValue = "") String query) {
        PageInfo<FirstPageBlog> pageInfo = blogService.getSearchBlog(query, pageNum, 3);
//        拿到redis中的访问量
        pageInfo.getList().forEach(blog -> {
            String key = "views::blogId-" + blog.getId();
            blog.setViews(redisUtils.pfcount(key));
        });

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }


    //    跳转博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model, HttpServletRequest request) {
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);

//        从redis中获取这个博客的访问量
        Long views = pfAddAndCount(detailedBlog, request);
        detailedBlog.setViews(views);

        model.addAttribute("blog", detailedBlog);
        return "blog";
    }

//    从redis中获取访问量
    public Long pfAddAndCount(DetailedBlog detailedBlog, HttpServletRequest request){
        String key = "views::blogId-" + detailedBlog.getId();
//        对访问加上时间戳
        String value = IpAddressUtils.getIpAddress(request) + System.currentTimeMillis();
        redisUtils.pfadd(key, value);
        return redisUtils.pfcount(key);
    }

//    最新博客列表
//    @GetMapping("/footer/newblog")
//    public String newblogs(Model model) {
//        List<FirstPageBlog> newBlog = blogService.getNewBlog();
//        model.addAttribute("newblogs", newBlog);
//        return "index :: newblogList";
//    }

    //    博客信息
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();
        int blogCommentTotal = blogService.getBlogCommentTotal();
        int blogMessageTotal = blogService.getBlogMessageTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "index :: blogMessage";
    }
}
