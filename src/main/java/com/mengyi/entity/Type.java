package com.mengyi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type{
    private Long id;
    private String name;

    /*一个分类多个博客*/
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
