create table TopicQuestionMapping(topic_id integer foreign key references Topic(id)  ON DELETE CASCADE ON UPDATE CASCADE,
                                  question_id integer foreign key references Question(id)  ON DELETE CASCADE ON UPDATE CASCADE
								  );