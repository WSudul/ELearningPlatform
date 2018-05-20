-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 19, 2018 at 12:47 AM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.11

--
-- Database: `elearning`
--


-- Dumping data for table `course`
--

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `password`, `active`) VALUES
  (1, 'jo@gmail.com', 'Jan', 'Nowak', 'asdfghj', 0),
  (2, 'jo23@gmail.com', 'Wol', 'Jlol', 'nopasswrodsz', 1);


INSERT INTO `course` (`id`, `description`, `name`) VALUES
  (1, 'Long text descrfiption course 1', 'Course 1'),
  (2, 'Long text descrfiption course 2', 'Course 2');

-- --------------------------------------------------------
--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`id`, `name`) VALUES
  (1, 'Tag1'),
  (2, 'Tag2'),
  (3, 'Tag3'),
  (4, 'Tag4'),
  (5, 'Tag5');

--------------------------------------------------------


--
-- Dumping data for table `course_tags`
--

INSERT INTO `course_tags` (`tag_id`, `course_id`) VALUES
  (1, 1),
  (2, 1),
  (2, 2),
  (3, 2);

-- --------------------------------------------------------

--
-- Dumping data for table `lesson`
--

INSERT INTO `lesson` (`id_lesson`, `content`, `name`, `subject_id`) VALUES
  (1, 'lessson content 1 for coruse 1', 'Lesson 1', 1),
  (2, 'lessson content 2 for coruse 1', 'Lesson 2', 1);

-- --------------------------------------------------------

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`id_quiz`, `name`, `subject_id`) VALUES
  (1, 'Quiz 1 for course 2 ', 2),
  (2, 'Quiz 2 for course 2 ', 2),
  (3, 'Quiz 1 for course 1', 1);

-- -


--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id_question`, `answer1`, `answer2`, `answer3`, `answer4`, `correct_answer`, `question`, `id_quiz`)
VALUES
  (1, 'A1', 'A2', 'A3', 'A4', 3, 'question 1 quiz 1', 1),
  (2, 'A11', 'A22', 'A33', 'A44', 3, 'questio n2', 1);

-- --------------------------------------------------------

--
-- Dumping data for table `usercourses`
--

INSERT INTO `usercourses` (`id`, `completed`, `date_completed`, `user_id`, `course_id`) VALUES
  (1, 0, NULL, 1, 1),
  (2, 0, NULL, 1, 2),
  (3, 0, NULL, 2, 1);

-------------------------------------------------------


-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `text`, `date_added`, `score`, `usercourses_id`) VALUES
  (1, 'review 9/10 usercourses 1', '2018-05-17', 9, 1),
  (2, 'review 5/10 usercourses 3', '2018-05-17', 5, 3),
  (3, 'review 9/10 usercourses 1', '2018-05-17', 9, 1),
  (4, 'review 5/10 usercourses 3', '2018-05-17', 5, 3);

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id`, `description`, `role`) VALUES
  (1, 'role user 1', 'USER_ROLE'),
  (2, 'role user 2 baadad', 'USER_ROLE_extra');

-- --------------------------------------------------------

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `roles_id`) VALUES
  (1, 1),
  (1, 2),
  (2, 2);


