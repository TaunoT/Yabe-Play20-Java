# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comments (
  id                        bigint not null,
  author_email              varchar(255),
  posted_at                 timestamp,
  content                   clob,
  post_id                   bigint,
  constraint pk_comments primary key (id))
;

create table posts (
  id                        bigint not null,
  title                     varchar(255),
  posted_at                 timestamp,
  content                   clob,
  author_email              varchar(255),
  constraint pk_posts primary key (id))
;

create table user (
  email                     varchar(255) not null,
  password                  varchar(255),
  fullname                  varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (email))
;

create sequence comments_seq;

create sequence posts_seq;

create sequence user_seq;

alter table comments add constraint fk_comments_author_1 foreign key (author_email) references user (email) on delete restrict on update restrict;
create index ix_comments_author_1 on comments (author_email);
alter table comments add constraint fk_comments_post_2 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_comments_post_2 on comments (post_id);
alter table posts add constraint fk_posts_author_3 foreign key (author_email) references user (email) on delete restrict on update restrict;
create index ix_posts_author_3 on posts (author_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comments;

drop table if exists posts;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comments_seq;

drop sequence if exists posts_seq;

drop sequence if exists user_seq;

