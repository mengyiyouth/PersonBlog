package com.mengyi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog{
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Long views;

    private Integer commentCount;

    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;

    /*得到一个博客的所有标签*/
    private List<Tag> tags = new ArrayList<>();
    /*不会进入数据库，一个博客的所有标签id*/
    private String tagIds;

    private Long typeId;
    private Long userId;
    private String description;
    private Type type;
    private User user;
    private List<Comment> comments = new ArrayList<>();



    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", commentCount=" + commentCount +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
