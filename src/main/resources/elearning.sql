-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2018 at 12:08 AM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `elearning`
--

-- --------------------------------------------------------

--
-- Table structure for table `access`
--

CREATE TABLE `access` (
  `idaccess` bigint(20) NOT NULL,
  `roleid` bigint(20) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `description` longtext NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `coursegrade`
--

CREATE TABLE `coursegrade` (
  `idcoursegrade` bigint(20) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `course_tags`
--

CREATE TABLE `course_tags` (
  `tag_id` int(11) NOT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `lesson`
--

CREATE TABLE `lesson` (
  `id_lesson` bigint(20) NOT NULL,
  `content` longtext NOT NULL,
  `name` varchar(255) NOT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `idmessage` bigint(20) NOT NULL,
  `content` longtext NOT NULL,
  `fromwho` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `towho` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `id_question` bigint(20) NOT NULL,
  `answer1` varchar(255) NOT NULL,
  `answer2` varchar(255) NOT NULL,
  `answer3` varchar(255) NOT NULL,
  `answer4` varchar(255) NOT NULL,
  `correct_answer` bigint(20) DEFAULT NULL,
  `question` varchar(255) NOT NULL,
  `id_quiz` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `id_quiz` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `requestaccess`
--

CREATE TABLE `requestaccess` (
  `idaccess` bigint(20) NOT NULL,
  `roleid` bigint(20) DEFAULT NULL,
  `subjectid` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `text` text,
  `date_added` int(11) NOT NULL,
  `score` tinyint(4) DEFAULT NULL,
  `usercourses_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `studentgrade`
--

CREATE TABLE `studentgrade` (
  `idstudentgrade` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `grade` double DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `usercourses`
--

CREATE TABLE `usercourses` (
  `id` int(11) NOT NULL,
  `completed` tinyint(1) NOT NULL,
  `date_completed` timestamp NULL DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access`
--
ALTER TABLE `access`
  ADD PRIMARY KEY (`idaccess`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `coursegrade`
--
ALTER TABLE `coursegrade`
  ADD PRIMARY KEY (`idcoursegrade`),
  ADD KEY `FK8flpbw70tukwkmt6mtnqlemvy` (`subject_id`);

--
-- Indexes for table `course_tags`
--
ALTER TABLE `course_tags`
  ADD PRIMARY KEY (`tag_id`,`course_id`),
  ADD KEY `tag_id` (`tag_id`),
  ADD KEY `tag_id_2` (`tag_id`,`course_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `lesson`
--
ALTER TABLE `lesson`
  ADD PRIMARY KEY (`id_lesson`),
  ADD KEY `FK7ydr23s8y9j6lip5qrngoymx4` (`subject_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`idmessage`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id_question`),
  ADD KEY `FKvnp1u9psthblq5s0v4g49b73` (`id_quiz`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id_quiz`),
  ADD KEY `FK2bn6jb1u6wox8j2oh77k4vyp4` (`subject_id`);

--
-- Indexes for table `requestaccess`
--
ALTER TABLE `requestaccess`
  ADD PRIMARY KEY (`idaccess`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD KEY `usercourses_id` (`usercourses_id`),
  ADD KEY `date_added` (`date_added`);

--
-- Indexes for table `studentgrade`
--
ALTER TABLE `studentgrade`
  ADD PRIMARY KEY (`idstudentgrade`),
  ADD KEY `FKn9j4kakw5wilrtwk3m6r4topo` (`subject_id`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usercourses`
--
ALTER TABLE `usercourses`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`,`course_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`roles_id`),
  ADD KEY `FK5i6gd32hnpr2nyf5edlvl9nhw` (`roles_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `access`
--
ALTER TABLE `access`
  MODIFY `idaccess` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `coursegrade`
--
ALTER TABLE `coursegrade`
  MODIFY `idcoursegrade` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lesson`
--
ALTER TABLE `lesson`
  MODIFY `id_lesson` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `idmessage` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `id_question` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `id_quiz` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `requestaccess`
--
ALTER TABLE `requestaccess`
  MODIFY `idaccess` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `studentgrade`
--
ALTER TABLE `studentgrade`
  MODIFY `idstudentgrade` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `access`
--
ALTER TABLE `access`
  ADD CONSTRAINT `access_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `access_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `coursegrade`
--
ALTER TABLE `coursegrade`
  ADD CONSTRAINT `FK8flpbw70tukwkmt6mtnqlemvy` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `course_tags`
--
ALTER TABLE `course_tags`
  ADD CONSTRAINT `course_tags_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `course_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`);

--
-- Constraints for table `lesson`
--
ALTER TABLE `lesson`
  ADD CONSTRAINT `FK7ydr23s8y9j6lip5qrngoymx4` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FKvnp1u9psthblq5s0v4g49b73` FOREIGN KEY (`id_quiz`) REFERENCES `quiz` (`id_quiz`);

--
-- Constraints for table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `FK2bn6jb1u6wox8j2oh77k4vyp4` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`usercourses_id`) REFERENCES `usercourses` (`id`);

--
-- Constraints for table `studentgrade`
--
ALTER TABLE `studentgrade`
  ADD CONSTRAINT `FKn9j4kakw5wilrtwk3m6r4topo` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `usercourses`
--
ALTER TABLE `usercourses`
  ADD CONSTRAINT `usercourses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `usercourses_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK5i6gd32hnpr2nyf5edlvl9nhw` FOREIGN KEY (`roles_id`) REFERENCES `user_role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
