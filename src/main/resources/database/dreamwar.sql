create table users (
  id bigserial primary key not null,
  user_name varchar unique not null,
  encrypted_password varchar,
  role_id bigint,
  state varchar,
  created_at timestamp,
  updated_at timestamp
);
create table roles (
  id bigserial primary key not null,
  name varchar,
  permissions text,
  created_at timestamp,
  updated_at timestamp
);
create table menus (
  id bigserial primary key not null,
  parent_id bigint,
  key varchar,
  name varchar,
  url varchar
);
create table resources (
  id bigserial primary key not null,
  menu_id bigint,
  entity_name varchar,
  permission varchar
);
create table storages (
  id bigserial primary key not null,
  file_hash varchar unique not null,
  file_name varchar,
  file_size bigint,
  file_type varchar,
  original_file_name varchar,
  created_at timestamp
);

DROP TABLE book IF EXISTS;
DROP TABLE book_store IF EXISTS;
DROP TABLE user IF EXISTS;

CREATE TABLE book (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  book_store_id BIGINT,
  name          VARCHAR(80),
  author        VARCHAR(80),
  price         DECIMAL(10,2),
  topic         VARCHAR(80),
  publish_date  DATE
);

CREATE TABLE book_store (
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  name         VARCHAR(80),
  address      VARCHAR(80)
);

CREATE TABLE user (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  username      VARCHAR(80),
  password      VARCHAR(80)
);

-- admin/123456
insert into users(id, user_name, encrypted_password, state, created_at, updated_at) values (1, 'admin', '$2a$10$apcHrdYrw.4mV6jUgVSX9ubLdnv6CyrM9Bp7a/eYoO4gOGak8ksJG', 'enable', now(), now());

insert into menus(id, parent_id, key, name, url) values (1, 0, 'sys', 'System', '');
insert into menus(id, parent_id, key, name, url) values (10, 1, 'user', 'UserBO', '/users');
insert into menus(id, parent_id, key, name, url) values (11, 1, 'role', 'Role', '/roles');

insert into resources(id, menu_id, entity_name, permission) values (100, 10, 'UserBO', 'read');
insert into resources(id, menu_id, entity_name, permission) values (101, 10, 'UserBO', 'create');
insert into resources(id, menu_id, entity_name, permission) values (102, 10, 'UserBO', 'update');
insert into resources(id, menu_id, entity_name, permission) values (103, 10, 'UserBO', 'destroy');
insert into resources(id, menu_id, entity_name, permission) values (110, 11, 'Role', 'read');
insert into resources(id, menu_id, entity_name, permission) values (111, 11, 'Role', 'create');
insert into resources(id, menu_id, entity_name, permission) values (112, 11, 'Role', 'update');
insert into resources(id, menu_id, entity_name, permission) values (113, 11, 'Role', 'destroy');


INSERT INTO book_store VALUES ('1', '新华书店', '湖北省武汉市洪山区文秀街131号');

INSERT INTO book VALUES ('1', '1', '社会研究方法教程', '袁方', '68.00', '社会学', '2015-03-01');
INSERT INTO book VALUES ('2', '1', '算法', '高德纳', '108.00', '数据结构', '2014-02-13');
INSERT INTO book VALUES ('3', '1', 'Java核心技术Ⅰ', 'Cay', '93.00', '编程语言', '2011-06-14');
INSERT INTO book VALUES ('4', '1', '现代操作系统', 'William', '56.50', '操作系统', '2016-08-23');
INSERT INTO book VALUES ('5', '1', 'Head First设计模式', 'Freeman', '32.00', '设计模式', '2013-10-15');
INSERT INTO book VALUES ('6', '1', '学习OpenCV', 'Bradski', '46.00', '技术', '2014-02-13');
INSERT INTO book VALUES ('7', '1', '小王子', '周克希', '15.00', '文学', '2008-07-13');
INSERT INTO book VALUES ('8', '1', 'Effective Java', 'Bloch', '38.00', '编程语言', '2014-12-03');
INSERT INTO book VALUES ('9', '1', '编程珠玑', 'Jon', '36.00', '数据结构', '2013-12-03');
INSERT INTO book VALUES ('10', '1', 'SQL必知必会', 'Ben', '13.00', '数据库', '2015-08-26');
INSERT INTO book VALUES ('11', '1', '编译器设计', 'Kelth', '59.00', '编译器', '2014-08-13');

INSERT INTO user VALUES ('1', 'shawn', 'fucksecurity');

commit;