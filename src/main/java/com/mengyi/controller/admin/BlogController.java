package com.mengyi.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mengyi.annotation.AuthToken;
import com.mengyi.entity.Blog;
import com.mengyi.entity.Type;
import com.mengyi.entity.User;
import com.mengyi.queryvo.BlogQuery;
import com.mengyi.queryvo.FirstPageBlog;
import com.mengyi.queryvo.SearchBlog;
import com.mengyi.queryvo.ShowBlog;
import com.mengyi.service.BlogService;
import com.mengyi.service.TagService;
import com.mengyi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Controller
@AuthToken
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //博客列表
    @RequestMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        //按照排序字段 倒序 排序
        PageInfo<BlogQuery> adminBlogList = blogService.getAllBlog(pageNum, 5);
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("pageInfo", adminBlogList);
        return "admin/blogs";
    }

    //跳转博客新增页面
    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    //    博客新增
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));

        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));

        //设置标签
//        blog.setTags(tagService.listTag(blog.getTagIds()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        Integer b = blogService.saveBlog(blog);

        if (b == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/blogs";
    }

    //    删除文章
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    //    跳转编辑修改文章
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        ShowBlog blogById = blogService.getBlogById(id);
        List<Type> allType = typeService.getAllType();
        model.addAttribute("blog", blogById);
        model.addAttribute("types", allType);
        return "admin/blogs-input";
    }

    //    编辑修改文章
    @PostMapping("/blogs/{id}")
    public String editPost(@Valid ShowBlog showBlog, RedirectAttributes attributes) {
        Integer b = blogService.updateBlog(showBlog);
        if (b == 0) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/blogs";
    }

    //    搜索博客
    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog, Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        PageInfo<BlogQuery> blogBySearch = blogService.getBlogBySearch(pageNum, 5, searchBlog);

        model.addAttribute("pageInfo", blogBySearch);
        return "admin/blogs :: blogList";
    }
}
