

DROP DATABASE IF EXISTS coronakitdb;

CREATE DATABASE coronakitdb;

USE coronakitdb;

CREATE TABLE ProductMaster(
	productid int primary key,
	productname varchar(100) not null,
	productcost varchar(8) not null,
	productdesc text not null
);

CREATE TABLE Kit(
	kitid int primary key,
	coronaKitId int not null,
	productId int not null,
	quantity int not null,
	amount int not null
);

CREATE TABLE CoronaKit(
	coronaKitId int primary key,
	pname varchar(50) not null,
	pemail varchar(100) not null,
	contactNumber char(10) not null,
	totalamount int null,
	deliveryAddress text null,
	orderDate varchar(20) null,
	orderFinalized boolean null
);

INSERT INTO ProductMaster VALUES
(1,"Face Mask","50","N95 Face mask"),
(2,"Hand Sanitizer","150","Detol Hand sanitizer"),
(3,"Gloves","20","Hand gloves"); 