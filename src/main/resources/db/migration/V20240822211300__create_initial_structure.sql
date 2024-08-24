
create table person (
  id serial8 not null,
  personal_number varchar(255) not null,
  name varchar(255) not null,
  date_birthday date not null,
  email varchar(255) not null,
  primary key (id)
);

create table category (
  id serial8 not null,
  name varchar(255) not null,
  description varchar(255) not null,
  primary key (id)
);

create table comment (
  id serial8 not null,
  author_id int8 not null,
  post_id int8 not null,
  text text not null,
  primary key (id)
);

create table post (
  id serial8 not null,
  category_id int8 not null,
  author_id int8 not null,
  title varchar(255) not null,
  text text not null,
  primary key (id)
);


alter table if exists comment
add constraint comment_post_id_fk foreign key (post_id) references post;

alter table if exists comment
add constraint comment_author_id_fk foreign key (author_id) references category;


alter table if exists post
add constraint post_category_id_fk foreign key (category_id) references category;

alter table if exists post
add constraint post_author_id_fk foreign key (author_id) references person;
