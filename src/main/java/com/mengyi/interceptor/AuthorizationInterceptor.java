package com.mengyi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mengyi.annotation.AuthToken;
import com.mengyi.entity.User;
import com.mengyi.util.RedisUtils;
import com.mengyi.util.tokenUtils.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author mengyiyouth
 * @date 2020/7/18 11:34
 **/
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //        获取被拦截的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //        检查是否打上了AuthToken的注解
        if (method.isAnnotationPresent(AuthToken.class) || handlerMethod.getBeanType().isAnnotationPresent(AuthToken.class)) {
//            从Cookie中取出Token
            String token = "";
            Cookie[] cookies = request.getCookies();
            if(!(cookies == null)){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("token")){
                        token = cookie.getValue();
                        break;
                    }
                }
            }
//            如果token为空,返回登录页面
            if(token.equals("")){
                response.sendRedirect("/admin");
                return false;
            }
            logger.info("Token in the request header: {}", token);
            String username = "";
//            token中有数据
            if (!StringUtils.isEmpty(token)) {
                User user = (User) redisUtils.get(token);
                if(user != null){
                    username = user.getUsername();
                    logger.info("Username in redis with token is {}", username);
                }
            }
            if (!StringUtils.isEmpty(username)) {
                Long createTime = Long.valueOf(String.valueOf(redisUtils.get(token + username)));
                logger.info("CreateTime of this token is {}", createTime);
                Long liveTime = System.currentTimeMillis() - createTime;
                logger.info("The token has been lived {} seconds", liveTime);
//                存活时间大于限定的时间则重置过期时间
                if (liveTime > Const.TOKEN_RESET_TIME) {
                    redisUtils.expire(username, Const.TOKEN_EXPIRE_TIME);
                    redisUtils.expire(token, Const.TOKEN_EXPIRE_TIME);
                    logger.info("Successful to update expire time!");
                    redisUtils.set(token + username, System.currentTimeMillis());
                }
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
