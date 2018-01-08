CREATE PROCEDURE [dbo].[pickQuestionFromTopicWithDifficulty]
	@topicId bigint,
	@difficultyLevel int
AS
BEGIN
	Select Id from Question q where q.difficultyLevel=@difficultyLevel and q.id IN
	(Select question_id from TopicQuestionMapping where topic_id = @topicId)
END