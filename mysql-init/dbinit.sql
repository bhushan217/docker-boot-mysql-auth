DROP DATABASE IF EXISTS dockerdemo;
CREATE DATABASE dockerdemo;
GRANT ALL PRIVILEGES ON dockerdemo.* TO 'demo'@'%' IDENTIFIED BY 'demo';
GRANT ALL PRIVILEGES ON dockerdemo.* TO 'demo'@'localhost' IDENTIFIED BY 'demo';
USE dockerdemo;
CREATE TABLE `b2k_employee` (
    `id` binary(16) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` varchar(31) NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` varchar(31) NOT NULL,
    `version` int(11) DEFAULT NULL,
    `email_id` varchar(63) NOT NULL,
    `first_name` varchar(63) NOT NULL,
    `last_name` varchar(63) NOT NULL,
    `rating` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
;
CREATE TABLE `b2k_user` (
    `id` binary(16) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` varchar(31) NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` varchar(31) NOT NULL,
    `version` int(11) DEFAULT NULL,
    `is_active` bit(1) NOT NULL default false,
    `last_login` datetime DEFAULT NULL,
    `name` varchar(31) NOT NULL,
    `password` varchar(255) DEFAULT NULL,
    `role` varchar(255) NOT NULL default 'USER',
    `token` varchar(100) DEFAULT NULL,
    `username` varchar(31) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_B2K_USERNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `b2k_user` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 1, 1,null, 'admin', 'admin7', 'ADMIN', null, 'admin' );
