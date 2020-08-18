package com.mengyi.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mengyi.annotation.AuthToken;
import com.mengyi.entity.Tag;
import com.mengyi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Controller
@AuthToken
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /*分页查询分类列表*/
    @GetMapping("/tags")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照字段倒序排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,5,orderBy);
        List<Tag> list = tagService.getAllTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        /*为了在前端能取出来*/
        return "admin/tags";
    }

    /*返回新增分类页面*/
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /*新增标签*/
    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        Integer i = tagService.saveTag(tag);
        if (i == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        /*有两个tags这样会跳到第一个GetMapping*/
        return "redirect:/admin/tags";
    }

    /*跳转修改标签页面*/
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    /*编辑标签*/
    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        Integer i = tagService.saveTag(tag);
        if (i == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        /*有两个tags这样会跳到第一个GetMapping*/
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }


}
