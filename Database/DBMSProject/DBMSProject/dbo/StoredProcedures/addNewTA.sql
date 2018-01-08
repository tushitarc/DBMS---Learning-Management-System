CREATE PROC addNewTA @StudentID INT
AS
BEGIN
	DECLARE @NAME VARCHAR(30)
	DECLARE @UserId VARCHAR(30)
	IF EXISTS(SELECT 1 FROM Student WHERE ID = @StudentID)
	BEGIN
		SELECT @Name = [Name], @UserId = UserId FROM Student WHERE ID = @StudentID
		INSERT INTO TeachingAssistant([Name], UserId, [Password]) VALUES (@Name, 'ta_'+@UserId, 'ta_'+@UserId)
	END
	ELSE
	BEGIN
		raiserror('Student NOT FOUND', 11, 1)
		RETURN
	END
END