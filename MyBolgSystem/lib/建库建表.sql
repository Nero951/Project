drop database if exists my_blog;
create database my_blog charset utf8mb4;
use my_blog;
-- 创建用户表
create table users (
    id int primary key auto_increment,
    username varchar(40) not null unique,
    password varchar(100) not null
);
-- 创建blog文章表
create table articles (
    id int primary key auto_increment,
    user_id int not null,
    title varchar(200) not null,
    content text not null,
    publish_at datetime not null
);