CREATE TABLE [dbo].[Instructor]
(
	[Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[Name] VARCHAR(30),
	[UserId] VARCHAR(30) UNIQUE NOT NULL,
	[Password] VARCHAR(30) NOT NULL,
	CONSTRAINT [PasswordMinLengthInstructor] CHECK (LEN([Password])>=4)
)