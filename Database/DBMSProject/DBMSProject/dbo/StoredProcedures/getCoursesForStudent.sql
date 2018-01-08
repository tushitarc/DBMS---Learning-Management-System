CREATE PROCEDURE getAllCoursesForStudent @id INT
AS
BEGIN
	SELECT C.Id, C.[Name] AS CourseName, C.StartDate, C.EndDate, C.InstructorID, C.MaxEnrollment , C.StudentsEnrolled FROM Course C,
	ENROLLMENT E
	WHERE C.Id = E.COURSE_ID
	AND E.STUDENT_ID = @id
END