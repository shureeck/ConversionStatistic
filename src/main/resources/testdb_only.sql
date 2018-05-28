-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Май 28 2018 г., 23:43
-- Версия сервера: 5.5.25
-- Версия PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `testdb`
--

DELIMITER $$
--
-- Процедуры
--
CREATE DEFINER=`gbuser`@`%` PROCEDURE `changeToTrue`(IN tablename VARCHAR(100), IN tab VARCHAR(45) )
BEGIN
	SET @q1 = CONCAT("update ", tablename," SET delete_object = true WHERE tab = '", tab, "';");
   PREPARE s1 FROM @q1;
   EXECUTE s1;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `actionitems_failed_objects`
--

CREATE TABLE IF NOT EXISTS `actionitems_failed_objects` (
  `objecttype` varchar(45) DEFAULT NULL,
  `objectname` varchar(100) DEFAULT NULL,
  `build` int(11) DEFAULT NULL,
  `COMMENT` varchar(100) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `delete_object` tinyint(1) DEFAULT '0',
  `test_list_number` int(11) DEFAULT NULL,
  `report` text,
  UNIQUE KEY `check_UNIQUE` (`objecttype`,`objectname`,`tab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Триггеры `actionitems_failed_objects`
--
DROP TRIGGER IF EXISTS `mytrigger_beforeinsert_actionitems`;
DELIMITER //
CREATE TRIGGER `mytrigger_beforeinsert_actionitems` BEFORE INSERT ON `actionitems_failed_objects`
 FOR EACH ROW begin
declare build_value INT;
declare comment_old varchar(45);
SET build_value = (SELECT build FROM `actionitems_failed_objects`  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab );
 SET comment_old = (SELECT comment FROM `actionitems_failed_objects`  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab) ;                          
IF (build_value IS NOT NULL) THEN
SET NEW.build=build_value;
END IF;
IF (comment_old IS NOT NULL) THEN
SET NEW.comment=comment_old;
END IF;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `action_items_general_statistic`
--

CREATE TABLE IF NOT EXISTS `action_items_general_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `check_UNIQUE` (`build`,`pair`,`category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=79 ;

-- --------------------------------------------------------

--
-- Структура таблицы `apply_by_categories`
--

CREATE TABLE IF NOT EXISTS `apply_by_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `passed` int(11) DEFAULT NULL,
  `failed` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `build_UNIQUE` (`build`,`category`,`pair`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=294 ;

-- --------------------------------------------------------

--
-- Структура таблицы `apply_failed_objects`
--

CREATE TABLE IF NOT EXISTS `apply_failed_objects` (
  `objecttype` varchar(45) DEFAULT NULL,
  `objectname` varchar(100) DEFAULT NULL,
  `build` int(11) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `delete_object` tinyint(1) DEFAULT '0',
  `test_list_number` int(11) DEFAULT NULL,
  `report` text,
  UNIQUE KEY `check_UNIQUE` (`objecttype`,`objectname`,`tab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Триггеры `apply_failed_objects`
--
DROP TRIGGER IF EXISTS `mytrigger_beforeinsert_apply`;
DELIMITER //
CREATE TRIGGER `mytrigger_beforeinsert_apply` BEFORE INSERT ON `apply_failed_objects`
 FOR EACH ROW begin
declare build_value INT;
declare comment_old varchar(45);
SET build_value = (SELECT build FROM apply_failed_objects  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab );
 SET comment_old = (SELECT comment FROM apply_failed_objects  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab) ;                          
IF (build_value IS NOT NULL) THEN
SET NEW.build=build_value;
END IF;
IF (comment_old IS NOT NULL) THEN
SET NEW.comment=comment_old;
END IF;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `apply_general_statistic`
--

CREATE TABLE IF NOT EXISTS `apply_general_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `check_UNIQUE` (`build`,`pair`,`category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=130 ;

-- --------------------------------------------------------

--
-- Структура таблицы `conversion_by_categories`
--

CREATE TABLE IF NOT EXISTS `conversion_by_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `passed` int(11) DEFAULT NULL,
  `failed` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `check_UNIQUE` (`build`,`pair`,`category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=266 ;

-- --------------------------------------------------------

--
-- Структура таблицы `conversion_failed_objects`
--

CREATE TABLE IF NOT EXISTS `conversion_failed_objects` (
  `objecttype` varchar(45) DEFAULT NULL,
  `objectname` varchar(100) DEFAULT NULL,
  `build` int(11) DEFAULT NULL,
  `COMMENT` varchar(100) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `delete_object` tinyint(1) DEFAULT '0',
  `test_list_number` int(11) DEFAULT NULL,
  `report` text,
  UNIQUE KEY `check_UNIQUE` (`objecttype`,`objectname`,`tab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Триггеры `conversion_failed_objects`
--
DROP TRIGGER IF EXISTS `mytrigger_beforeinsert_conversion`;
DELIMITER //
CREATE TRIGGER `mytrigger_beforeinsert_conversion` BEFORE INSERT ON `conversion_failed_objects`
 FOR EACH ROW begin
declare build_value INT;
declare comment_old varchar(45);
SET build_value = (SELECT build FROM conversion_failed_objects  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab );
 SET comment_old = (SELECT comment FROM conversion_failed_objects  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab) ;                          
IF (build_value IS NOT NULL) THEN
SET NEW.build=build_value;
END IF;
IF (comment_old IS NOT NULL) THEN
SET NEW.comment=comment_old;
END IF;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `conversion_general_statistic`
--

CREATE TABLE IF NOT EXISTS `conversion_general_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `check_UNIQUE` (`build`,`pair`,`category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=109 ;

-- --------------------------------------------------------

--
-- Структура таблицы `errors_by_categories`
--

CREATE TABLE IF NOT EXISTS `errors_by_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `failed` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `check_UNIQUE` (`build`,`pair`,`category`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=225 ;

-- --------------------------------------------------------

--
-- Структура таблицы `error_failed_objects`
--

CREATE TABLE IF NOT EXISTS `error_failed_objects` (
  `objecttype` varchar(45) DEFAULT NULL,
  `objectname` varchar(100) DEFAULT NULL,
  `build` int(11) DEFAULT NULL,
  `COMMENT` varchar(100) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `delete_object` tinyint(1) DEFAULT '0',
  `test_list_number` int(11) DEFAULT NULL,
  `report` text,
  UNIQUE KEY `check_UNIQUE` (`objecttype`,`objectname`,`tab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Триггеры `error_failed_objects`
--
DROP TRIGGER IF EXISTS `mytrigger_beforeinsert_errors`;
DELIMITER //
CREATE TRIGGER `mytrigger_beforeinsert_errors` BEFORE INSERT ON `error_failed_objects`
 FOR EACH ROW begin
declare build_value INT;
declare comment_old varchar(45);
SET build_value = (SELECT build FROM error_failed_objects  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab );
 SET comment_old = (SELECT comment FROM error_failed_objects  
							WHERE objectname = new.objectname 
                            AND objecttype = new.objecttype AND tab=new.tab) ;                          
IF (build_value IS NOT NULL) THEN
SET NEW.build=build_value;
END IF;
IF (comment_old IS NOT NULL) THEN
SET NEW.comment=comment_old;
END IF;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `statistic_by_source`
--

CREATE TABLE IF NOT EXISTS `statistic_by_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build` int(11) DEFAULT NULL,
  `release_build` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `pair` varchar(45) DEFAULT NULL,
  `tab` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `build_UNIQUE` (`build`,`category`,`pair`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=387 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
