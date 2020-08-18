package com.mengyi.controller;

import com.mengyi.queryvo.BlogQuery;
import com.mengyi.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/28
 **/
@Controller
public class ArchiveShowController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archive(Model model){
        List<BlogQuery> blogs = blogService.getTimeBlog();
        model.addAttribute("blogs", blogs);
        return "archives";
    }

}
