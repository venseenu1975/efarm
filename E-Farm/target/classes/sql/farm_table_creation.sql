CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(4500) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `lat` decimal(20,10) DEFAULT NULL,
  `lon` decimal(20,10) DEFAULT NULL,
  `uAliasName` varchar(45) DEFAULT NULL,
  `uPass` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

---------------------------------------------------------------------
CREATE TABLE `category_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(45) DEFAULT NULL,
  `cat_desc` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


1	Dairy	Milk and related products
2	Vegetables	Vegetables
3	Fruits	Fruits
----------------------------------------------------------------
CREATE TABLE `product_inv` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) DEFAULT NULL,
  `product` varchar(45) DEFAULT NULL,
  `product_img` blob,
  `product_desc` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

1	1	Milk	...	Milk
2	1	Ghee	...	Ghee
3	1	Butter		Butter
4	1	Paneer		Paneer
5	1	Cheese		Cheese	
6	1	Curd		curd		
7	2	Tomato		tomato
8	2	Ladies Finger		ladies finger		
9	2	Curry Leaves		
10	2	Drumstick		
11	2	Brinjal		
12	2	Coconut		
13	3	Guava		
14	3	Pome		
15	3	Papaya		
16	3	Banana		
17	2	Drumstick Leaves		
18	2	Plantain		
----------------------------------------------------------------
CREATE TABLE `seller_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_id` int(11) DEFAULT NULL,
  `prod_desc` varchar(45) DEFAULT NULL,
  `prod_img` longblob,
  `prod_delivery_mode` varchar(45) DEFAULT NULL,
  `seller_id` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `prod_quantity` int(11) DEFAULT NULL,
  `product_availabilty` date DEFAULT NULL,
  `active` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
