/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/17 23:01:48                           */
/*==============================================================*/


drop table if exists UserInfo;

drop table if exists Users;

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
);

/*==============================================================*/
/* Table: Users                                                 */
/*==============================================================*/
create table Users
(
   user_id              bigint not null auto_increment,
   email                varchar(128) not null,
   password             varchar(64) not null,
   password_md5         varchar(256) not null,
   primary key (user_id)
);

alter table UserInfo add constraint FK_Reference_1 foreign key (user_id)
      references Users (user_id) on delete restrict on update restrict;

