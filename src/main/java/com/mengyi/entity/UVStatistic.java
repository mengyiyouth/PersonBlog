package com.mengyi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

/**
 * @author mengyiyouth
 * @date 2020/7/18 10:12
 * UV独立访客统计
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UVStatistic {
//  按照日期和数量进行统计
    private LinkedHashMap<String, Long> statisticMap;
//    历史访问总数
    private Long total;

    @Override
    public String toString() {
        return "UVStatistic{" +
                "statisticMap=" + statisticMap +
                ", total=" + total +
                '}';
    }
}
