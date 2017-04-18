/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/18 21:53:09                           */
/*==============================================================*/


drop table if exists User;

drop table if exists UserInfo;

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   user_id              bigint not null auto_increment,
   email                varchar(128) not null,
   password             varchar(64) not null,
   password_md5         varchar(256) not null,
   primary key (user_id)
)
charset = UTF8;

/*==============================================================*/
/* Table: UserInfo                                              */
/*==============================================================*/
create table UserInfo
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   email                varchar(128) not null,
   nickname             varchar(50),
   phone                varchar(20),
   birthday             date,
   age                  smallint,
   sex                  smallint,
   bio                  varchar(100),
   location             varchar(100),
   primary key (id)
)
charset = UTF8;

alter table UserInfo add constraint FK_Reference_1 foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

