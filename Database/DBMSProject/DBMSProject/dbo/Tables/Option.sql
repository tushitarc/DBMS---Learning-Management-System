﻿CREATE TABLE [OPTION] (
    ID INT IDENTITY(1,1) PRIMARY KEY,
	QuestionId INT REFERENCES Question(Id) ON DELETE CASCADE,
    SetId INT NOT NULL,
	[Text] VARCHAR(1000),
	[Correct] CHAR(1)
)