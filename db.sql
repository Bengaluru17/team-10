create database if not exist REACHING_HANDS;
use REACHING_HANDS;

create table IF NOT EXISTS INVENTORY
	(
		item_id int primary key AUTO_INCREMENT,
		item_name varchar(25) not null,
		current_count int,
		threshold_count int,
		CHECK (current_count>=0)
	);

create table IF NOT EXISTS LOGIN
	(
		login_id varchar(25) primary key,
		password varchar(25) not null
	);

