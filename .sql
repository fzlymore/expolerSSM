-- 新建一个用户customer表--
CREATE TABLE `customer` ( `id` BIGINT (20) NOT NULL AUTO_INCREMENT,
 `name` VARCHAR (255) DEFAULT NULL,
 `contact` VARCHAR (255) DEFAULT NULL,
 `telephone` VARCHAR (255) DEFAULT NULL,
 `email` VARCHAR (255) DEFAULT NULL,
 `remark` text,
 PRIMARY KEY (`id`) ) ENGINE = INNODB DEFAULT CHARSET=utf8;

insert into customer values ('1','customer1','Jack','13512345678','jack@gmqil.com',null);
insert into customer values ('2','customer2','Rose','13623456789','rose@gmqil.com',null);