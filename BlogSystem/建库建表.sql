create database hjb2_blog charset utf8mb4;
use hjb2_blog;

-- 用户表
create table users (
  id int primary key auto_increment,
  username varchar(40) not null unique,
  password varchar(100) not null
);

create table articles (
  id int primary key auto_increment,
  user_id int not null,
  title varchar(200) not null,
  content text not null,
  published_at datetime not null
);