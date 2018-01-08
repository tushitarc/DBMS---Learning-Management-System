CREATE PROCEDURE [dbo].[GetLoginDetails]
	@userId VARCHAR(30),
	@password VARCHAR(30)
AS
BEGIN
	IF EXISTS(SELECT * FROM STUDENT WHERE UserId = @userId AND Password = @password)
		SELECT Id, [Name], UserId, [Level], 'Student' AS [TYPE] FROM STUDENT WHERE  UserId = @userId AND Password = @password
	IF EXISTS(SELECT * FROM TeachingAssistant WHERE UserId = @userId AND Password = @password)
		SELECT Id, [Name], UserId, 'TeachingAssistant' AS [TYPE] FROM TeachingAssistant WHERE  UserId = @userId AND Password = @password
	IF EXISTS(SELECT * FROM INSTRUCTOR WHERE UserId = @userId AND Password = @password)
		SELECT Id, [Name], UserId, 'INSTRUCTOR' AS [TYPE] FROM INSTRUCTOR WHERE  UserId = @userId AND Password = @password
END
