CREATE PROC GetAllHomeworksForCourse @CourseID INT, @InstructorId INT
AS
BEGIN
	--Throw error if instructor is not teaching this course
	DECLARE @CourseInstructor INT
	SELECT @CourseInstructor = InstructorID from Course WHERE ID = @CourseId
	IF @CourseInstructor <> @InstructorId
	BEGIN
		raiserror('Instructor is not teaching the course', 11, 1);
		RETURN
	END

	SELECT [Id], [Name], [NUM_OF_QUESTIONS], STARTDATE, ENDDATE, MAX_TRIES, POINTS_IF_CORRECT, POINTS_IF_WRONG, HOMEWORK_TYPE, ScoreSelection FROM HOMEWORK H
	WHERE COURSE_ID = @CourseID
END