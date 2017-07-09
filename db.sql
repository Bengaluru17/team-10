create database if not exist REACHING_HANDS;
use REACHING_HANDS;

create table IF NOT EXISTS INVENTORY
	(
		item_id int primary key AUTO_INCREMENT,
		item_name varchar(25) not null,
		item_type varchar(25) not null,
		current_count int,
		threshold_count int,
		CHECK (current_count>=0)
	);

create table IF NOT EXISTS LOGIN
	(
		login_id varchar(25) primary key,
		password varchar(25) not null
	);


INSERT INTO INVENTORY VALUES(1,"bath_soap","boy",1,1);
INSERT INTO INVENTORY VALUES(2,"shampoo","boy",2,1);
INSERT INTO INVENTORY VALUES(3,"shampoo","girl",2,1);
INSERT INTO INVENTORY VALUES(4,"bath_soap","girl",1,1);
INSERT INTO INVENTORY VALUES(5,"sortseqip","boy",3,1);
INSERT INTO INVENTORY VALUES(6,"sortseqip","girl",3,1);
INSERT INTO INVENTORY VALUES(7,"toothpaste","boy",1,1);
INSERT INTO INVENTORY VALUES(8,"detergent","kitchen",2,1);
INSERT INTO INVENTORY VALUES(9,"soap","kitchen",3,1);
INSERT INTO INVENTORY VALUES(10,"toothpaste","girl",1,1);	


INSERT INTO LOGIN VALUES("admin","password");
INSERT INTO LOGIN VALUES("Staff1","password1");
INSERT INTO LOGIN VALUES("skp","123");
INSERT INTO LOGIN VALUES("Staff2","123");

create table IF NOT EXISTS CHILDREN
	(
		children_id int primary key AUTO_INCREMENT,
		name varchar(25) not null,
		age int not null,
		gender varchar(10),
		dateofjoin date,
		school_type varchar(30) not null,
		school_name varchar(30) not null,
		standard varchar(5) not null,
		grade varchar(2) not null
	);


INSERT INTO CHILDREN VALUES(1,"Ram",5,"MALE","01-01-2012","Home-School","Reaching Hands","I","A");
INSERT INTO CHILDREN VALUES(2,"Ashwini",6,"FEMALE","03-08-2016","Private","st.Mary","V","C");
INSERT INTO CHILDREN VALUES(3,"Sham",15,"MALE","21-08-2002","Private","Jain","IX","B");
INSERT INTO CHILDREN VALUES(4,"Pragya",7,"FEMALE","09-07-2010","Home-School","Reaching Hands","IV","A");
INSERT INTO CHILDREN VALUES(5,"Varsha",11,"FEMALE","30-05-2012","Home-School","Reaching Hands","VII","B");
INSERT INTO CHILDREN VALUES(6,"Raghav",16,"MALE","01-08-2002","Private","st.Miras","IX","C");
INSERT INTO CHILDREN VALUES(7,"Amogh",5,"MALE","01-05-2012","Home-School","Reaching Hands","II","A");

create table IF NOT EXISTS STAFF
	(
		staff_id int primary key AUTO_INCREMENT,
		name varchar(25) not null,
		dateofjoin date not null,
		role varchar(40) not null
	);


INSERT INTO STAFF VALUES(1,"Sumuki","01-02-1996","Cook");
INSERT INTO STAFF VALUES(2,"Rajesh","07-08-2015","Driver");
INSERT INTO STAFF VALUES(3,"Ramya","08-09-2013","Volunteer");
INSERT INTO STAFF VALUES(4,"Arya","08-05-2001","Volunteer");
INSERT INTO STAFF VALUES(5,"Santosh","08-12-2012","Accountancy");
