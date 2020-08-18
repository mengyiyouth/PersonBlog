package com.mengyi.service.impl;

import com.mengyi.entity.UVStatistic;
import com.mengyi.service.UVStatisticService;
import com.mengyi.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author mengyiyouth
 * @date 2020/7/18 11:08
 **/
@Service
public class UVStatisticServicImpl implements UVStatisticService {
    @Autowired
    private RedisUtils redisUtils;

    private static final String PREFIX = "uniqueViews::";

    private Calendar calendar = Calendar.getInstance();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /***
     * 统计recent天的访问记录
     * @param recent
     * @return
     */
    @Override
    public UVStatistic getStatistic(Integer recent) {
        calendar.setTime(new Date());
        LinkedHashMap<String, Long> statisticMap = new LinkedHashMap<>(recent);
        for(int i = recent - 1; i >= 0; i--){
            String date = null;
            if(i == recent - 1){
//                先得到最后前七天
                date = getPastDate(i);
            }else{
//                加1一次得到六五四三二一天
                date = getPreDate(1);
            }
//          根据日期得到redis中的Key
            String key = PREFIX + date;
            if(redisUtils.hasKey(key)){
                statisticMap.put(date, redisUtils.pfcount(key));
            }else{
//                不存在k，说明访问量为0;
                statisticMap.put(date, 0L);
            }
        }
        UVStatistic uvStatistic = new UVStatistic();
        logger.info("访问量统计时间段: {}", statisticMap);
        uvStatistic.setStatisticMap(statisticMap);
        uvStatistic.setTotal(getTotal());
        return uvStatistic;
    }

    @Override
    public Long getTotal() {
        String[] keys = redisUtils.keys(PREFIX + "*");
        Long count = 0L;
        for (String k : keys) {
            count += redisUtils.pfcount(k);
        }
        return count;
    }


//    得到之前的日期
    public String getPastDate(Integer past) {
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }

    public String getPreDate(Integer pre) {
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + pre);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }
}
