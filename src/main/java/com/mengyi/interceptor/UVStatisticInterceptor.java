package com.mengyi.interceptor;

import com.mengyi.util.IpAddressUtils;
import com.mengyi.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mengyiyouth
 * @date 2020/7/16 12:52
 * 每日访客统计拦截器
 **/
public class UVStatisticInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtils redisUtil;

    private static final String PREFIX = "uniqueViews::";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = IpAddressUtils.getIpAddress(request);
        String referer = request.getHeader("Referer");
//        请求地址
        String requestURI = request.getRequestURL().toString();
        String date = getDate();
        String declareDate = getDeclareDate();
        String key = PREFIX + date;
//        用Hyperloglog对于重复的ip不会只算一次
        redisUtil.pfadd(key, ipAddress);
        logger.info("{}：访客ip：{}，Referer：{}, Request: {}", declareDate, ipAddress, referer, requestURI);
        return true;
    }

    /***
     * 得到当前日期
     * @return
     */
    private String getDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        return formatDate;
    }

    /***
     * 计算当前准确日期精确到秒
     * @return
     */
    private String getDeclareDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdf.format(date);
        return formatDate;
    }
}
