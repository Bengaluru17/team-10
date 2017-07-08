create database if not exist REACHING_HANDS;
use REACHING_HANDS;
create table INVENTORY
	(
		item_id varchar(15) primary key,
		item_name varchar(25) not null,
		current_count int,
		threshold_count int,
		CHECK (current_count>=0)
	);

