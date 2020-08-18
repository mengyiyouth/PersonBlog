package com.mengyi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mengyiyouth
 * @date 2020/5/25
 **/
/*这个注解会拦截掉所有，注有Controller注解的控制器*/
@ControllerAdvice
public class ControllerException {
    /*统一异常的处理类*/
//    获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*代表可以拦截Exception类型的异常*/
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        /*将错误信息，打印到日志中*/
        logger.error("Request URL : {}, Exception : {}",request.getRequestURI(), e);
//        当标识了状态码的时候，即此时是自定义的NotFoundException
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            //指定状态
            throw e;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",request.getRequestURI());
        modelAndView.addObject("exception", e);
        /*通过setViewName()方法跳转到指定的页面*/
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
