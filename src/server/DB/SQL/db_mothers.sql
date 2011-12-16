-- phpMyAdmin SQL Dump
-- version 3.4.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 16, 2011 at 09:38 PM
-- Server version: 5.1.54
-- PHP Version: 5.3.5-1ubuntu7.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `db_mothers`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(100) CHARACTER SET latin1 NOT NULL,
  `passwd` varchar(100) CHARACTER SET latin1 NOT NULL,
  `favor0` float NOT NULL,
  `favor1` float NOT NULL,
  `favor2` float NOT NULL,
  `favor3` float NOT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `apts`
--

CREATE TABLE IF NOT EXISTS `apts` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ',
  `address` varchar(200) COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ',
  `favor0` float NOT NULL DEFAULT '0',
  `favor1` float NOT NULL DEFAULT '0',
  `favor2` float NOT NULL DEFAULT '0',
  `favor3` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`idx`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `attentions`
--

CREATE TABLE IF NOT EXISTS `attentions` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lat` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lng` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;
