package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Comment {

    private Integer id;
    private String content;
    private Integer commenter;//评论者
    private Integer articleId;//文章Id--外键
    private Date createTime;//创建日期
}
