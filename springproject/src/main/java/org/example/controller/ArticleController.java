package org.example.controller;

import org.example.model.Article;
import org.example.model.User;
import org.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    //显示所有文章信息,还需要用户信息告诉前端是否登录
    //一般响应的数据格式：使用已有的模型，或创建新的模型
    @GetMapping("/query")
    public Object query(HttpServletRequest req ){
        User user = null;
        //获取session对象，如果未登录，返回null
        HttpSession session = req.getSession(false);
        if(session!=null){
            User get = (User)session.getAttribute("user");
            if(get!=null){
                user=get;
            }
        }
        List<Article> list = articleService.queryAll();
        //返回的数据：map，articles=List<Article>,如果登录，就存user=用户信息，
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("articles", list);
        return map;
    }

    @GetMapping("/queryByUser")
    public Object queryByUser(HttpSession session){
        //数据库通过登录用户Id查询对应的文章
        User user = (User)session.getAttribute("user");

        List<Article> articles = articleService.queryUserId(user.getId());
        //返回模拟的文章数据
        return articles;
    }

    @GetMapping("/query/{id}")
    public Object queryById(@PathVariable Integer id){
        Article article = articleService.queryById(id);
        return article;
    }


}
