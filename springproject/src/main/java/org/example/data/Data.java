package org.example.data;


import org.example.model.Article;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Article> allArticles(){
        List<Article> list = new ArrayList<>();
        Article a1 = new Article();
        a1.setId(1);
        a1.setTitle("文章1");
        a1.setContent("内容1");
        Article a2 = new Article();
        a2.setId(2);
        a2.setTitle("文章2");
        a2.setContent("内容2");
        Article a3 = new Article();
        a3.setId(3);
        a3.setTitle("文章3");
        a3.setContent("内容3");
        list.add(a1);
        list.add(a2);
        list.add(a3);
        return list;
    }
    public static List<Article> allArticlesByUser(){
        List<Article> list = new ArrayList<>();
        Article a1 = new Article();
        a1.setId(1);
        a1.setTitle("文章1");
        a1.setContent("内容1");
        Article a2 = new Article();
        a2.setId(2);
        a2.setTitle("文章2");
        a2.setContent("内容2");
        Article a3 = new Article();
        a3.setId(3);
        a3.setTitle("文章3");
        a3.setContent("内容3");
        list.add(a1);
        list.add(a2);
        list.add(a3);
        return list;
    }
}
