
-- ----------------------------
-- Table structure for Student Homework Detail
-- ----------------------------

CREATE TABLE [dbo].[StudentHomeworkDetail] (
  [question_id] int  NULL,
  [option_id] int  NULL,
  [score] int  NULL,
  [correct] bit  NULL,
  [hint] varchar(200) NOT NULL,
  [sth_id] int  NOT NULL
)

-- ----------------------------
-- Foreign Keys structure for table Student Homework detail
-- ----------------------------

GO

ALTER TABLE [dbo].[StudentHomeworkDetail]  WITH CHECK ADD FOREIGN KEY([sth_id])
REFERENCES [dbo].[StudentHomework] ([id])
GO
ALTER TABLE [dbo].[StudentHomeworkDetail] ADD CONSTRAINT [FK_QUESTION] 
FOREIGN KEY ([question_id]) REFERENCES [QUESTION] ([ID]) ON DELETE Cascade
GO
