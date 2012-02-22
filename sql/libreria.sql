# EMS MySQL Manager Pro 3.4.0.4
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : libreria


SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `libreria`;

CREATE DATABASE `libreria`
    CHARACTER SET 'latin1'
    COLLATE 'latin1_swedish_ci';

USE `libreria`;

#
# Structure for the `autore` table : 
#

DROP TABLE IF EXISTS `autore`;

CREATE TABLE `autore` (
  `codice` int(11) unsigned NOT NULL auto_increment,
  `nome` varchar(30) default NULL,
  PRIMARY KEY  (`codice`),
  UNIQUE KEY `codice` (`codice`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Structure for the `libro` table : 
#

DROP TABLE IF EXISTS `libro`;

CREATE TABLE `libro` (
  `ISBN` varchar(30) NOT NULL,
  `titolo` varchar(50) default NULL,
  `casaEditrice` varchar(30) default NULL,
  `prezzo` float(9,2) unsigned default NULL,
  PRIMARY KEY  (`ISBN`),
  UNIQUE KEY `ISBN` (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Structure for the `scrittura` table : 
#

DROP TABLE IF EXISTS `scrittura`;

CREATE TABLE `scrittura` (
  `libro` varchar(30) NOT NULL,
  `autore` int(11) unsigned NOT NULL,
  PRIMARY KEY  (`libro`,`autore`),
  KEY `libro` (`libro`),
  KEY `scrittore` (`autore`),
  CONSTRAINT `scrittura_ibfk_3` FOREIGN KEY (`libro`) REFERENCES `libro` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scrittura_ibfk_4` FOREIGN KEY (`autore`) REFERENCES `autore` (`codice`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

