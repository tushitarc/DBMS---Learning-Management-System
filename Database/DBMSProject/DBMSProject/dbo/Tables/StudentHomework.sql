CREATE TABLE [dbo].[StudentHomework] (
  [id] int IDENTITY(1,1) NOT NULL primary key,
  [student_id] int  NULL,
  [hw_id] int  NULL,
  [score] int  NULL,
  [attempt] int  NULL,   
  CONSTRAINT [Student_Homework_Attempt] UNIQUE NONCLUSTERED
    (
        [student_id], [hw_id], [attempt]
    )
)

GO

ALTER TABLE [dbo].[StudentHomework] ADD CONSTRAINT [FK__STUDENT] 
FOREIGN KEY ([student_id]) REFERENCES [Student] ([Id]) ON DELETE Cascade
GO
-- ----------------------------
ALTER TABLE [dbo].[StudentHomework] ADD CONSTRAINT [FK__HOMEWOKR] 
FOREIGN KEY ([hw_id]) REFERENCES [HOMEWORK] ([ID]) ON DELETE Cascade
GO