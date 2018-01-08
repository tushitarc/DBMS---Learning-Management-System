﻿CREATE PROCEDURE [dbo].[fetchReport]
(
    -- Add the parameters for the stored procedure here
	@courseID int
)
AS
BEGIN

SELECT SH.STUDENT_ID, SH.HW_ID, S.NAME, MAX(SH.SCORE) AS MAXIMUM_SCORE 
FROM [STUDENTHOMEWORK] SH
INNER JOIN HOMEWORK H ON H.ID = SH.HW_ID 
INNER JOIN STUDENT S ON S.ID = SH.STUDENT_ID
WHERE H.COURSE_ID = @courseID
GROUP BY SH.STUDENT_ID, S.NAME, SH.HW_ID

END