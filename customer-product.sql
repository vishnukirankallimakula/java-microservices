DROP DATABASE IF EXISTS `customerproductmgmtdb`;

CREATE DATABASE `customerproductmgmtdb`;
use customerproductmgmtdb;

DROP TABLE IF EXISTS `customerproductmgmtdb`.`cust_order`;
CREATE TABLE  `customerproductmgmtdb`.`cust_order` (
  `order_id` int(11) NOT NULL auto_increment,
  `billing_amount` double NOT NULL,
  `customer_id` int(11) NOT NULL,
  `order_date` date default NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY  (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2015 DEFAULT CHARSET=latin1;

DELETE from `cust_order`;

INSERT INTO cust_order (order_id,billing_amount,customer_id,order_date,product_id,quantity) VALUES
(2001,1998,1001,'2022-08-07',1,2),
(2002,5998,1001,'2022-06-07',5,2),
(2003,6790,1001,'2022-01-03',9,1),

(2004,3999,1003,'2022-03-20',3,1),
(2005,21999,1003,'2022-06-17',7,1),

(2006,8999,1008,'2022-08-02',2,1),
(2007,6790,1008,'2022-06-12',9,1),
(2008,10400,1008,'2022-03-07',4,1),

(2009,13580,1010,'2022-03-07',9,2),
(2010,13500,1010,'2022-03-16',6,3),
(2011,16500,1010,'2022-10-10',8,1),
(2012,3999,1010,'2022-11-29',3,1),


(2013,3996,1006,'2022-06-07',1,4),
(2014,8999,1006,'2022-06-07',2,1);

commit;

DROP TABLE IF EXISTS `customerproductmgmtdb`.`customer`;
CREATE TABLE  `customerproductmgmtdb`.`customer` (
  `customer_id` int(11) NOT NULL auto_increment,
  `customer_email` varchar(255) default NULL,
  `customer_type` varchar(255) default NULL,
  PRIMARY KEY  (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=latin1;

DELETE from `customer`;

INSERT INTO customer (customer_id, customer_email, customer_type) VALUES
(1001, 'Jack@accenture.com', 'Gold'),
(1002, 'Mary@accenture.com', 'Silver'),
(1003, 'John@accenture.com', 'Platinum'),
(1004, 'Joseph@accenture.com', 'Gold'),
(1005, 'Ram@accenture.com', 'Silver'),
(1006, 'Jerrif@accenture.com', 'Platinum'),
(1007, 'Justin@accenture.com', 'Gold'),
(1008, 'Noel@accenture.com', 'Silver'),
(1009, 'Killo@accenture.com', 'Platinum'),
(1010, 'Rupek@accenture.com', 'Gold'),
(1011, 'JAS@accenture.com', 'Silver'),
(1012, 'MSD@accenture.com', 'Platinum');
commit;

DROP TABLE IF EXISTS `customerproductmgmtdb`.`product`;
CREATE TABLE  `customerproductmgmtdb`.`product` (
  `product_id` int(11) NOT NULL auto_increment,
  `price` double NOT NULL,
  `product_name` varchar(255) default NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY  (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DELETE from `product`;

INSERT INTO product (product_id,price,product_name,stock) VALUES
(1, 999,'Bag', 300   ),
(2, 8999,'Sennheiser Headphone', 150   ),
(3, 3999,'Fitbit', 400   ),
(4, 10400,'Desert Aircooler', 80   ),
(5, 2999,'Yoga mat', 250   ),
(6, 4500,'Laptop bag', 100   ),
(7, 21999,'Kindle Oasis', 45   ),
(8, 16500,'Digital Piano', 20   ),
(9, 6790,'Saregama Caravan', 50   );
commit;

