CREATE TABLE [dbo].[Question]
(
	[Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[Text] VARCHAR(1000),
	[DifficultyLevel] INT NOT NULL,
	[Hint] VARCHAR(1000) NULL,
	[Explanation] VARCHAR(1000) NULL,
	[NumberOfSets] INT,
	CONSTRAINT [MinimumSets] CHECK ([NumberOfSets] >= 1)
)