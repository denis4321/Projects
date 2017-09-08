-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema terminator
--

CREATE DATABASE IF NOT EXISTS terminator;
USE terminator;

--
-- Definition of table `exercises`
--

DROP TABLE IF EXISTS `exercises`;
CREATE TABLE `exercises` (
  `ID` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `usr` int(11) NOT NULL,
  PRIMARY KEY  (`ID`,`usr`),
  UNIQUE KEY `uni_name` (`name`,`usr`),
  KEY `fk_exercises_users1` (`usr`),
  CONSTRAINT `fk_exercises_users1` FOREIGN KEY (`usr`) REFERENCES `users` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `exercises`
--

/*!40000 ALTER TABLE `exercises` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercises` ENABLE KEYS */;


--
-- Definition of table `exercises_details`
--

DROP TABLE IF EXISTS `exercises_details`;
CREATE TABLE `exercises_details` (
  `ID` int(11) NOT NULL auto_increment,
  `rest` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `fk_exercises_details_exercises1` (`ID`),
  CONSTRAINT `fk_exercises_details_exercises1` FOREIGN KEY (`ID`) REFERENCES `exercises` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `exercises_details`
--

/*!40000 ALTER TABLE `exercises_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercises_details` ENABLE KEYS */;


--
-- Definition of table `sets`
--

DROP TABLE IF EXISTS `sets`;
CREATE TABLE `sets` (
  `ID` int(11) NOT NULL auto_increment,
  `number` int(11) NOT NULL,
  `weight` double default NULL,
  `repeats` int(11) default NULL,
  `exercise` int(11) NOT NULL,
  PRIMARY KEY  (`ID`,`exercise`),
  KEY `fk_sets_exercises1` (`exercise`),
  CONSTRAINT `fk_sets_exercises1` FOREIGN KEY (`exercise`) REFERENCES `exercises` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sets`
--

/*!40000 ALTER TABLE `sets` DISABLE KEYS */;
/*!40000 ALTER TABLE `sets` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL auto_increment,
  `login` varchar(45) NOT NULL,
  `pass` varchar(32) NOT NULL,
  `role` varchar(45) NOT NULL default 'arny',
  `email` varchar(45) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `uni_login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`ID`,`login`,`pass`,`role`,`email`) VALUES 
 (2,'conarh','96e79218965eb72c92a549dd5a330112','arny','conarh@none.com'),
 (3,'user','96e79218965eb72c92a549dd5a330112','arny','user@user.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;


--
-- Definition of table `workouts`
--

DROP TABLE IF EXISTS `workouts`;
CREATE TABLE `workouts` (
  `ID` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `number` int(11) NOT NULL,
  `usr` int(11) NOT NULL,
  PRIMARY KEY  (`ID`,`usr`),
  UNIQUE KEY `name_uni` (`name`,`usr`),
  UNIQUE KEY `num_uni` (`number`,`usr`),
  KEY `fk_workouts_users` (`usr`),
  CONSTRAINT `fk_workouts_users` FOREIGN KEY (`usr`) REFERENCES `users` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `workouts`
--

/*!40000 ALTER TABLE `workouts` DISABLE KEYS */;
/*!40000 ALTER TABLE `workouts` ENABLE KEYS */;


--
-- Definition of table `workouts_exercises`
--

DROP TABLE IF EXISTS `workouts_exercises`;
CREATE TABLE `workouts_exercises` (
  `ID` int(11) NOT NULL auto_increment,
  `workout` int(11) NOT NULL,
  `exercise` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY  (`ID`,`workout`,`exercise`),
  UNIQUE KEY `uni_exercise` (`workout`,`exercise`,`number`),
  KEY `fk_workouts_exercises_workouts1` (`workout`),
  KEY `fk_workouts_exercises_exercises1` (`exercise`),
  CONSTRAINT `fk_workouts_exercises_workouts1` FOREIGN KEY (`workout`) REFERENCES `workouts` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_workouts_exercises_exercises1` FOREIGN KEY (`exercise`) REFERENCES `exercises` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `workouts_exercises`
--

/*!40000 ALTER TABLE `workouts_exercises` DISABLE KEYS */;
/*!40000 ALTER TABLE `workouts_exercises` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
