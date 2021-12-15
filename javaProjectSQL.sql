CREATE DATABASE sunrisedb;
USE sunrisedb;

CREATE TABLE MenuItem(
	id int not null auto_increment,
    name varchar(150) not null,
    price decimal(5,2) not null,
    isActive boolean not null default 0,
    dateOfLaunch date not null,
    category varchar(100) not null,
    isFreeDelivery boolean not null default 0,
	primary key(id)
);

CREATE TABLE Cart(
	id int not null auto_increment,
	total decimal(5,2) not null,
    primary key(id)
);

CREATE TABLE CartMenuItem(
	id int not null auto_increment,
    cartId int not null,
    menuItemId int not null,
    primary key(id),
    foreign key(menuItemId) references MenuItem(id),
    foreign key(cartId) references Cart(id)
);