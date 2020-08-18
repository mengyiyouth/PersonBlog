package com.mengyi.controller;

import com.mengyi.entity.UVStatistic;
import com.mengyi.service.UVStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;

/**
 * @author mengyiyouth
 * @date 2020/5/28
 **/
@Controller
public class AboutShowController {
    @Autowired
    private UVStatisticService uvStatisticService;

    @GetMapping("/about")
    public String about(Model model) {
        UVStatistic uvStatistic = uvStatisticService.getStatistic(7);
//        LinkedHashMap<String, Long> statisticMap = uvStatistic.getStatisticMap();
////        传递Map
//        model.addAttribute("statisticMap", statisticMap);
//        传递总访问量
        model.addAttribute("uvStatistic",uvStatistic);
        return "about";
    }

}
