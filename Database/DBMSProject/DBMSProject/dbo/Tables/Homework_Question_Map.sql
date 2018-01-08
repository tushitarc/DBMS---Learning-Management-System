﻿CREATE TABLE HOMEWORK_QUESTION_MAP(
    HW_ID int FOREIGN KEY REFERENCES HOMEWORK(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    Q_ID int FOREIGN KEY REFERENCES QUESTION(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT [UNIQUENESS_HOMEWORK_Question] UNIQUE NONCLUSTERED (HW_ID, Q_ID)
)