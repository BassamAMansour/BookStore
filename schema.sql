create schema if not exists book_store collate utf8mb4_0900_ai_ci;

create table if not exists author
(
  id         int auto_increment primary key,
  authorName varchar(45) not null,
  constraint authorName unique (authorName)
);

create table if not exists publisher
(
  id      int auto_increment,
  name    varchar(45) not null,
  phone   varchar(15) not null,
  address varchar(50) not null,
  primary key (id, name)
);

create index publisher_name_index
  on publisher (name);

create table if not exists user
(
  id            int auto_increment,
  username      varchar(45) not null,
  password      varchar(45) not null,
  email         varchar(45) not null,
  firstName     varchar(45) not null,
  lastName      varchar(45) not null,
  phone         varchar(15) not null,
  address       varchar(45) not null,
  privilegeType int         not null,
  primary key (id, username)
);

create table if not exists book
(
  isbn            int         not null,
  title           varchar(45) not null,
  authorID        int         not null,
  threshold       int         not null,
  sellingPrice    int         not null,
  category        int         not null,
  quantity        int         not null,
  publicationYear int         not null,
  publisherID     int         not null,
  addedBy         int         not null,
  primary key (isbn, title),

  constraint isbn unique (isbn),

  constraint book_author_id_fk
    foreign key (authorID) references author (id)
      on update cascade on delete cascade,
  constraint book_publisher_id_fk
    foreign key (publisherID) references publisher (id)
      on update cascade on delete cascade,
  constraint book_user_id_fk
    foreign key (addedBy) references user (id)
      on update cascade on delete cascade
);

create index book_category_index
  on book (category);

create table if not exists `order`
(
  id              int auto_increment primary key,
  orderedQuantity int not null,
  bookID          int not null,
  constraint order_book_isbn_fk
    foreign key (bookID) references book (isbn)
      on update cascade on delete cascade
);

create table if not exists sale
(
  id             int auto_increment primary key,
  saleDate       date        not null,
  expirationDate date        not null,
  cardNum        varchar(20) not null,
  soldQuantity   int         not null,
  userID         int         not null,
  bookID         int         not null,
  constraint sale_book_isbn_fk
    foreign key (bookID) references book (isbn)
      on update cascade on delete cascade,
  constraint sale_user_id_fk
    foreign key (userID) references user (id)
      on update cascade on delete cascade
);

delimiter //
create trigger noNegativeQuantity before update on book for each row
begin
  if new.quantity < 0 then
     signal sqlstate '45000';
  end if;
end //
delimiter ;

delimiter //
create trigger createOrder after update on book for each row
begin
  if new.quantity < new.threshold then
    insert into `order` values (NULL, 20, NEW.isbn);
  end if;
end //
delimiter ;
delimiter //

delimiter //
create trigger orderDeletion before delete on `order` for each row
begin
  update book set quantity = quantity + OLD.orderedQuantity where isbn = OLD.bookID;
end //
delimiter ;
delimiter //

use book_store;

insert into sale values(30055, "2019-11-13", "2019-2-27", 7450728554716622, 34, 80310, 5772529);
