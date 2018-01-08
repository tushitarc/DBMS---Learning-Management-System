CREATE TRIGGER BEFOREHOMEWORKINSERT ON HOMEWORK INSTEAD OF INSERT
AS
BEGIN

IF NOT EXISTS(SELECT I.COURSE_ID FROM INSERTED I INNER JOIN COURSE C ON C.Id = I.COURSE_ID AND C.EndDate <= GETDATE()) 
     RAISERROR('Homework cannot be added to a course that has ended.',0,1)
ELSE       
			INSERT INTO [dbo].[HOMEWORK]
           ([NAME]
           ,[COURSE_ID]
           ,[NUM_OF_QUESTIONS]
           ,[STARTDATE]
           ,[ENDDATE]
           ,[MAX_TRIES]
           ,[POINTS_IF_CORRECT]
           ,[POINTS_IF_WRONG]
		   ,[HOMEWORK_TYPE])
			(SELECT [NAME]
           ,[COURSE_ID]
           ,[NUM_OF_QUESTIONS]
           ,[STARTDATE]
           ,[ENDDATE]
           ,[MAX_TRIES]
           ,[POINTS_IF_CORRECT]
           ,[POINTS_IF_WRONG]
		   ,[HOMEWORK_TYPE] FROM INSERTED) 

END

