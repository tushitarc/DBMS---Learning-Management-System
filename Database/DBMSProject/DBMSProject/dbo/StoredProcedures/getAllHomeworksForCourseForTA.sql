CREATE PROC GetAllHomeworksForCourseForTA @CourseID INT, @TaId INT
AS
BEGIN
	--Throw error if ta is not assisting this course
	IF NOT EXISTS (SELECT 1 FROM COURSE_ASSISTANCE WHERE TA_ID = @TaID AND COURSE_ID = @CourseId)
	BEGIN
		raiserror('TA is not assisting this course', 11, 1);
		RETURN
	END

	CREATE TABLE #StudentHomework(HWID INT, StudentID INT, SCORE FLOAT)

	Select ID
	Into   #Homeworks
	From   HOMEWORK
	WHERE COURSE_ID = @CourseId

	Declare @Id int

	While (Select Count(*) From #Homeworks) > 0
	Begin

		Select Top 1 @Id = Id From #Homeworks

		SELECT Student_ID
		INTO #Student_Ids
		FROM ENROLLMENT
		WHERE COURSE_ID = @CourseId

		DECLARE @StudentID INT
		While (Select Count(*) From #Student_Ids) > 0
		Begin
			Select Top 1 @StudentID = Student_ID From #Student_Ids
				DECLARE @SCORE FLOAT
				EXEC @SCORE = GetScoreForHomework @StudentId, @Id
				INSERT INTO #StudentHomework VALUES(@Id, @StudentID, @SCORE)
			Delete #Student_Ids Where Student_ID = @StudentID
		END
		DROP TABLE #Student_Ids
		Delete #Homeworks Where Id = @Id

	End

	SELECT * FROM #StudentHomework
	DROP TABLE #Homeworks
	DROP TABLE #StudentHomework

END