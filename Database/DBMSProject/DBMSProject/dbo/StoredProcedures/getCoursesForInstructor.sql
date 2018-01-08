CREATE PROCEDURE getCoursesForInstructor @id INT
AS
BEGIN
	SELECT C.Id, C.[Name] AS CourseName, C.StartDate, C.EndDate, C.InstructorID, C.MaxEnrollment , C.StudentsEnrolled FROM Course C
	WHERE C.InstructorID = @id
END