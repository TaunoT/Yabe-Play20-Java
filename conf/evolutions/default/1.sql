# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comments (
  id                        bigint auto_increment not null,
  author_email              varchar(255),
  posted_at                 datetime,
  content                   longtext,
  post_id                   bigint,
  constraint pk_comments primary key (id))
;

create table posts (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  posted_at                 datetime,
  content                   longtext,
  author_email              varchar(255),
  constraint pk_posts primary key (id))
;

create table tags (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_tags primary key (id))
;

create table users (
  email                     varchar(255) not null,
  password                  varchar(255),
  fullname                  varchar(255),
  is_admin                  tinyint(1) default 0,
  constraint pk_users primary key (email))
;


create table posts_tags (
  posts_id                       bigint not null,
  tags_id                        bigint not null,
  constraint pk_posts_tags primary key (posts_id, tags_id))
;
alter table comments add constraint fk_comments_author_1 foreign key (author_email) references users (email) on delete restrict on update restrict;
create index ix_comments_author_1 on comments (author_email);
alter table comments add constraint fk_comments_post_2 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_comments_post_2 on comments (post_id);
alter table posts add constraint fk_posts_author_3 foreign key (author_email) references users (email) on delete restrict on update restrict;
create index ix_posts_author_3 on posts (author_email);



alter table posts_tags add constraint fk_posts_tags_posts_01 foreign key (posts_id) references posts (id) on delete restrict on update restrict;

alter table posts_tags add constraint fk_posts_tags_tags_02 foreign key (tags_id) references tags (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comments;

drop table posts;

drop table posts_tags;

drop table tags;

drop table users;

SET FOREIGN_KEY_CHECKS=1;

