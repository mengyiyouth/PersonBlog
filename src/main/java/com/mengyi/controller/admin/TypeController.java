package com.mengyi.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mengyi.annotation.AuthToken;
import com.mengyi.entity.Type;
import com.mengyi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Controller
@AuthToken
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /*分页查询分类列表*/
    @GetMapping("/types")
    public String list(Model model, @RequestParam(defaultValue="1", value="pageNum") Integer pageNum){
        //按照排序字段倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,5,orderBy);
        List<Type> list = typeService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    /*返回新增分类页面*/
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /*新增分类*/
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes redirectAttributes){
        /*页面封装了name直接保存即可*/
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            redirectAttributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        Integer i = typeService.saveType(type);
        if(i == 0){
            redirectAttributes.addFlashAttribute("message", "新增失败");
        }else{
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    /*跳转修改分类页面*/
    /*在前端types.html点击编辑会跳转到这里，同时将参数传送过来*/
    /*前端的id又是通过getAllType传到后端的*/
    @GetMapping("/types/{id}/input")
    public String ediInput(@PathVariable Long id, Model model){
        //将type存入，在tpes-input中读取
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }


    /*编辑修改分类*/
    @PostMapping("/types/{id}")
    public String editPost(Type type, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        int ii = 0;
        if(type1 != null){
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        Integer i = typeService.updateType(type);
        if(i == 0){
            attributes.addFlashAttribute("message", "更新失败");
        }else{
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /*删除分类*/
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }



}
