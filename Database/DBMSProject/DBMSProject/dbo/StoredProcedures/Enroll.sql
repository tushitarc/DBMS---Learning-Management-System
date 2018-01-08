CREATE PROCEDURE [dbo].[enroll] @StudentId INT, @CourseId INT, @InstructorId INT
AS
BEGIN
	DECLARE @CourseInstructor INT
	SELECT @CourseInstructor = InstructorID from Course WHERE ID = @CourseId
	IF @CourseInstructor <> @InstructorId
	BEGIN
		raiserror('Instructor is not teaching the course', 11, 1);
		RETURN
	END
	DECLARE @EndDate DATETIME
	SELECT @EndDate = ENDDATE FROM Course WHERE ID = @CourseId
	IF @EndDate < GETDATE()
	BEGIN
		raiserror('Cannot enroll student in a past course', 11, 1);
		RETURN
	END
	INSERT INTO ENROLLMENT VALUES (@CourseId, @StudentId)
	SELECT 1
END