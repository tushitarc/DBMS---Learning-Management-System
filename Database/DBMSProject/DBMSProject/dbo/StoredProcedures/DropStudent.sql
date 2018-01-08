CREATE PROCEDURE [dropStudent] @StudentId INT, @CourseId INT, @InstructorId INT
AS
BEGIN
	DECLARE @CourseInstructor INT
	SELECT @CourseInstructor = InstructorID from Course WHERE ID = @CourseId
	IF @CourseInstructor <> @InstructorId
	BEGIN
		raiserror('Instructor is not teaching the course', 11, 1);
		RETURN
	END
	UPDATE Course
	SET StudentsEnrolled = StudentsEnrolled - 1
	WHERE Id = @CourseId

	DELETE FROM ENROLLMENT WHERE COURSE_ID = @CourseId AND Student_ID = @StudentId
END