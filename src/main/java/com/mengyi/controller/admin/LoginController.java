package com.mengyi.controller.admin;

import com.mengyi.entity.User;
import com.mengyi.service.UserService;
import com.mengyi.util.RedisUtils;
import com.mengyi.util.tokenUtils.Const;
import com.mengyi.util.tokenUtils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.*;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private RedisUtils redisUtils;

    /***
     * 跳转到登录页面
     * @return 登录页面
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /***
     * 登录检验
     * @param username 从login.html中得到的用户名和密码
     * @param password
     * @param session
     * @param redirectAttributes
     * @return 成功跳转到登录页面
     */
//    @PostMapping("/login")
//    public String login(@RequestParam String username,
//                        @RequestParam String password,
//                        HttpSession session,
//                        RedirectAttributes redirectAttributes){
//        User user = userService.checkUser(username, password);
//        if(user != null){
//            //界面中拿不到密码
//            user.setPassword(null);
//            /*将信息存到session中*/
//            session.setAttribute("user",user);
//
//            session.getAttribute("user");
//            int t = 0;
//            user.setPassword(null);
//            return "admin/index";
//        }else{
//            /*给出提示信息*/
//            redirectAttributes.addFlashAttribute("message","用户名和密码错误");
//            return "redirect:/admin";
//        }
//    }

    /***
     * 进行免登录操作
     * @param response
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/index")
    public String autoLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session){
        //        首先判断cookie中的token信息
        String findToken = "";

        Cookie[] cookies = request.getCookies();
        if(!(cookies == null)){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    findToken = cookie.getValue();
                    break;
                }
            }
        }
        User findUser = findUserByToken(request, findToken);
//        如果通过cookie中的token找到了用户信息
        if(findUser != null){
//            可以免登录
            session.setAttribute("user",findUser);
            return "admin/index";
        }else{
            return "redirect:/admin";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password, RedirectAttributes redirectAttributes,
                        HttpServletResponse response, HttpServletRequest request,
                        HttpSession session) {


//        第一次登录会存储token到cookie中
        String token = "";
        User user = null;
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user = userService.checkUser(username, password);
        if (user != null) {
            token = tokenGenerator.generate(username, password);
//            绑定token与username
            redisUtils.set(username, token);
//            设置过期时间
            redisUtils.expire(username, Const.TOKEN_EXPIRE_TIME);
//                绑定token与user
            redisUtils.set(token, user);
            redisUtils.expire(token, Const.TOKEN_EXPIRE_TIME);
            Long currentTime = System.currentTimeMillis();
//                绑定创建时间
            redisUtils.set(token + username, currentTime.toString());

            //            将token存放到cookie中
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
//            一天过期
            cookie.setMaxAge(60 * 60 * 24);
            user.setPassword(null);
            response.addCookie(cookie);

//            加入session中
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            /*给出提示信息*/
            redirectAttributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }

    /***
     * 注销
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        try {
            String token = request.getHeader("token");
            if (!StringUtils.isEmpty(token)) {
                User user = (User) redisUtils.get(token);
                if (user != null) {
                    redisUtils.del(token, user.getUsername(), token + user.getUsername());
                    return "redirect:/admin";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    public User findUserByToken(HttpServletRequest request, String token){
        if(StringUtils.isEmpty(token)){
//            token为空
            return null;
        }
        User user = (User) redisUtils.get(token);
        if(user != null){
            return user;
        }
//        通过token没有找到用户信息
        return null;
    }

//    /***
//     * 注销登录
//     * @param httpSession
//     * @return 返回登录页面
//     */
//    @GetMapping("/logout")
//    public String logout(HttpSession httpSession){
//        httpSession.removeAttribute("user");
//        return "redirect:/admin";
//    }
}
