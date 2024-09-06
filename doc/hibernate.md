## POSTS

`/GET/posts`

[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0
[Hibernate] select p1_0.id,p1_0.date_birthday,p1_0.email,p1_0.name,p1_0.personal_number from person p1_0 where p1_0.id = any (?)
[Hibernate] select c1_0.id,c1_0.description,c1_0.name from category c1_0 where c1_0.id = any (?)

`POST/posts`

[Hibernate] select count(_) from category c1_0 where c1_0.id=?
[Hibernate] select count(_) from person p1_0 where p1_0.id=?
[Hibernate] select nextval('post_id_seq')
[Hibernate] insert into post (author_id,category_id,text,title,id) values (?,?,?,?,?)

`GET/posts/{id}`

[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] select p1_0.id,p1_0.date_birthday,p1_0.email,p1_0.name,p1_0.personal_number from person p1_0 where p1_0.id=?
[Hibernate] select c1_0.id,c1_0.description,c1_0.name from category c1_0 where c1_0.id=?

`PUT/posts/{id}`

[Hibernate] select count(_) from category c1_0 where c1_0.id=?
[Hibernate] select count(_) from person p1_0 where p1_0.id=?
[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] update post set author_id=?,category_id=?,text=?,title=? where id=?
[Hibernate] select p1_0.id,p1_0.date_birthday,p1_0.email,p1_0.name,p1_0.personal_number from person p1_0 where p1_0.id=?
[Hibernate] select c1_0.id,c1_0.description,c1_0.name from category c1_0 where c1_0.id=?

`DELETE/posts/{id}`

[Hibernate] select count(\*) from post p1_0 where p1_0.id=?
[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] select c1_0.post_id,c1_0.id,c1_0.author_id,c1_0.text from comment c1_0 where c1_0.post_id=? order by c1_0.id desc
[Hibernate] delete from post where id=?

`GET/posts/{id}/comments`

[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] select c1_0.post_id,c1_0.id,c1_0.author_id,c1_0.text from comment c1_0 where c1_0.post_id=? order by c1_0.id desc

`DELETE/posts/{id}/comments`

`POST/posts/{id}/comments`

[Hibernate] select nextval('comment_id_seq')
[Hibernate] insert into comment (author_id,post_id,text,id) values (?,?,?,?)

`GET/posts/{id}/comments/{commentId}`

post/findById
coment/findById

[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] select c1_0.id,c1_0.author_id,c1_0.post_id,c1_0.text from comment c1_0 where c1_0.id=? and c1_0.post_id=?

`PUT/posts/{id}/comments/{commentId}`

[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] select c1_0.id,c1_0.author_id,c1_0.post_id,c1_0.text from comment c1_0 where c1_0.id=? and c1_0.post_id=?
[Hibernate] update comment set text=? where id=?re id=?

`DELETE/posts/{id}/comments/{commentId}`

[Hibernate] select p1_0.id,p1_0.author_id,p1_0.category_id,p1_0.text,p1_0.title from post p1_0 where p1_0.id=?
[Hibernate] select c1_0.id,c1_0.author_id,c1_0.post_id,c1_0.text from comment c1_0 where c1_0.id=? and c1_0.post_id=?
[Hibernate] select c1_0.post_id,c1_0.id,c1_0.author_id,c1_0.text from comment c1_0 where c1_0.post_id=? order by c1_0.id desc
[Hibernate] delete from comment where id=?

---

````java
TypedQuery<T> query = this.getEntityManager().createQuery(String qlString, Class<T> resultClass)
```java

https://stackoverflow.com/questions/2002993/jpa-getsingleresult-or-null
````
