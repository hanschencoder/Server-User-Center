/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/26 20:21:58                           */
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
   primary key (user_id),
   unique key AK_Key_2 (email)
)
charset = UTF8;

/*==============================================================*/
/* Table: UserInfo                                              */
/*==============================================================*/
create table UserInfo
(
   user_info_id         bigint not null auto_increment,
   user_id              bigint not null,
   nickname             varchar(50),
   phone                varchar(20),
   birthday             date,
   sex                  smallint,
   bio                  varchar(100),
   primary key (user_info_id),
   unique key AK_Key_2 (user_id)
)
charset = UTF8;

alter table UserInfo add constraint FK_Reference_1 foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

