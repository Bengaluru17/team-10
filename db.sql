-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 13, 2017 at 10:55 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `reaching_hands`
--

-- --------------------------------------------------------

--
-- Table structure for table `approval`
--

CREATE TABLE IF NOT EXISTS `approval` (
  `Items` varchar(20) NOT NULL,
  `Flag` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `approval`
--

INSERT INTO `approval` (`Items`, `Flag`) VALUES
('Vegetables', 'No'),
('Chairs', 'No');

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE IF NOT EXISTS `billing` (
  `Balance` int(11) NOT NULL,
  `spent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`Balance`, `spent`) VALUES
(5000, 1600);

-- --------------------------------------------------------

--
-- Table structure for table `children`
--

CREATE TABLE IF NOT EXISTS `children` (
  `children_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dateofjoin` date DEFAULT NULL,
  `school_type` varchar(30) NOT NULL,
  `school_name` varchar(30) NOT NULL,
  `standard` varchar(5) NOT NULL,
  `grade` varchar(2) NOT NULL,
  PRIMARY KEY (`children_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `children`
--

INSERT INTO `children` (`children_id`, `name`, `age`, `gender`, `dateofjoin`, `school_type`, `school_name`, `standard`, `grade`) VALUES
(1, 'Ram', 5, 'MALE', '0000-00-00', 'Home-School', 'Reaching Hands', 'I', 'A'),
(2, 'Ashwini', 6, 'FEMALE', '0000-00-00', 'Private', 'st.Mary', 'V', 'C'),
(3, 'Sham', 15, 'MALE', '0000-00-00', 'Private', 'Jain', 'IX', 'B'),
(4, 'Pragya', 7, 'FEMALE', '0000-00-00', 'Home-School', 'Reaching Hands', 'IV', 'A'),
(5, 'Varsha', 11, 'FEMALE', '0000-00-00', 'Home-School', 'Reaching Hands', 'VII', 'B'),
(6, 'Raghav', 16, 'MALE', '0000-00-00', 'Private', 'st.Miras', 'IX', 'C'),
(7, 'Amogh', 5, 'MALE', '0000-00-00', 'Home-School', 'Reaching Hands', 'II', 'A');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE IF NOT EXISTS `inventory` (
  `item_id` int(15) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(25) NOT NULL,
  `item_type` varchar(25) NOT NULL,
  `current_count` int(11) DEFAULT NULL,
  `threshold_count` int(11) DEFAULT NULL,
  `approval` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`item_id`, `item_name`, `item_type`, `current_count`, `threshold_count`, `approval`) VALUES
(1, 'Shoes', 'Boys', 25, 5, NULL),
(4, 'Clothes', 'Boys', 40, 10, NULL),
(6, 'NewBottles', 'Girls', 50, 15, NULL),
(7, 'NewChairs', 'Kitchen', 40, 5, NULL),
(8, 'Shirts', 'Boys', 29, 30, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `login_id` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`login_id`, `password`) VALUES
('admin', 'password'),
('skp', '123'),
('Staff1', 'password1'),
('Staff2', '123');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `dateofjoin` date NOT NULL,
  `role` varchar(40) NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `name`, `dateofjoin`, `role`) VALUES
(1, 'Sumuki', '0000-00-00', 'Cook'),
(2, 'Rajesh', '0000-00-00', 'Driver'),
(3, 'Ramya', '0000-00-00', 'Volunteer'),
(4, 'Arya', '0000-00-00', 'Volunteer'),
(5, 'Santosh', '0000-00-00', 'Accountancy');
