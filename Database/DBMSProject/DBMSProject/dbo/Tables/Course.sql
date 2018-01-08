CREATE TABLE [dbo].[Course]
(
	[Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[Name] VARCHAR(100) NOT NULL,
	StartDate DATETIME NOT NULL,
	EndDate DATETIME NOT NULL,
	InstructorID INT FOREIGN KEY REFERENCES Instructor(Id) NOT NULL,
	MaxEnrollment INT DEFAULT 5,
	StudentsEnrolled INT DEFAULT 0,
	CourseCode VARCHAR(10) NOT NULL DEFAULT 'CSC',
	CHECK (StudentsEnrolled <= MaxEnrollment),
	CHECK (StartDate <= EndDate)
)