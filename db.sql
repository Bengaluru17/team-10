create database if not exist RH_INVETORY;
use RH_INVETORY;
create table if not exist INVENTORY
	(
		item_id varchar(15) primary key,
		item_name varchar(25) not null,
		current_count int,
		threshold_count int,
		CHECK (current_count>=0)
	);

