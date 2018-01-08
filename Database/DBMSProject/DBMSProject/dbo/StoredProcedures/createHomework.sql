/****** Object:  StoredProcedure [dbo].[createHomework]    Script Date: 11/19/2017 6:01:59 PM ******/
DROP PROCEDURE [dbo].[createHomework]
GO

/****** Object:  StoredProcedure [dbo].[createHomework]    Script Date: 11/19/2017 6:01:59 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[createHomework]
(
    -- Add the parameters for the stored procedure here
    @name varchar(200),
	@courseID int,
	@numOfQuestions int,
	@maxTries int,
	@startDate date,
	@endDate date,
	@pointsIfRight int,
	@pointsIfWrong int,
	@homeworkType varchar(20),
	@topicID int,
	@scoreSelect varchar(20)
)
AS
BEGIN
	
	DECLARE @CourseEndDate DATETIME
	SELECT @CourseEndDate = ENDDATE FROM Course WHERE ID = @courseID
	IF @CourseEndDate < GETDATE()
	raiserror('Cannot create a homework for a past course', 11, 1);

    -- Insert statements for procedure here
    INSERT INTO [dbo].[HOMEWORK]
           ([NAME]
           ,[COURSE_ID]
           ,[NUM_OF_QUESTIONS]
           ,[STARTDATE]
           ,[ENDDATE]
           ,[MAX_TRIES]
           ,[POINTS_IF_CORRECT]
           ,[POINTS_IF_WRONG]
		   ,[HOMEWORK_TYPE]
		   ,[TOPIC_ID]
		   ,[ScoreSelection])
     VALUES
           (@name
           ,@courseID
           ,@numOfQuestions
           ,@startDate
           ,@endDate
           ,@maxTries
           ,@pointsIfRight
           ,@pointsIfWrong
		   ,@homeworkType
		   ,@topicID
		   ,@scoreSelect)
END
GO


