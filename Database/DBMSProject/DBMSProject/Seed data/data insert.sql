SET IDENTITY_INSERT [dbo].[Instructor] ON
INSERT [dbo].[Instructor] ([Id], [Name], [UserId], [Password]) VALUES (1, N'Kemafor Ogan', N'kogan', N'kogan')
INSERT [dbo].[Instructor] ([Id], [Name], [UserId], [Password]) VALUES (2, N'Tushita', N'troycha', N'troycha')
SET IDENTITY_INSERT [dbo].[Instructor] OFF

SET IDENTITY_INSERT [dbo].[Course] ON 
INSERT [dbo].[Course] ([Id], [Name], [StartDate], [EndDate], [InstructorID], [MaxEnrollment], [StudentsEnrolled]) VALUES (1, N'DBMS', CAST(N'2017-10-02T00:00:00.000' AS DateTime), CAST(N'2017-12-31T00:00:00.000' AS DateTime), 1, 2, 0)
INSERT [dbo].[Course] ([Id], [Name], [StartDate], [EndDate], [InstructorID], [MaxEnrollment], [StudentsEnrolled]) VALUES (2, N'OODD', CAST(N'2017-10-02T00:00:00.000' AS DateTime), CAST(N'2017-12-31T00:00:00.000' AS DateTime), 2, 3, 3)
INSERT [dbo].[Course] ([Id], [Name], [StartDate], [EndDate], [InstructorID], [MaxEnrollment], [StudentsEnrolled]) VALUES (3, N'Algorithms', CAST(N'2017-10-02T00:00:00.000' AS DateTime), CAST(N'2017-12-31T00:00:00.000' AS DateTime), 2, 3, 0)
INSERT [dbo].[Course] ([Id], [Name], [StartDate], [EndDate], [InstructorID], [MaxEnrollment], [StudentsEnrolled]) VALUES (4, N'Kalu', CAST(N'3917-11-11T00:00:00.000' AS DateTime), CAST(N'3918-11-11T00:00:00.000' AS DateTime), 1, NULL, 0)
INSERT [dbo].[Course] ([Id], [Name], [StartDate], [EndDate], [InstructorID], [MaxEnrollment], [StudentsEnrolled]) VALUES (5, N'Kalu', CAST(N'2017-10-11T00:00:00.000' AS DateTime), CAST(N'2018-12-15T00:00:00.000' AS DateTime), 1, 5, 0)
INSERT [dbo].[Course] ([Id], [Name], [StartDate], [EndDate], [InstructorID], [MaxEnrollment], [StudentsEnrolled]) VALUES (6, N'ALDA', CAST(N'2017-10-31T00:00:00.000' AS DateTime), CAST(N'2018-02-15T00:00:00.000' AS DateTime), 1, 5, 0)
SET IDENTITY_INSERT [dbo].[Course] OFF

SET IDENTITY_INSERT [dbo].[Topic] ON 
INSERT [dbo].[Topic] ([id], [name]) VALUES (1, N'DBMS')
INSERT [dbo].[Topic] ([id], [name]) VALUES (2, N'DBMS')
INSERT [dbo].[Topic] ([id], [name]) VALUES (3, N'DBMS')
INSERT [dbo].[Topic] ([id], [name]) VALUES (4, N'DBMS')
INSERT [dbo].[Topic] ([id], [name]) VALUES (5, N'DBMS 5')
SET IDENTITY_INSERT [dbo].[Topic] OFF

SET IDENTITY_INSERT [dbo].[HOMEWORK] ON 
INSERT [dbo].[HOMEWORK] ([ID], [NAME], [COURSE_ID], [NUM_OF_QUESTIONS], [STARTDATE], [ENDDATE], [MAX_TRIES], [POINTS_IF_CORRECT], [POINTS_IF_WRONG], [HOMEWORK_TYPE], [TOPIC_ID]) VALUES (4, N'TEST', 1, 5, CAST(N'2018-02-11' AS Date), CAST(N'2018-03-11' AS Date), N'2', 3, 1, N'STANDARD', 1)
SET IDENTITY_INSERT [dbo].[HOMEWORK] OFF

SET IDENTITY_INSERT [dbo].[Question] ON 
INSERT [dbo].[Question] ([Id], [Text], [DifficultyLevel], [Hint], [Explanation], [NumberOfSets]) VALUES (27, N'Question 1 Param1=?, Param2=?', 5, N'Hint 1', N'Expl 1', 2)
INSERT [dbo].[Question] ([Id], [Text], [DifficultyLevel], [Hint], [Explanation], [NumberOfSets]) VALUES (28, N'Question 2 Param1=?, Param2=?', 5, N'Hint 2', N'Expl 2', 2)
INSERT [dbo].[Question] ([Id], [Text], [DifficultyLevel], [Hint], [Explanation], [NumberOfSets]) VALUES (29, N'quest 2', 1, N'hint 3', N'exp ', 2)
SET IDENTITY_INSERT [dbo].[Question] OFF

SET IDENTITY_INSERT [dbo].[Student] ON 
INSERT [dbo].[Student] ([Id], [Name], [UserId], [Password], [Level]) VALUES (3, N'Akshay', N'anjain2', N'anjain2', N'GRAD')
INSERT [dbo].[Student] ([Id], [Name], [UserId], [Password], [Level]) VALUES (4, N'Shreyas', N'sszagade', N'sszagade', N'GRAD')
INSERT [dbo].[Student] ([Id], [Name], [UserId], [Password], [Level]) VALUES (6, N'Dummy', N'dummy', N'dummy', N'UNDERGRAD')
SET IDENTITY_INSERT [dbo].[Student] OFF

INSERT [dbo].[ENROLLMENT] ([COURSE_ID], [STUDENT_ID]) VALUES (2, 3)
INSERT [dbo].[ENROLLMENT] ([COURSE_ID], [STUDENT_ID]) VALUES (2, 4)
INSERT [dbo].[ENROLLMENT] ([COURSE_ID], [STUDENT_ID]) VALUES (2, 6)

SET IDENTITY_INSERT [dbo].[TeachingAssistant] ON 
INSERT [dbo].[TeachingAssistant] ([Id], [Name], [UserId], [Password]) VALUES (1, N'Himani', N'hhimani', N'hhimani')
INSERT [dbo].[TeachingAssistant] ([Id], [Name], [UserId], [Password]) VALUES (2, N'Arjun', N'asharma', N'asharma')
SET IDENTITY_INSERT [dbo].[TeachingAssistant] OFF

INSERT [dbo].[COURSE_ASSISTANCE] ([COURSE_ID], [TA_ID]) VALUES (1, 2)
INSERT [dbo].[COURSE_ASSISTANCE] ([COURSE_ID], [TA_ID]) VALUES (2, 2)
INSERT [dbo].[TopicQuestionMapping] ([topic_id], [question_id]) VALUES (1, 27)
INSERT [dbo].[TopicQuestionMapping] ([topic_id], [question_id]) VALUES (1, 28)
INSERT [dbo].[TopicQuestionMapping] ([topic_id], [question_id]) VALUES (NULL, NULL)


INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (27, 1, 1, N'Param 1')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (27, 1, 2, N'Param 2')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (27, 2, 1, N'Param 1 Set 2')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (27, 2, 2, N'Param 2 Set 2')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (28, 1, 1, N'ParamQ2P1')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (28, 1, 2, N'Param2P2')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (28, 2, 1, N'ParamQ2P1S2')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (28, 2, 2, N'ParamQ2P2S2')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (28, 3, 1, N'P3')
INSERT [dbo].[PARAMETER] ([QuestionId], [SetId], [ID], [VALUE]) VALUES (28, 3, 2, N'P4')

SET IDENTITY_INSERT [dbo].[OPTION] ON
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (2, 27, 1, N'Opt 1', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (3, 27, 1, N'Opt 2', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (4, 27, 1, N'Opt 3', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (5, 27, 1, N'Opt 4', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (6, 27, 1, N'Opt 5', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (7, 27, 1, N'Opt 6', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (8, 27, 2, N'Opt 1', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (9, 27, 2, N'Opt 2', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (10, 27, 2, N'Opt 3', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (11, 27, 2, N'Opt 4', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (12, 28, 1, N'O 1', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (13, 28, 1, N'O 2', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (14, 28, 1, N'O 3', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (15, 28, 1, N'O 4', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (16, 28, 1, N'O 5', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (17, 28, 1, N'O 6', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (18, 28, 1, N'O 7', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (19, 28, 1, N'O 8', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (20, 28, 2, N'O1Q2S2', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (21, 28, 2, N'O2Q2S2', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (22, 28, 2, N'O2Q2S2', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (23, 28, 2, N'O4Q2S2', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (24, 28, 2, N'O5Q2S2', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (25, 28, 3, N'True', N'F')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (26, 28, 3, N'False', N'Y')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (27, 28, 3, N'Cannot say', N'N')
INSERT [dbo].[OPTION] ([ID], [QuestionId], [SetId], [Text], [Correct]) VALUES (28, 28, 3, N'True for some instances', N'N')
SET IDENTITY_INSERT [dbo].[OPTION] OFF