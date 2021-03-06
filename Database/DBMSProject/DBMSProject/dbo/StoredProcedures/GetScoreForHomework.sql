﻿CREATE PROC [dbo].[GetScoreForHomework] @StudentId INT, @HWID INT
AS
BEGIN
	DECLARE @SCORE FLOAT
	DECLARE @SCORESELECTION VARCHAR(3)
	SELECT @SCORESELECTION = ScoreSelection FROM HOMEWORK WHERE ID = @HWID
	IF @SCORESELECTION = 'LAT'
		SELECT TOP 1 @SCORE = SCORE FROM StudentHomework WHERE Student_id = @StudentId AND HW_ID = @HWID ORDER BY attempt desc
	ELSE IF @SCORESELECTION = 'AVG'
		SELECT @SCORE = AVG(CAST(SCORE AS FLOAT)) FROM StudentHomework WHERE Student_id = @StudentId AND HW_ID = @HWID GROUP BY hw_id having hw_id = @HWID
	ELSE
		SELECT @SCORE = MAX(SCORE) FROM StudentHomework WHERE Student_id = @StudentId AND HW_ID = @HWID GROUP BY hw_id having hw_id = @HWID
	RETURN @SCORE
END