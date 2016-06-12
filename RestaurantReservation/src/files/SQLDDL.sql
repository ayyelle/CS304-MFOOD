drop table restaurant cascade constraints;
drop table customer cascade constraints;
drop table reviews;
drop table menuitem;
drop table hastable cascade constraints;
drop table tablebooking;
drop table EmployeeWorkAt;

create table restaurant (
  RID int primary key,
  name varchar(50),
  street_address varchar(50),
  city varchar(50),
  location varchar(50),
  cuisine varchar(50),
  OID int NOT NULL,
  ownerName varchar(50),
  ownerPassword varchar(50) NOT NULL,
  img varchar(100),
  unique(name, location)
  );

insert into restaurant values(
  1,
  'Joeys',
  '820 Burrard St',
  'Vancouver',
  'Burrard',
  'Canadian',
  1000,
  'Joey Tomato',
  'joeys1',
  'images/joey-burrard.jpg'
  );

insert into restaurant values(
  2,
  'Tomokazu',
  '1128 W Broadway',
  'Vancouver',
  'Broadway',
  'Sushi',
  2000,
 'Yoshi Nintendo',
 'tomokazu2',
 '!!.jpg'
  );

insert into restaurant values(
  3,
  'Las Margaritas',
  '1999 W 4th Ave',
  'Vancouver',
  'Kitsilano',
  'Mexican',
  3000,
  'Diego Gonzalez',
  'lasmargaritas3',
  '!!.jpg'
  );

insert into restaurant values(
  4,
  'Burgoo',
  '3 Lonsdale Ave',
  'North Vancouver',
  'Lonsdale',
  'Comfort food',
  4000,
  'Jill Hipstar',
  'jhip',
  '!!.jpg'
  );

insert into restaurant values(
  5,
  'Joeys',
  '1424 W Broadway',
  'Vancouver',
  'Broadway',
  'Canadian',
  5000,
 'Mario Joey',
 'marioj',
 '!!.jpg'
  );

insert into restaurant values(
  6,
  'Cafe Medina',
  '780 Richards Street',
  'Vancouver',
  'Downtown',
  'European',
  6000,
 'Robbie Kane',
 'rkane',
 'images/cafe-medina.gif'
  );

insert into restaurant values(
  7,
  'Stephos Souvlaki Greek Taverna',
  '1124 Davie Street',
  'Vancouver',
  'West End',
  'Greek',
  7000,
 'Steph Vardalos',
 'svard', 
 '!!.png'
  );

insert into restaurant values(
  8,
  'Chambar',
  '568 Beatty Street',
  'Vancouver',
  'Downtown',
  'European',
  8000,
 'Nico Schuermans',
 'nicocham',
 '!!.png'
  );

insert into restaurant values(
  9,
  'Fable Kitchen',
  '1944 W 4th Avenue',
  'Vancouver',
  'Kitsilano',
  'Canadian',
  9000,
 'Trevor Bird',
 'birdy',
 'fable-kitchen.png'
  );

insert into restaurant values(
  10,
  'Jethros Fine Grub',
  '3420 Dunbar Street',
  'Vancouver',
  'Dunbar',
  'Diner',
  1000,
 'Emily-Jane Stuart',
 'ejGrub', 
 '!!.png'
  );


create table customer(
  UserName varchar(50) primary key,
  password varchar(50) NOT NULL,
  firstName varchar(50),
  lastName varchar(50),
  PhoneNum varchar(20)
  );

insert into customer values ('lcoombe', '12345', 'Lauren', 'Coombe', '778-555-1123');
insert into customer values ('aleong', '5678', 'Anna', 'Leong', '778-445-2567');
insert into customer values ('jwong', 'pass', 'Jolene', 'Wong', '778-677-8842');
insert into customer values ('lliu', 'password', 'Leo', 'Liu', '778-646-1123');
insert into customer values ('guestUsr', 'guestpass', 'Guest', 'User', '445-225-1111');

create table reviews(
  comments varchar(1000),
  rating int,
  RID int,
  UserName varchar(50),
  foreign key (RID) references restaurant,
  foreign key (UserName) references customer
  );

insert into reviews values ('This is a great restaurant.', 5, 2, 'aleong');
insert into reviews values ('I had a really bad meal here.', 2, 3, 'guestUsr');
insert into reviews values ('The food was delicious, and the atmosphere great!', 4, 1, 'lcoombe');
insert into reviews values ('Id definately go back!', 5, 4, 'aleong');
insert into reviews values ('Make sure you try the macaroni.', 4, 5, 'jwong');

create table MenuItem(
  FID int,
  name varchar(50),
  price real,
  RID int,
  PRIMARY KEY(FID, RID),
  foreign key (RID) references restaurant
  ); 

insert into MenuItem values (2, 'Macaroni and Cheese', 12.00, 5);
insert into MenuItem values (3, 'Tomato Soup', 8.00, 5);
insert into MenuItem values (4, 'Lobster Grilled Cheese', 15.00, 1);
insert into MenuItem values (5, 'Chicken Teriyaki', 7.50, 2);
insert into MenuItem values (6, 'Tacos', 10.00, 3);

create table HasTable(
  TID int,
  tablesize int,
  RID int,
  numBooked int,
 PRIMARY KEY(TID, RID),
  foreign key (RID) references restaurant
  );

insert into HasTable values (1, 2, 1, 0);
insert into HasTable values (2, 2, 1, 0);
insert into HasTable values (3, 4, 1, 0);

insert into HasTable values (1, 2, 2, 0);
insert into HasTable values (2, 2, 2, 0);
insert into HasTable values (3, 4, 2, 0);
insert into HasTable values (4, 6, 2, 0);

insert into HasTable values (1, 2, 3, 0);
insert into HasTable values (2, 2, 3, 0);
insert into HasTable values (3, 4, 3, 0);
insert into HasTable values (4, 8, 3, 0);

insert into HasTable values (1, 2, 4, 0);
insert into HasTable values (2, 4, 4, 0);

insert into HasTable values (1, 2, 5, 0);
insert into HasTable values (2, 4, 5, 0);
insert into HasTable values (3, 5, 5, 0);
insert into HasTable values (4, 6, 5, 0);

insert into HasTable values (1, 2, 6, 0);
 insert into HasTable values (2, 4, 6, 0);
 insert into HasTable values (3, 5, 6, 0);
 insert into HasTable values (4, 6, 6, 0);
 
 insert into HasTable values (1, 2, 7, 0);
 insert into HasTable values (2, 4, 7, 0);
 insert into HasTable values (3, 5, 7, 0);
 insert into HasTable values (4, 6, 7, 0);
 insert into HasTable values (5, 2, 7, 0);
 insert into HasTable values (6, 2, 7, 0);
 
 insert into HasTable values (1, 2, 8, 0);
 insert into HasTable values (2, 4, 8, 0);
 insert into HasTable values (3, 5, 8, 0);
insert into HasTable values (4, 6, 8, 0);
insert into HasTable values (5, 2, 8, 0);
insert into HasTable values (6, 2, 8, 0);

insert into HasTable values (1, 2, 9, 0);
insert into HasTable values (2, 4, 9, 0);
insert into HasTable values (3, 5, 9, 0);
insert into HasTable values (4, 6, 9, 0);
insert into HasTable values (5, 4, 9, 0);
insert into HasTable values (6, 10, 9, 0);

insert into HasTable values (1, 2, 10, 0);
insert into HasTable values (2, 4, 10, 0);
insert into HasTable values (3, 2, 10, 0);
insert into HasTable values (4, 6, 10, 0);
insert into HasTable values (5, 4, 10, 0);
insert into HasTable values (6, 10, 10, 0);

create table TableBooking(
  StartDayTime timestamp,
  PartySize int,
  Duration int,
  TID int,
  RID int,
  UserName varchar(50),
  primary key (TID, RID, StartDayTime, UserName),
  foreign key (TID, RID) references HasTable on delete cascade,
  foreign key (UserName) references customer on delete cascade
  );

 CREATE OR REPLACE TRIGGER tableBookUpdate
 AFTER INSERT ON TableBooking
 REFERENCING NEW AS n
 FOR EACH ROW
 BEGIN
         update hastable h
         set h.numBooked = h.numBooked + 1
        where h.TID = :n.TID and h.RID = :n.RID;
END;
/

insert into tablebooking values (TO_TIMESTAMP('2016-05-16 20:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 2, 1, 'aleong');
insert into tablebooking values (TO_TIMESTAMP('2016-05-20 17:00:00', 'yyyy-mm-dd hh24:mi:ss'), 4, 2, 3, 2, 'lcoombe');
insert into tablebooking values (TO_TIMESTAMP('2016-05-25 18:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 5, 'jwong');
insert into tablebooking values (TO_TIMESTAMP('2016-05-17 19:00:00', 'yyyy-mm-dd hh24:mi:ss'), 6, 2, 4, 3, 'aleong');
insert into tablebooking values (TO_TIMESTAMP('2016-05-18 20:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 4, 'lliu');

Create table EmployeeWorkAt(
  EID int,
  Name varchar(20),
  Password varchar(30),
  RID int,
  PRIMARY KEY(EID, RID),
  FOREIGN KEY(RID) references Restaurant on delete cascade
);

Insert into employeeworkat values (100,'Jane Smith', 'jsmith',1);
Insert into employeeworkat values (200,'Amy Lee', 'alee',2);
Insert into employeeworkat values (300,'Larry Jones','ljones',3);
Insert into employeeworkat values (400,'Chris Young','cyoung',4);
Insert into employeeworkat values (500,'Lily White','lwhite',5);

Insert into employeeworkat values (600,'Anna OConnel','aoconn',6);
Insert into employeeworkat values (601,'Patrick OConnel','poconn',6);

Insert into employeeworkat values (700,'Stuart Bonbon','sbon',7);

Insert into employeeworkat values (800,'Trevor Eagle','theEagle',8);

Insert into employeeworkat values (900,'Sarah Smith','ssmith',9);
Insert into employeeworkat values (901,'Joe Fresh','jfresh',9);

Insert into employeeworkat values (1000,'Harry Jones','jonesy',10);




drop View CuisineMax;

Create View CuisineMax AS (Select r.cuisine, MAX(re.rating) AS maxrating from restaurant r,reviews re where r.RID=re.RID group by r.cuisine);