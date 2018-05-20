-- Database: `elearning`
--

-- --------------------------------------------------------

--
-- Table structure for table `access`
--

CREATE TABLE `access` (
  `idaccess`  INTEGER NOT NULL,
  `roleid`    INTEGER DEFAULT NULL,
  `course_id` INTEGER DEFAULT NULL,
  `user_id`   INTEGER DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id`          INTEGER      NOT NULL,
  `description` LONGTEXT     NOT NULL,
  `name`        VARCHAR(255) NOT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `coursegrade`
--

CREATE TABLE `coursegrade` (
  `idcoursegrade` INTEGER NOT NULL,
  `grade`         INT(11) DEFAULT NULL,
  `userid`        INTEGER DEFAULT NULL,
  `subject_id`    INTEGER DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `course_tags`
--

CREATE TABLE `course_tags` (
  `tag_id`    INT(11) NOT NULL,
  `course_id` INTEGER NOT NULL
);

--
-- Dumping data for table `course_tags`
--

-- --------------------------------------------------------

--
-- Table structure for table `lesson`
--

CREATE TABLE `lesson` (
  `id_lesson`  INTEGER      NOT NULL,
  `content`    LONGTEXT     NOT NULL,
  `name`       VARCHAR(255) NOT NULL,
  `subject_id` INTEGER DEFAULT NULL
);

--
-- Dumping data for table `lesson`
--

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `idmessage` INTEGER      NOT NULL,
  `content`   LONGTEXT     NOT NULL,
  `fromwho`   VARCHAR(255) DEFAULT NULL,
  `title`     VARCHAR(255) NOT NULL,
  `towho`     VARCHAR(255) DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `id_question`    INTEGER      NOT NULL,
  `answer1`        VARCHAR(255) NOT NULL,
  `answer2`        VARCHAR(255) NOT NULL,
  `answer3`        VARCHAR(255) NOT NULL,
  `answer4`        VARCHAR(255) NOT NULL,
  `correct_answer` INTEGER DEFAULT NULL,
  `question`       VARCHAR(255) NOT NULL,
  `id_quiz`        INTEGER DEFAULT NULL
);

--
-- Dumping data for table `question`
--

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `id_quiz`    INTEGER      NOT NULL,
  `name`       VARCHAR(255) NOT NULL,
  `subject_id` INTEGER DEFAULT NULL
);

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`id_quiz`, `name`, `subject_id`) VALUES
  (1, 'Quiz 1 for course 2 ', 2),
  (2, 'Quiz 2 for course 2 ', 2),
  (3, 'Quiz 1 for course 1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `requestaccess`
--

CREATE TABLE `requestaccess` (
  `idaccess`  INTEGER NOT NULL,
  `roleid`    INTEGER DEFAULT NULL,
  `subjectid` INTEGER DEFAULT NULL,
  `userid`    INTEGER DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id`             INT(11) NOT NULL,
  `text`           TEXT,
  `date_added`     DATE    NOT NULL,
  `score`          TINYINT(4) DEFAULT NULL,
  `usercourses_id` INT(11) NOT NULL
);

--
-- Dumping data for table `review`
--
-- --------------------------------------------------------

--
-- Table structure for table `studentgrade`
--

CREATE TABLE `studentgrade` (
  `idstudentgrade` INTEGER NOT NULL,
  `comment`        VARCHAR(255) DEFAULT NULL,
  `grade`          DOUBLE       DEFAULT NULL,
  `userid`         INTEGER      DEFAULT NULL,
  `subject_id`     INTEGER      DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `id`   INT(11)      NOT NULL,
  `name` VARCHAR(255) NOT NULL
);

--
-- Dumping data for table `tag`
--

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id`         INTEGER      NOT NULL,
  `email`      VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255)          DEFAULT NULL,
  `last_name`  VARCHAR(255)          DEFAULT NULL,
  `password`   VARCHAR(255) NOT NULL,
  `active`     TINYINT(1)   NOT NULL DEFAULT '1'
);

--
-- Dumping data for table `user`
--

-- --------------------------------------------------------

--
-- Table structure for table `usercourses`
--

CREATE TABLE `usercourses` (
  `id`             INT(11)    NOT NULL,
  `completed`      TINYINT(1) NOT NULL DEFAULT '0',
  `date_completed` TIMESTAMP  NULL     DEFAULT NULL,
  `user_id`        INTEGER    NOT NULL,
  `course_id`      INTEGER    NOT NULL
);

--
-- Dumping data for table `usercourses`
--

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `id`          INTEGER NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `role`        VARCHAR(255) DEFAULT NULL
);

--
-- Dumping data for table `user_role`
--

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id`  INTEGER NOT NULL,
  `roles_id` INTEGER NOT NULL
);

--
-- Dumping data for table `user_roles`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access`
--
ALTER TABLE access
  ADD PRIMARY KEY (idaccess),
  ADD UNIQUE KEY user_id (user_id),
  ADD KEY course_id (course_id);

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
  ADD PRIMARY KEY (`tag_id`, `course_id`),
  ADD UNIQUE KEY `tag_id_3` (`tag_id`, `course_id`),
  ADD KEY `tag_id` (`tag_id`),
  ADD KEY `tag_id_2` (`tag_id`, `course_id`),
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
  ADD KEY `fk_id_quiz` (`id_quiz`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id_quiz`),
  ADD KEY `fk_subject_id` (`subject_id`);

--
-- Indexes for table `requestaccess`
--
ALTER TABLE `requestaccess`
  ADD PRIMARY KEY (`idaccess`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `date_added` (`date_added`),
  ADD KEY `usercourses_id` (`usercourses_id`);

--
-- Indexes for table `studentgrade`
--
ALTER TABLE `studentgrade`
  ADD PRIMARY KEY (`idstudentgrade`),
  ADD KEY `fk_subject_id` (`subject_id`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `usercourses`
--
ALTER TABLE `usercourses`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`, `course_id`),
  ADD UNIQUE KEY `user_id_2` (`user_id`, `course_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `description` (`description`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`, `roles_id`),
  ADD KEY `FK5i6gd32hnpr2nyf5edlvl9nhw` (`roles_id`);


ALTER TABLE `access`
  MODIFY `idaccess` INTEGER NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` INTEGER NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 0;

--
-- AUTO_INCREMENT for table `coursegrade`
--
ALTER TABLE `coursegrade`
  MODIFY `idcoursegrade` INTEGER NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lesson`
--
ALTER TABLE `lesson`
  MODIFY `id_lesson` INTEGER NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 0;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `idmessage` INTEGER NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `id_question` INTEGER NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 0;

--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `id_quiz` INTEGER NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 0;

--
-- AUTO_INCREMENT for table `requestaccess`
--
ALTER TABLE `requestaccess`
  MODIFY `idaccess` INTEGER NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 0;

--
-- AUTO_INCREMENT for table `studentgrade`
--
ALTER TABLE `studentgrade`
  MODIFY `idstudentgrade` INTEGER NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tag`
--
ALTER TABLE `tag`
  MODIFY `id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` INTEGER NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 3;

--
-- AUTO_INCREMENT for table `usercourses`
--
ALTER TABLE `usercourses`
  MODIFY `id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 4;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` INTEGER NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `access`
--
ALTER TABLE access
  ADD CONSTRAINT `access_ibfk_1` FOREIGN KEY (course_id) REFERENCES course (id),
  ADD CONSTRAINT `access_ibfk_2` FOREIGN KEY (user_id) REFERENCES user (id);

--
-- Constraints for table `coursegrade`
--
ALTER TABLE `coursegrade`
  ADD CONSTRAINT `fk_coursegrade_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

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
  ADD CONSTRAINT `fk_lesson_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `fk_question_quiz` FOREIGN KEY (`id_quiz`) REFERENCES `quiz` (`id_quiz`);

--
-- Constraints for table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `fk_quiz_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `course` (`id`);

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
  ADD CONSTRAINT `fk_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `fk_user_roles_roles_id` FOREIGN KEY (`roles_id`) REFERENCES `user_role` (`id`);

