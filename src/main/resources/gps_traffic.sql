DROP DATABASE IF EXISTS gps_traffic;
CREATE DATABASE IF NOT EXISTS gps_traffic;
use gps_traffic;   
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
   `id` int NOT NULL auto_increment,
   `username` varchar(255) NOT NULL, 
   `password` varchar(255) NOT NULL,
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `gps`;
CREATE TABLE IF NOT EXISTS `gps` (
	`id` int NOT NULL auto_increment,
    `name` varchar(255),
    `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id` int NOT NULL,
    `total2d_dist` DECIMAL(20,5) NULL,
    `total3d_dist` DECIMAL(20,5) NULL,
    `total_speed` DECIMAL(20,5) NULL,
    `moving_speed` DECIMAL(20,5) NULL,
    `max_speed` DECIMAL(20,5) NULL,
    `total_time` varchar(255) NULL,
    `time_stopped` varchar(255) NULL,
    `time_moving` varchar(255) NULL,
    `start_time` varchar(255) NULL,
    `end_time` varchar(255) NULL,
    `url` varchar(255) NULL,
    `point_no` INT NULL,
    `avg_density` DECIMAL(20,5) NULL,
    `up_hill` DECIMAL(20,5) NULL,
    `down_hill` DECIMAL(20,5) NULL,
    `max_elevation` DECIMAL(20,5) NULL,
    `min_elevation` DECIMAL(20,5) NULL,
    PRIMARY KEY (`id`) ,
    INDEX `user_id_idx` (`user_id` ASC),
	CONSTRAINT `fk_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `jwtblacklist`;
CREATE TABLE IF NOT EXISTS `jwtblacklist` (
    `token` varchar(255),
    PRIMARY KEY (`token`)
)
ENGINE=InnoDB;

   