DROP DATABASE IF EXISTS dockerdemo;
CREATE DATABASE dockerdemo;
GRANT ALL PRIVILEGES ON dockerdemo.* TO 'demo'@'%' IDENTIFIED BY 'demo';
GRANT ALL PRIVILEGES ON dockerdemo.* TO 'demo'@'localhost' IDENTIFIED BY 'demo';
GRANT ALL PRIVILEGES ON dockerdemo.* TO 'demo'@'b2k-mysql-db' IDENTIFIED BY 'demo';
GRANT ALL PRIVILEGES ON dockerdemo.* TO 'demo'@'springboot-mysql-app' IDENTIFIED BY 'demo';
USE dockerdemo;
CREATE TABLE `b2k_department` (
    `id` binary(16) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` varchar(31) NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` varchar(31) NOT NULL,
    `version` int(11) DEFAULT NULL,
    `name` varchar(31) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_B2K_DEPARTMENT` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
;
INSERT INTO `b2k_department` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 1, 'ADMIN');
INSERT INTO `b2k_department` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 1, 'IT');
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
    `dept_id` binary(16) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_B2K_EMP_EMAIL` (`email_id`),
    CONSTRAINT `FK_B2K_EMP_DEPT` FOREIGN KEY (`dept_id`) REFERENCES `b2k_department` (`id`)
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1
;
INSERT INTO `b2k_user` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 1, 1,null, 'admin', 'admin7', 'ADMIN', null, 'admin' );
CREATE TABLE `b2k_category` (
    `id` binary(16) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` varchar(31) NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` varchar(31) NOT NULL,
    `version` int(11) DEFAULT NULL,
    `name` varchar(128) NOT NULL,
    `dept_level` int(6) DEFAULT NULL,
    `parent_id` binary(16) DEFAULT NULL,
    PRIMARY KEY (`id`),
    -- KEY `key_parent_id` (`parent_id`),
    UNIQUE KEY `UK_b2k_category` (`name`),
    CONSTRAINT `fk_b2k_category` FOREIGN KEY (`parent_id`) REFERENCES `b2k_category` (`id`)
);
INSERT INTO `b2k_category` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 0, 'Electronics', 0, null);
INSERT INTO `b2k_category` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 0,
                                    'Mobile phones', 1, (select id from `b2k_category` c where name='Electronics'));
INSERT INTO `b2k_category` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 0,
                                    'Washing machines', 1, (select id from `b2k_category`c  where name='Electronics'));
INSERT INTO `b2k_category` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 0,
                                    'iPhone', 2, (select id from `b2k_category` c where name='Mobile phones'));
INSERT INTO `b2k_category` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 0,
                                    'Micromax', 2, (select id from `b2k_category` c where name='Mobile phones'));
INSERT INTO `b2k_category` values ( unhex(replace(UUID(), '-', '')), current_timestamp , 'SYSTEM', current_timestamp , 'SYSTEM', 0,
                                    'LG', 2, (select id from `b2k_category` c where name='Washing machines'));



