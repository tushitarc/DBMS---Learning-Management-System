create table TopicBelongsToCourse (topicId int foreign key references Topic(id) ON DELETE CASCADE ON UPDATE CASCADE,
									courseId int foreign key references Course(id) ON DELETE CASCADE ON UPDATE CASCADE,
									unique(topicId,courseId)
									)