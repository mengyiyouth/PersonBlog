package com.mengyi.queryvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author mengyiyouth
 * @date 2020/5/27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendBlog implements Serializable {
    private Long id;
    private String title;
    private String firstPicture;
    private boolean recommend;

    @Override
    public String toString() {
        return "RecommendBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
