package com.mengyi.service;

import com.mengyi.entity.UVStatistic;

/**
 * @author mengyiyouth
 * @date 2020/7/18 11:07
 **/
public interface UVStatisticService {
    /***
     * 获取最近recent天的访问数据
     * @param recent
     * @return
     */
    UVStatistic getStatistic(Integer recent);

    /***
     * 获取历史访问总数
     * @return
     */
    Long getTotal();
}
