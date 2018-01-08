CREATE TABLE [dbo].[student_takes_hw](
	[student_id] [int],
	[hw_id] [int] NULL,
	[score] [int] NULL,
	[attempts] [int] NULL,
	[id] [int] IDENTITY(1,1) PRIMARY KEY NOT NULL
)