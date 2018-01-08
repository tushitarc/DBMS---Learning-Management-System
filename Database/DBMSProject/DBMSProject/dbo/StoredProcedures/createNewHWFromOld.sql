CREATE PROCEDURE [dbo].[createNewHWFromOld]
(
    -- Add the parameters for the stored procedure here
	@hwOld int,
    @hwNew int
)
AS
BEGIN
    -- Insert statements for procedure here
    INSERT INTO [dbo].[HOMEWORK_QUESTION_MAP]
           ([HW_ID]
           ,[Q_ID])
	SELECT @hwNew AS HW_ID, Q_ID
	FROM [HOMEWORK_QUESTION_MAP] WHERE HW_ID = @hwOld
END