DROP DATABASE IF EXISTS gps_traffic;
CREATE DATABASE IF NOT EXISTS gps_traffic;
use gps_traffic;   
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
   `id` int NOT NULL auto_increment,
   `username` varchar(255), 
   `password` varchar(255),
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `gps_detail`;
CREATE TABLE IF NOT EXISTS `gps_detail` (
	`id` int NOT NULL auto_increment,
    `metadata` TEXT,
    `waypoint` TEXT,
    `track` TEXT,
    PRIMARY KEY (`id`) 
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `gps`;
CREATE TABLE IF NOT EXISTS `gps` (
	`id` int NOT NULL auto_increment,
    `name` varchar(255),
    `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id` int not null,
    `gps_detail_id` int not null,
    
    PRIMARY KEY (`id`) ,
    INDEX `user_id_idx` (`user_id` ASC),
    INDEX `gps_id_idx` (`gps_detail_id` ASC),
	CONSTRAINT `fk_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
 	CONSTRAINT `fk_gps_detail`
    FOREIGN KEY (`gps_detail_id`)
    REFERENCES `gps_detail` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE=InnoDB;



INSERT INTO `user`(`username`,`password`) VALUES ('test01', '$2a$10$NSw5cslZs9YeVPkmSEnPgeGY5Sx4UyLoNsdBpZ.AjpuhPuBUhu/6C');
INSERT INTO `user`(`username`,`password`) VALUES ('test02', '$2a$10$rbnLf4kp5tHwHF7mRAxkje3i6Aq/5gsYhQFINr1ROrUC8faxTmmwW');
   