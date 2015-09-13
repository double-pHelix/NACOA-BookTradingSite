-- phpMyAdmin SQL Dump
-- The is SQL code that should be run by JDBC
-- How to run SQL Scripts in JDBC: http://stackoverflow.com/questions/1044194/running-a-sql-script-using-mysql-with-jdbc
-- It will link to this required code: https://github.com/BenoitDuffez/ScriptRunner

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `NACOADatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE IF NOT EXISTS `admins` (
  `id` int(11) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` int(128) NOT NULL,
  `email` int(52) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) NOT NULL,
  `title` varchar(52) NOT NULL,
  `author` varchar(52) NOT NULL,
  `picture` varchar(128) NOT NULL,
  `price` float NOT NULL,
  `publisher` varchar(52) NOT NULL,
  `dateofpublication` date NOT NULL,
  `pages` int(11) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `genre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(128) NOT NULL,
  `email` varchar(52) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `dob` date NOT NULL,
  `address` text NOT NULL,
  `creditcarddetails` text NOT NULL,
  `is_halted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `firstname`, `lastname`, `nickname`, `dob`, `address`, `creditcarddetails`, `is_halted`) VALUES
(1, 'firstuser', 'password', 'fake@email.com', 'Kevin', 'Rondo', 'Rondo', '2015-09-23', 'Fake House ', 'Fake', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_customer_books`
--

CREATE TABLE IF NOT EXISTS `user_customer_books` (
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `is_sold` tinyint(1) NOT NULL DEFAULT '0',
  `dateofupload` date NOT NULL,
  `dateofsale` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_history`
--

CREATE TABLE IF NOT EXISTS `user_history` (
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `action_type` int(11) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_seller_books`
--

CREATE TABLE IF NOT EXISTS `user_seller_books` (
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `is_sold` tinyint(1) NOT NULL DEFAULT '0',
  `dateofupload` date NOT NULL,
  `dateofsale` date NOT NULL,
  `is_paused` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_customer_books`
--
ALTER TABLE `user_customer_books`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `user_seller_books`
--
ALTER TABLE `user_seller_books`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_customer_books`
--
ALTER TABLE `user_customer_books`
  ADD CONSTRAINT `user_customer_books_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `user_customer_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

--
-- Constraints for table `user_seller_books`
--
ALTER TABLE `user_seller_books`
  ADD CONSTRAINT `user_seller_books_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `user_seller_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
