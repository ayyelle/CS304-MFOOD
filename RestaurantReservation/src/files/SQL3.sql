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
 'images/Tomokazu-Broadway.jpg'
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
  'images/lasmargaritas-kitsilano.jpg'
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
  'images/burgoo-lonsdale.jpg'
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
 'images/joeys-broadway.jpg'
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
 'images/stephos-westend.jpg'
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
 'images/chambar-downtown.jpg'
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
 'images/fable-kitchen.jpg'
  );

insert into restaurant values(
  10,
  'Hitoe Sushi',
  '3347 W 4th Ave',
  'Vancouver',
  'Kitsilano',
  'Sushi',
  1000,
 'Amy Chan',
 'achan', 
 'images/hitoe-kitsilano.jpg'
  );


INSERT INTO restaurant VALUES (100, 'Stackhouse Burger Bar', '1224 Granville Street', 'Vancouver', 'Granville', 'steakhouse', 1001, 'Steak Burgs', 'sburgs1', 'images/stackhouseburgerbar-granville.jpg');
INSERT INTO restaurant VALUES (101, 'Phnom Penh', '244 E Georgia Street', 'Vancouver', 'Chinatown', 'cambodian', 1010, 'Viet Pho', 'vpho101', 'images/phnompenh-chinatown.jpg');
INSERT INTO restaurant VALUES (102, 'Pearl Castle Cafe', '3779 Sexmith Road', 'Richmond', 'Continental Center', 'taiwanese', 1020, 'Jack Hsu', 'jhsu102', 'images/pearlcastlecafe-continentalcenter.jpg');
INSERT INTO restaurant VALUES (103, 'Ramen Jinya', '270 Robson', 'Vancouver', 'Downtown', 'japanese', 1030, 'Kuroko Basuke', 'kbas103', 'images/ramenjinya-downtown.jpg');
INSERT INTO restaurant VALUES (104, 'Insadong', '403 North Road', 'Coquitlam', 'Loughheed Village', 'korean', 1040, 'Yoona Kim', 'ykim4', 'images/insadong-loughheedvillage.jpg');

INSERT INTO restaurant VALUES (120, 'Coast Restaurant', '1054 Alberni Street', 'Vancouver', 'West End', 'Seafood', 1120, 'Glowbal Group', 'ggroupcoast','images/coastrestaurant-westend.jpg');
INSERT INTO restaurant VALUES (121, 'Guu Original', '838 Thurlow Street', 'Vancouver', 'West End', 'Japanese', 1221, 'Jenna Wong', 'jwong','images/guuoriginal-westend.jpg');
INSERT INTO restaurant VALUES (122, 'Le Crocodile', '909 Burrard Street', 'Vancouver', 'Downtown', 'French', 1222, 'Michel Jacob', 'mjacob','images/lecrocodile-downtown.jpg');
INSERT INTO restaurant VALUES (123, 'Ebisu On Robson', '827 Bute Street', 'Vancouver', 'Downtown', 'Japanese', 1223, 'Kamei Japenase Group', 'kameiebisu','images/ebisuonrobson-downtown.jpg');
INSERT INTO restaurant VALUES (124, 'Red Robin', '803 Thurlow Street', 'Vancouver', 'Downtown', 'Canadian', 1224, 'Tiana Lowell', 'tlowell','images/redrobin-downtown.jpg');


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


insert into reviews values('Duck needs more salt.', 2, 1, 'lcoombe');
insert into reviews values('The taro paste was made from potatoes.', 3, 1, 'lliu');
insert into reviews values('I loved the lava pie.', 4, 1, 'aleong');
insert into reviews values('Very healthy option.', 2, 1, 'guestUsr');
insert into reviews values('Not enough butter.', 2, 100, 'guestUsr');
insert into reviews values('My cup had lipstick stains.', 2, 1, 'lcoombe');
insert into reviews values('The waiters were not attentive.', 2, 101, 'lliu');
insert into reviews values('You can get better rice noodles elsewhere', 2, 100, 'jwong');
insert into reviews values('Best food in Chinatown!', 5, 102, 'aleong');
insert into reviews values('Very delicious!!!', 5, 102, 'lcoombe');

insert into reviews values('Just like in Japan.', 5, 2, 'jwong');
insert into reviews values('They used real wasabi importaed from Japan.', 4, 103 , 'aleong');
insert into reviews values('Their rolls are the size of burritoes. Wait, what is this place called again?', 3, 104 , 'lcoombe');
insert into reviews values('I saw Kanye West here!', 5, 101 , 'lliu');
insert into reviews values('Their soup tastes like it has fermeted beans in it.', 2, 104 , 'aleong');
insert into reviews values('Not as good as Japan.', 3, 103 , 'guestUsr');
insert into reviews values('I am not a big fan of seaweed.', 1, 120 , 'guestUsr');
insert into reviews values('I got more than I bargained for.', 4, 120 , 'guestUsr');

insert into reviews values('Fish tacos are so good.', 5, 123, 'jwong');
insert into reviews values('The happy hour sangrias are well worth the trip.', 4, 123 , 'aleong');
insert into reviews values('I only had desert, their churros are okay.', 3, 124 , 'lcoombe');
insert into reviews values('Espero que estos gringos saben que no comemos burritoes en México .', 3, 121 , 'lliu');
insert into reviews values('Their food was so spicy. I love spicy.', 5, 122 , 'aleong');
insert into reviews values('Not as good as Senor Picantes.', 3, 3 , 'guestUsr');
insert into reviews values('My stomach does not take to beans well. Everything here had beans in it.', 1, 122 , 'guestUsr');
insert into reviews values('I got more than I bargained for.', 4, 124 , 'guestUsr');

insert into reviews values('I love their onion rings!', 5, 120, 'jwong');
insert into reviews values('Will definitely come again for their Stack Burger', 4, 100, 'aleong');
insert into reviews values('Overhype, they were okay', 3, 102, 'guestUsr');
insert into reviews values('Very crispy fries. Almost as good as McDonalds.', 5, 4, 'lliu');
insert into reviews values('They gave me an extra paddy for free.', 4, 4, 'aleong');
insert into reviews values('Their cola is a little flat.', 3, 4, 'guestUsr');
insert into reviews values('Was dissapointed in the recommended dish.', 1, 4, 'guestUsr');
insert into reviews values('I got more than I bargained for.', 4, 4, 'guestUsr');

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

insert into MenuItem values (7, 'Tagine', 16.00, 6);
insert into MenuItem values (8, 'Wolves Breakfast', 16.00, 6);
insert into MenuItem values (9, 'Cassoulet', 17.00, 6);
insert into MenuItem values (10, 'La Sante', 13.00, 6);
insert into MenuItem values (11, 'Granola', 8.00, 6);
 
insert into MenuItem values (12, 'Lamb Souvlaki', 13.95, 7); 
insert into MenuItem values (13, 'Pork Pita Souvlaki', 8.95, 7);
insert into MenuItem values (14, 'Steamed Clams', 10.95, 7);
insert into MenuItem values (15, 'Spanakopita', 7.95, 7);
insert into MenuItem values (16, 'Greek Salad', 7.95, 7);
insert into MenuItem values (17, 'Marinated Artichokes', 6.95, 7);

insert into MenuItem values (18, 'Pastilla', 21.00, 8);
insert into MenuItem values (19, 'Salade du Printemps', 13.00, 8);
insert into MenuItem values (20, 'Fruits de Mer', 18.00, 8);
insert into MenuItem values (21, 'Coquotte', 29.00, 8);
insert into MenuItem values (22, 'Canard et Gnocchi', 34.00, 8);
insert into MenuItem values (23, 'Pastilla', 21.00, 8);

insert into MenuItem values (24, 'Wild BC Salmon', 26.00, 9);
insert into MenuItem values (25, 'Halibut', 21.00, 9);
insert into MenuItem values (26, 'Onion Gnocchi', 21.00, 9);
insert into MenuItem values (27, 'Smoked Duck Breast', 27.00, 9);
insert into MenuItem values (28, 'Moroccan Lamb', 28.00, 9);
insert into MenuItem values (29, 'Zucchini Tagliatelle', 19.00, 9);

insert into MenuItem values (30, 'Spicy California Roll', 12.95, 10);
insert into MenuItem values (31, 'Avocado Roll', 3.50, 10);
insert into MenuItem values (32, 'Salmon Roll', 2.50, 10);
insert into MenuItem values (33, 'Spicy Tuna Roll', 3.95, 10);
insert into MenuItem values (34, 'Chicken Teriyaki Don', 10.95, 10);
insert into MenuItem values (35, 'Tuna Don', 12.95, 10);


insert into MenuItem values (65, 'King Crab Merus', 38.95, 120);
insert into MenuItem values (53, 'Dungeness Crab Cake', 15.95, 120);
insert into MenuItem values (52, 'Asari-Don', 6.80, 121);
insert into MenuItem values (58, 'Salade Nicoise', 20.50, 122);
insert into MenuItem values (56, 'Duck Confit', 22.00, 122);
insert into MenuItem values (51, 'Venison Medallions', 25.00, 122);
insert into MenuItem values (55, 'Tan Tan Ramen', 9.99, 123);
insert into MenuItem values (58, 'Osaka Box', 10.99, 123);
insert into MenuItem values (56, 'Original Burger', 10.00, 124);


insert into MenuItem values (61, 'Stackhouse Burger', 7.00, 100);
insert into MenuItem values (62, 'Onion Rings', 4.00, 100);
insert into MenuItem values (63, 'Ahi Tuna Burger', 9.00, 100);
insert into MenuItem values (64, 'Seared Tenderloin', 16.00, 100);

insert into MenuItem values (71, 'Lemongrass Pork Chops on rice', 10.00, 101);
insert into MenuItem values (72, 'Prawn Ball Rice Noodles', 7.00, 101);
insert into MenuItem values (73, 'Beef Ball Rice Noodles', 7.00, 101);
insert into MenuItem values (74, 'Phnom Penh Rice Noodles', 8.00, 101);


insert into MenuItem values (81, 'Shredded pork fried rice', 8.00, 102);
insert into MenuItem values (82, 'Fried fish cake', 6.00, 102);
insert into MenuItem values (83, 'Gingseng Chicken Hotpot', 10.00, 102);
insert into MenuItem values (84, 'kung-pow fish nuggets', 9.00, 102);


insert into MenuItem values (41, 'Spicy Chicken Ramen', 12.00, 103);
insert into MenuItem values (42, 'Cha Cha Cha Ramen', 15.00, 103);
insert into MenuItem values (43, 'Tonkotsu Black', 13.00, 103);
insert into MenuItem values (44, 'Tonkotsu Assari', 11.00, 103);


insert into MenuItem values (91, 'Radished Seaweed Roll', 6.00, 104);
insert into MenuItem values (92, 'Family BBQ Combination', 80.00, 104);
insert into MenuItem values (93, 'Seafood Pancake', 15.00, 104);
insert into MenuItem values (94, 'Seafood Tofu Soup', 3.00, 104);

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


insert into HasTable values (1, 2, 120, 0);
insert into HasTable values (2, 2, 120, 0);
insert into HasTable values (3, 4, 120, 0);

insert into HasTable values (1, 2, 121, 0);
insert into HasTable values (2, 2, 121, 0);
insert into HasTable values (3, 4, 121, 0);
insert into HasTable values (4, 6, 121, 0);
insert into HasTable values (5, 8, 121, 0);
insert into HasTable values (6, 4, 121, 0);

insert into HasTable values (1, 2, 122, 0);
insert into HasTable values (2, 2, 122, 0);
insert into HasTable values (3, 4, 122, 0);
insert into HasTable values (4, 6, 122, 0);

insert into HasTable values (1, 2, 123, 0);
insert into HasTable values (2, 2, 123, 0);
insert into HasTable values (3, 4, 123, 0);
insert into HasTable values (4, 6, 123, 0);
insert into HasTable values (5, 8, 123, 0);
insert into HasTable values (6, 4, 123, 0);

insert into HasTable values (1, 2, 124, 0);
insert into HasTable values (2, 2, 124, 0);
insert into HasTable values (3, 4, 124, 0);
insert into HasTable values (4, 6, 124, 0);

insert into HasTable values (1, 2, 100, 0);
insert into HasTable values (2, 2, 100, 0);
insert into HasTable values (3, 4, 100, 0);
insert into HasTable values (4, 4, 100, 0);
insert into HasTable values (5, 8, 100, 0);
insert into HasTable values (6, 8, 100, 0);
insert into HasTable values (7, 20, 100, 0);

insert into HasTable values (1, 2, 101, 0);
insert into HasTable values (2, 4, 101, 0);
insert into HasTable values (3, 4, 101, 0);
insert into HasTable values (4, 6, 101, 0);
insert into HasTable values (5, 6, 101, 0);

insert into HasTable values (1, 2, 102, 0);
insert into HasTable values (2, 2, 102, 0);
insert into HasTable values (3, 4, 102, 0);
insert into HasTable values (4, 4, 102, 0);

insert into HasTable values (1, 2, 103, 0);
insert into HasTable values (2, 2, 103, 0);
insert into HasTable values (3, 4, 103, 0);
insert into HasTable values (4, 4, 103, 0);
insert into HasTable values (5, 4, 103, 0);

insert into HasTable values (1, 2, 104, 0);
insert into HasTable values (2, 6, 104, 0);
insert into HasTable values (3, 6, 104, 0);
insert into HasTable values (4, 6, 104, 0);

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

insert into tablebooking values (TO_TIMESTAMP('2016-06-18 20:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 2, 1, 'aleong');
insert into tablebooking values (TO_TIMESTAMP('2016-06-20 17:00:00', 'yyyy-mm-dd hh24:mi:ss'), 4, 2, 3, 2, 'lcoombe');
insert into tablebooking values (TO_TIMESTAMP('2016-06-25 18:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 5, 'jwong');
insert into tablebooking values (TO_TIMESTAMP('2016-06-19 19:00:00', 'yyyy-mm-dd hh24:mi:ss'), 6, 2, 4, 3, 'aleong');
insert into tablebooking values (TO_TIMESTAMP('2016-07-18 20:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 4, 'lliu');
insert into tablebooking values (TO_TIMESTAMP('2016-07-18 16:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 100, 'aleong');
insert into tablebooking values (TO_TIMESTAMP('2016-07-08 18:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 100, 'lcoombe');
insert into tablebooking values (TO_TIMESTAMP('2016-07-10 20:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 100, 'lliu');

insert into tablebooking values (TO_TIMESTAMP('2016-08-08 18:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 101, 'lcoombe');
insert into tablebooking values (TO_TIMESTAMP('2016-09-08 18:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 102, 'lcoombe');
insert into tablebooking values (TO_TIMESTAMP('2016-10-08 18:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2, 1, 103, 'lcoombe');



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

Insert into employeeworkat values (110,'Sammy Lowe', 'slowe',120);
Insert into employeeworkat values (210,'Wendy Carr', 'wcarr',121);
Insert into employeeworkat values (310,'Jason LeBlanc','jleblanc',122);
Insert into employeeworkat values (410,'Rebecca Wilde','rwilde',123);
Insert into employeeworkat values (510,'Sarah Chung','schung',124);

Insert into employeeworkat values (122,'Cat Pauls', 'cp100',100);
Insert into employeeworkat values (121,'Bob Bumblebee', 'bb101',100);
Insert into employeeworkat values (123,'Fred Fish', 'ff101',101); 
Insert into employeeworkat values (124,'Thomas Tugboat','Ttug100',102);
Insert into employeeworkat values (125,'Sasha Brause','potato1',103);
Insert into employeeworkat values (126,'Mikasa Ackerman','mack111',104);


drop View CuisineMax;

Create View CuisineMax AS (Select r.cuisine, MAX(re.rating) AS maxrating from restaurant r,reviews re where r.RID=re.RID group by r.cuisine);