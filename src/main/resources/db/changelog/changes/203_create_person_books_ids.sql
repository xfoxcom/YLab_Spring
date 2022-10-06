CREATE SCHEMA IF not exists ulab_edu;

create table ulab_edu.person_books_ids
(

  person_id integer not null,
  books_ids integer

);

comment on table ulab_edu.person_books_ids is 'Хранение зависимости пользователь - книга';
comment on column ulab_edu.person_books_ids.person_id is 'Идентификатор пользователя';
comment on column ulab_edu.person_books_ids.books_ids is 'Идентификатор книги';


