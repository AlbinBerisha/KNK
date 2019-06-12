drop database if exists HCIProject;
create database HCIProject;

use HCIProject;

create table users (
	uID integer auto_increment not null,
    uFirstName varchar(32) not null,
    uLastName varchar(32) not null,
    uGender varchar(8) not null,
    uBirthDate date not null,
    uUsername varchar(32) not null unique,
    uPassword varchar(32) not null,
    uProfilePicture varchar(32) not null,
    primary key (uID)
);

create table countries (
	countryID integer auto_increment,
    countryName varchar(32),
    countryCode varchar(2),
    primary key(countryID, countryCode)
);

create table cities (
	cityID integer auto_increment,
    countryID integer,
    cityName varchar(32),
    cityCode integer,
    primary key (cityID, cityCode),
    foreign key (countryID) references countries (countryID)
);

create table cars (
	carID int auto_increment,
    carManufacturer varchar(32),
    carModel varchar(32),
    carImagePath varchar(32),
    primary key(carID)
);

create table rentHistory (
	rID int auto_increment not null,
    uUsername varchar(32),
    rOrderDate date not null,
    rCar varchar(32) not null,
    rLocation varchar(32) not null,
    rPick_upDate date not null,
    rDrop_offDate date not null,
    primary key (rID),
    foreign key (uUsername) references users (uUsername)
);

insert into users(uFirstName, uLastName, uGender, uBirthDate, uUsername, uPassword, uProfilePicture) values 
('Albin', 'Berisha', 'Male', '1999-05-13', 'AlbinBerisha', '123123', 'Default Profile Picture.png'),
('Ardit', 'Konjuhi', 'Male', '1998-01-28', 'ArditKonjuhi', 'asdasd', 'Default Profile Picture.png');

insert into countries(countryName, countryCode) values('Kosovo', 'XK');
insert into countries(countryName, countryCode) values('Albania', 'AL');
insert into countries(countryName, countryCode) values('North Macedonia', 'MK');
insert into countries(countryName, countryCode) values('Montenegro', 'ME');

insert into cities(countryID, cityName, cityCode) values (1, 'Prishtina', 10000);
insert into cities(countryID, cityName, cityCode) values(1, 'Prizren', 20000);

insert into cars (carManufacturer, carModel, carImagePath) values ('Audi', 'Q5', '/images/Audi Q5.png');
insert into cars (carManufacturer, carModel, carImagePath) values ('Audi', 'A5 Coupe', '/images/Audi A5 Coupe.png');
insert into cars (carManufacturer, carModel, carImagePath) values ('Audi', 'A1', '/images/Audi A1.png');
insert into cars (carManufacturer, carModel, carImagePath) values ('Audi', 'Q8', '/images/Audi Q8.png');
insert into cars (carManufacturer, carModel, carImagePath) values ('Volkswagen', 'Passat', '/images/Volkswagen Passat.png');
insert into cars (carManufacturer, carModel, carImagePath) values ('Volkswagen', 'T-Roc', '/images/Volkswagen T-Roc.png');

insert into rentHistory (uUsername, rOrderDate, rCar, rLocation, rPick_upDate, rDrop_offDate) values ('AlbinBerisha', '2019-04-01', 'Audi Q5', 'Prishtina, Kosovo', '2019-05-13', '2019-06-19');
