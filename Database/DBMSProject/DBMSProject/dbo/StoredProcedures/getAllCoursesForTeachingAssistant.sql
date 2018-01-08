CREATE PROCEDURE getCoursesForTeachingAssistant @id INT
AS
BEGIN
	SELECT C.Id, C.[Name] AS CourseName, C.StartDate, C.EndDate, C.InstructorID, C.MaxEnrollment , C.StudentsEnrolled FROM Course C,
	COURSE_ASSISTANCE CA
	WHERE C.Id = CA.COURSE_ID
	AND CA.TA_ID = @id
END