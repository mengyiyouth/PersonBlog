package com.mengyi.queryvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mengyiyouth
 * @date 2020/5/27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBlog {
    private String title;
    private Long typeId;
    private boolean recommend;

    @Override
    public String toString() {
        return "SearchBlog{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                '}';
    }
}
