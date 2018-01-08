package program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;
import database.objects.Topic;

public class Homework {
	IDataAccess dataAccess;
	int id;
	int courseID;
	String name;
	int numOfQuestions;
	int pointsIfRight;
	int pointsIfWrong;
	int maxTries;
	Date startDate;
	Date endDate;
	String scoreSelectCriteria;	
	List<Question> questions;
	String homeworkType;
	// each hw should have a topic id because a course has multiple topics
	int topicId;
	//TODO
	//Store difficulty level as well for each homework, cumulative of all questions difficulty
	Scanner sc = new Scanner(System.in);

	public void showHWMenuForInstructor() throws Exception {
		boolean loop = true;
		while (loop) {
			System.out.println("");
			System.out.println("");
			System.out.println("Enter a choice regarding homework");
			System.out.println("1. View all exercises for a course");
			System.out.println("2. View details of an exercise");
			System.out.println("3. Create an exercise");
			System.out.println("4. Add a question to a homework");
			System.out.println("5. Remove a question from a homework");
			System.out.println("6. Create a new homework from an old homework");
			System.out.println("7. Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: ViewHomeworksbyCourse(); break;
			case 2:
				displayHWDetails();
				break;
			case 3:
				boolean isAdpative = CreateHomework();
				/* Questions will be added dynamically for adaptive homework */
				if (isAdpative)
					loop = false;
				break;
			case 4:
				AddQuestionToHW();
				break;
			case 5:
				RemoveQuestionFromHW();
				break;
			case 6:
				CreateNewHWFromOld();
				break;
			case 7:
				loop = false;
				break;
			default:
				System.out.println("You entered a wrong choice, please select again");
			}
		}
	}
	//Student homework menu is basically a part of student menuO
	public void showHWMenuForStudent() throws Exception {
		boolean loop = true;
		while (loop) {
			System.out.println("Enter a choice regarding homework");
			System.out.println("1. View homework for a course");
			System.out.println("2. Take homework");
			//System.out.println("3. View Past homeworks");
			System.out.println("3. Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				ViewHomeworksbyCourse();
				break;
			case 2:
				takeHomework();
				break;
			//case 3:
				// viewHomeworkHistory();
				//break;
			case 3:
				loop = false;
				break;
			default:
				System.out.println("You entered a wrong choice, please select again");
			}
		}
	}

	public Homework() {
		name = "";
		courseID = 0;
		numOfQuestions = 0;
		pointsIfRight = 0;
		pointsIfWrong = 0;
		startDate = new Date();
		endDate = new Date();
		maxTries = 1;
		questions = new ArrayList<Question>();
		dataAccess = new DataAccess();
		scoreSelectCriteria ="MAX";
	}

	public void AddQuestionToHW() {
		//if(hwID == null) {
		System.out.println("Enter Homework ID");
		int homeWorkID = sc.nextInt();
		//}
		boolean mainChoice = true;
		while(mainChoice) {
		int questionID;
		boolean choice = true;
		 
		while(choice) {
		System.out.println("Enter :");
		System.out.println("1 : Explore questions");
		System.out.println("2 : Exit and enter Question ID");			
		int opt = sc.nextInt();
		switch(opt)
		{
		case 1:{
			System.out.println("Enter course ID to explore topics:");
			int cID = sc.nextInt();
			List<Topic> listTopics = null;
			try {
				listTopics = Topic.getAllTopicsForACourse(cID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(listTopics.isEmpty()) {
				System.out.println("No topics mapped to this course.");
				break;
			}
			for(Topic l:listTopics){
				System.out.println(l.getName());
			}
			System.out.println();
			List<Question> listQuestion = new Question().RetrieveQuestionsByTopic();
			if(listQuestion.isEmpty())
				System.out.println("No questions mapped to this topic.");
			for(Question l:listQuestion){
				System.out.println(l.getId() +" : "+ l.getText());
			}
			//System.out.println("Enter topic ID to explore questions in the topic:");
			//int tID = sc.nextInt();
		}break;
		case 2: choice = false; break;
		}			
		}
		System.out.println("Enter question ID: ");
		questionID = sc.nextInt();
		
		String insertQuery = "INSERT INTO HOMEWORK_QUESTION_MAP (HW_ID, Q_ID) VALUES (" + homeWorkID + "," + questionID
				+ ")";

		try {
			dataAccess.executeQueryWithoutResult(insertQuery);
			System.out.println("Successfully added question to the homework.");
		}

		catch (Exception e) {
			System.out.print(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Do you want to add another question? 1 : Yes/ 0 : N");
		mainChoice = (sc.nextInt()==1)? true:false;
		}

	}

	public void CreateNewHWFromOld() {
		System.out.println("Enter Old Homework ID");
		int homeWorkID1 = sc.nextInt();
		System.out.println("Enter New Homework ID");
		int homeWorkID2 = sc.nextInt();

		try {
			dataAccess.executeQueryWithoutResult("EXEC createNewHWFromOld " + homeWorkID1 + "," + homeWorkID2);
			System.out.println(
					"Successfully added questions from Homework: " + homeWorkID1 + " to Homework: " + homeWorkID2);
		}

		catch (Exception e) {
			System.out.print(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean CreateHomework() throws Exception {
		boolean isAdaptive = false;
		Homework homework = new Homework();
		sc.nextLine();
		System.out.println("Enter a name for the Homework:");
		homework.name = sc.nextLine();
		System.out.println("Enter the associated course ID: ");
		homework.courseID = sc.nextInt();
		System.out.println("Enter the associated topic ID: ");
		homework.topicId = sc.nextInt();
		System.out.println("Enter Number of Questions: ");
		homework.numOfQuestions = sc.nextInt();
		System.out.println("Enter Maximum number of tries: ");
		homework.maxTries = sc.nextInt();
		System.out.println("Enter start date for the homework in format MM/dd/yyyy: ");
		// Adding this extra sc.nextLine() so that start date is taken from next
		// line
		// sc.nextLine();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		//Date startDate = formatter.parse(sc.next());
		//String dateFormat = "MM/dd/yyyy";
		homework.startDate = formatter.parse(sc.next());
		System.out.println("Enter end date for the homework in format MM/dd/yyyy: ");
		homework.endDate = formatter.parse(sc.next());
		System.out.println("Enter points to be added if answer is right: ");
		homework.pointsIfRight = sc.nextInt();
		System.out.println("Enter points to be deducted if answer is wrong: ");
		homework.pointsIfWrong = sc.nextInt();
		System.out.println("Enter the type of homework you want to create:STANDARD OR ADAPTIVE");
		homework.homeworkType = sc.next();
		System.out.println("Enter the score selection criteria: LAT OR MAX OR AVG");
		homework.scoreSelectCriteria = sc.next();
		// if homework is adaptive we cannot do homework question mapping now,
		// we need
		// to add questions dynamically, just store the type, so when student
		// attempts that
		// adaptive homework, pull out questions based on difficulty then
		/***
		 * Three ways to create adaptive homework 1. add list of questions by
		 * questionid 2. add questions from selected topics? 3. old homework,
		 * but we can remove and add questions from old homework as well
		 */
		
		if (homework.homeworkType.equalsIgnoreCase("Adaptive"))
			isAdaptive = true;
		try {
			String query ="EXEC createHomework '" + homework.name + "'," + homework.courseID + ","
					+ homework.numOfQuestions + "," + homework.maxTries + ", '" + (new java.sql.Date(homework.startDate.getTime())) + "' , '"
					+ (new java.sql.Date(homework.endDate.getTime()))+ "' ," + homework.pointsIfRight + "," + homework.pointsIfWrong + ", '"
					+ homework.homeworkType + "' ," + homework.topicId + " , '" + homework.scoreSelectCriteria+"'";
			dataAccess.executeQueryWithoutResult(query);
			if (isAdaptive)
				System.out.println("Successfully created an adaptive homework");
			else
				System.out.println("Successfully created homework.");
			
			//AddQuestionToHW();
		}

		catch (Exception e) {
			System.out.print(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			homework = null;
		}

		return isAdaptive ? true : false;
	}

	public void RemoveQuestionFromHW() {
		System.out.println("Enter Homework ID");
		int homeWorkID = sc.nextInt();
		System.out.println("Enter Question ID");
		int questionID = sc.nextInt();

		String deleteQuery = "DELETE FROM HOMEWORK_QUESTION_MAP WHERE HW_ID =" + homeWorkID + " AND Q_ID = "
				+ questionID;

		try {
			dataAccess.executeQueryWithoutResult(deleteQuery);
			System.out.println("Successfully deleted question from the homework.");
		}

		catch (Exception e) {
			System.out.print(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Used for take homework, if homeworkid is not given, ask homeworkid,
	 * passing homeworkid from takehomework
	 */
	public Homework RetrieveHomeworkByID(Integer homeworkId) {
		Homework retrievedHomework = new Homework();
		Scanner src = new Scanner(System.in);
		if (homeworkId == null) {
			System.out.print("\nEnter Homework exercise ID : ");
			id = src.nextInt();
		} else
			id = homeworkId;
		String retrieveHWDetails = "SELECT * FROM HOMEWORK WHERE ID=" + id;
		String retrieveQuestions = "SELECT q.* FROM HOMEWORK_QUESTION_MAP HQM INNER JOIN QUESTION Q ON HQM.Q_ID = Q.ID "
				+ " WHERE HW_ID=" + id;

		try {
			ResultSet rs = dataAccess.executeQuery(retrieveHWDetails);

			while (rs.next()) {
				retrievedHomework.id = rs.getInt("ID");
				retrievedHomework.courseID = rs.getInt("COURSE_ID");
				retrievedHomework.name = rs.getString("NAME");
				retrievedHomework.numOfQuestions = rs.getInt("NUM_OF_QUESTIONS");
				retrievedHomework.maxTries = rs.getInt("MAX_TRIES");
				retrievedHomework.startDate = rs.getDate("STARTDATE");
				retrievedHomework.endDate = rs.getDate("ENDDATE");
				retrievedHomework.pointsIfRight = rs.getInt("POINTS_IF_CORRECT");
				retrievedHomework.pointsIfWrong = rs.getInt("POINTS_IF_WRONG");
				retrievedHomework.homeworkType = rs.getString("HOMEWORK_TYPE");
				retrievedHomework.topicId = rs.getInt("TOPIC_ID");
				retrievedHomework.scoreSelectCriteria = rs.getString("ScoreSelection");
			}

			rs = dataAccess.executeQuery(retrieveQuestions);

			while (rs.next()) {
				Question retrievedQuestion = new Question();
				retrievedQuestion.id = rs.getInt("ID");
				retrievedQuestion.text = rs.getString("TEXT");
				retrievedQuestion.difficultylevel = rs.getInt("DIFFICULTYLEVEL");
				retrievedQuestion.explanation = rs.getString("EXPLANATION");
				retrievedQuestion.hint = rs.getString("HINT");
				retrievedHomework.questions.add(retrievedQuestion);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
			e.printStackTrace();
		}

		return retrievedHomework;
	}

	// TODO this method calls retrieve homework by id, so this means we pull out
	// hw details
	// just for one homework and not all homeworks
	public void displayHWDetails() {
		Homework hw = RetrieveHomeworkByID(null);
		System.out.println("Exercise Name:" + hw.name);
		System.out.println("Exercise Start Date:" + hw.startDate);
		System.out.println("Exercise End Date:" + hw.endDate);
		System.out.println("Exercise Number of Questions:" + hw.numOfQuestions);
		System.out.println("Exercise Maximum Retries:" + hw.maxTries);
		System.out.println("Points obtained if answer correct:" + hw.pointsIfRight);
		System.out.println("Points deducted if answer incorrect:" + hw.pointsIfWrong);
		System.out.println("Score selection criteria:"+hw.scoreSelectCriteria);

		System.out.println("Exercise contains the following questions:");
		for (int i = 0; i < hw.questions.size(); i++)
			System.out.println("ID: " + hw.questions.get(i).id + " Question: " + hw.questions.get(i).text);

	}

	public void ViewHomeworksbyCourse() {
		String selectQuery ="";
		System.out.println("Enter the course id to see its homeworks");
		Scanner in = new Scanner(System.in);
		int courseID = in.nextInt();
		boolean studentFlag = true;
		
		if(Student.LOGGEDINSTUDENT == 0)
			studentFlag = false;
		
		if(studentFlag)
		selectQuery = "select * from Homework where course_id=" + courseID+" and STARTDATE <= getdate() order by ENDDATE desc";
		else
		selectQuery = "select * from Homework where course_id=" + courseID+" order by ENDDATE desc";
		
		List<Homework> homeworks = new ArrayList<Homework>();
		try {
			ResultSet rs = dataAccess.executeQuery(selectQuery);
			if(studentFlag) {
			System.out.println("---------------Open Homeworks--------------------");
			while (rs.next()) {
				if(rs.getDate("ENDDATE").compareTo(new Date()) > 0) {
				System.out.println("---------"+"Exercise ID:" + rs.getString("ID")+ "---------");	
				System.out.println("Exercise Name:" + rs.getString("NAME"));
				System.out.println("Exercise Topic:" + rs.getString("TOPIC_ID"));
				System.out.println("Exercise Type:" + rs.getString("HOMEWORK_TYPE"));
				System.out.println("Exercise Start Date:" + rs.getDate("STARTDATE"));
				System.out.println("Exercise End Date:" + rs.getDate("ENDDATE"));
				System.out.println("Exercise Number of Questions:" + rs.getInt("NUM_OF_QUESTIONS"));
				System.out.println("Exercise Maximum Retries:" + rs.getInt("MAX_TRIES"));
				System.out.println("Points obtained if answer correct:" + rs.getInt("POINTS_IF_CORRECT"));
				System.out.println("Points deducted if answer incorrect:" + rs.getInt("POINTS_IF_WRONG"));
				System.out.println("Score selection criteria:"+rs.getString("ScoreSelection"));
				System.out.println();
				break;
				}
			}
			System.out.println("---------------Past Homeworks--------------------");
			while (rs.next()) {
				if(rs.getDate("ENDDATE").compareTo(new Date()) < 0) {
					System.out.println("---------"+"Exercise ID:" + rs.getString("ID")+ "---------");
					System.out.println("Exercise Name:" + rs.getString("NAME"));
					System.out.println("Exercise Topic:" + rs.getString("TOPIC_ID"));
					System.out.println("Exercise Type:" + rs.getString("HOMEWORK_TYPE"));
					System.out.println("Exercise Start Date:" + rs.getDate("STARTDATE"));
					System.out.println("Exercise End Date:" + rs.getDate("ENDDATE"));
					System.out.println("Exercise Number of Questions:" + rs.getInt("NUM_OF_QUESTIONS"));
					System.out.println("Exercise Maximum Retries:" + rs.getInt("MAX_TRIES"));
					System.out.println("Points obtained if answer correct:" + rs.getInt("POINTS_IF_CORRECT"));
					System.out.println("Points deducted if answer incorrect:" + rs.getInt("POINTS_IF_WRONG"));
					System.out.println("Score selection criteria:"+rs.getString("ScoreSelection"));
					System.out.println();
				}
			}
			}
			else {
				while (rs.next()) {
					System.out.println("---------"+"Exercise ID:" + rs.getString("ID")+ "---------");	
					System.out.println("Exercise Name:" + rs.getString("NAME"));
					System.out.println("Exercise Topic:" + rs.getString("TOPIC_ID"));
					System.out.println("Exercise Type:" + rs.getString("HOMEWORK_TYPE"));
					System.out.println("Exercise Start Date:" + rs.getDate("STARTDATE"));
					System.out.println("Exercise End Date:" + rs.getDate("ENDDATE"));
					System.out.println("Exercise Number of Questions:" + rs.getInt("NUM_OF_QUESTIONS"));
					System.out.println("Exercise Maximum Retries:" + rs.getInt("MAX_TRIES"));
					System.out.println("Points obtained if answer correct:" + rs.getInt("POINTS_IF_CORRECT"));
					System.out.println("Points deducted if answer incorrect:" + rs.getInt("POINTS_IF_WRONG"));
					System.out.println("Score selection criteria:"+rs.getString("ScoreSelection"));
					System.out.println();
			}
			}
		} catch (Exception e) {
			//System.out.println("Something went wrong, Please try again");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void takeHomework() throws Exception {
		System.out.println("Enter the homework id you want to take");
		Scanner in = new Scanner(System.in);
		int homeworkId = in.nextInt();
		Homework homework = RetrieveHomeworkByID(homeworkId);
		
		//TODO fix the date validations
//		Date currentDate = new Date();
//		Date hwStartDate = homework.startDate;
//		Date hwEndDate = homework.endDate;
//		System.out.println(currentDate+" "+hwStartDate+" "+hwEndDate);
//		if (currentDate.before(hwStartDate))
//			throw new Exception("You cannot take a homework before start date");
//		if (currentDate.after(hwEndDate))
//			throw new Exception("You cannot take a homework after end date");
//		
		// if logged in studentid is 0 then person cannot take a homework
		//if (Student.LOGGEDINSTUDENT == 0)
			//throw new Exception("You should login as a student to take homework");
		//TODO get student id from logged in student
		
		int studentId = Student.LOGGEDINSTUDENT;
		//int studentId = 8;

		List<StudentHomework> shwList=new StudentHomework().getStudentHomework(studentId, homeworkId);
		
		//null will be returend in case of exception
		if(shwList==null)
		throw new Exception("Something went wrong, Please try again");
		
		if (shwList.size() >= homework.maxTries) {
			System.out.println("You have finished your attempts for this homework");
			return;
		}
		int attempt=shwList.size()+1;
		if (homework.homeworkType.equalsIgnoreCase("adaptive")) {
			takeAdaptiveHomework(homework, studentId, attempt);
			return;
		}
		//Current Student Homework object
		StudentHomework currentStudentHomework=new StudentHomework();
		currentStudentHomework.student_id=studentId;
		currentStudentHomework.hw_id=homeworkId;
		currentStudentHomework.attempt=attempt;
	new StudentHomework().saveStudentHomework(currentStudentHomework);
	
	currentStudentHomework=new StudentHomework().getStudentHomework(studentId,homeworkId,attempt);
	//System.out.println(currentStudentHomework);
	int count=0;
	int currentHomeworkScore=0;
	Integer sth_id=currentStudentHomework.id;
	//System.out.println(sth_id);
	List<Question> questions = homework.questions;
	while(count!=questions.size()){
		StudentHomeworkDetail shd=new StudentHomeworkDetail();
		shd.sth_id=sth_id;
		int questionId=questions.get(count).getId();
		shd.question_id=questionId;
		Question q=Question.displayQuestion(questionId);
		System.out.println("Enter the option id : 0,1,2,3");
		int optionId=in.nextInt();
		boolean isCorrect=q.isCorrectOption(optionId);
		shd.option_id=optionId;
		if(isCorrect){
			shd.correct=isCorrect;
			shd.score=homework.pointsIfRight;
			shd.hint="You answered it correctly, option ("+optionId +")";
		}
		else{
			shd.correct=isCorrect;
			shd.score=-homework.pointsIfWrong;
			shd.hint="You answered it incorrectly, option ("+optionId +")The hint is :"+ q.hint;
		}
		new StudentHomeworkDetail().saveStudentHomeworkDetail(shd);
		currentHomeworkScore+=shd.score;
		count++;
	}
		new StudentHomework().updateStudentHomework(sth_id,currentHomeworkScore);
		System.out.println("You have complete the homework, and your score is:"+currentHomeworkScore);
	}
	
	/*
	 * need topic id for picking questions from that topic
	 */
	public void takeAdaptiveHomework(Homework homework,Integer studentId,Integer attempt) throws Exception{
		int topicId=homework.topicId;
		int numberOfQuestions=homework.numOfQuestions;
		int countQuestions=0;
		
		int maxDifficultyLevel=6;
		int minDifficultyLevel=1;
		int studentDifficultyLevel=3;
		StudentHomework shw=new StudentHomework();
		shw.hw_id=homework.id;
		shw.student_id=studentId;
		shw.attempt=attempt;
		int currentHomeworkScore=0;
		new StudentHomework().saveStudentHomework(shw);
		shw=new StudentHomework().getStudentHomework(studentId, homework.id, attempt);
		int sth_id=shw.id;
		while(countQuestions!=numberOfQuestions){
			StudentHomeworkDetail shd=new StudentHomeworkDetail();
			shd.sth_id=sth_id;
			int pickedQuestionId=pickQuestion(topicId,studentDifficultyLevel);
			Question q=Question.displayQuestion(pickedQuestionId);
			System.out.println("Enter the option id : 0,1,2,3");
			Scanner in=new Scanner(System.in);
			int optionId=in.nextInt();
			boolean isCorrect=q.isCorrectOption(optionId);
			shd.option_id=optionId;
			shd.question_id=pickedQuestionId;
			if(isCorrect){
				shd.correct=isCorrect;
				shd.score=homework.pointsIfRight;
				shd.hint="You answered it correctly, option ("+optionId +")";
				studentDifficultyLevel=(studentDifficultyLevel==maxDifficultyLevel)?6:studentDifficultyLevel+1;
			}
			else{
				shd.correct=isCorrect;
				shd.score=-homework.pointsIfWrong;
				shd.hint="You answered it incorrectly, option ("+optionId +")The hint is :"+ q.hint;
				studentDifficultyLevel=(studentDifficultyLevel==minDifficultyLevel)?1:studentDifficultyLevel-1;
			}
			new StudentHomeworkDetail().saveStudentHomeworkDetail(shd);
			currentHomeworkScore+=shd.score;
			//save detail
			countQuestions++;	
			}
		//update shw
		new StudentHomework().updateStudentHomework(sth_id, currentHomeworkScore);
		System.out.println("You have complete the homework, and your score is:"+currentHomeworkScore);
	}

	/*
	 * pick a question from a topic w
	 */
	private int pickQuestion(Integer topicId,int difficultyLevel) throws Exception{
		//get a random question from this topic and with this topic id
		List<Integer> listQuestions=new ArrayList<Integer>();
		try {
			ResultSet rs=dataAccess.executeQuery(
					"EXEC pickQuestionFromTopicWithDifficulty "+topicId+","+difficultyLevel);
			while(rs.next()){
				int questionId=rs.getInt(1);
				listQuestions.add(questionId);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Something went wrong, Please try again");
		}
		if(listQuestions==null) 
			throw new Exception("Could not find question for topic :"+topicId+" and difficulty"+difficultyLevel);
		int rand= (int)(listQuestions.size()*Math.random());
		return listQuestions.get(rand);
	}
	
	public void viewPastHomeworkBriefSummary(){
		System.out.println("Enter the homework id for which you want to see summary");
		Scanner in=new Scanner(System.in);
		int homeworkId=in.nextInt();
		Integer studentId=Student.LOGGEDINSTUDENT;
		if(studentId==0){
			System.out.println("Enter the student id for which you want to see summary");
			studentId=in.nextInt();
		}
		List<StudentHomework> list=new StudentHomework().getStudentHomework(Student.LOGGEDINSTUDENT, homeworkId);
		//Show marks of each attempt
		if( list == null || list.isEmpty())
			System.out.println("No records found for the homewokr id");
		
		for(StudentHomework l:list)
			System.out.println(l.toString());
	}
	public void viewPastHomeworkCompleteSummary() throws Exception{
		System.out.println("Enter the homework id for which you want to see summary");
		Scanner in=new Scanner(System.in);
		int homeworkId=in.nextInt();
		Integer studentId=Student.LOGGEDINSTUDENT;
		if(studentId==0){
			System.out.println("Enter the student id for which you want to see summary");
			studentId=in.nextInt();
		}
		List<StudentHomework> list=new StudentHomework().getStudentHomework(Student.LOGGEDINSTUDENT, homeworkId);
		//Show marks of each attempt
		if(list==null)
			System.out.println("No records found for the homewokr id");
		
		for(StudentHomework l:list){
			System.out.println(l.toString());
			System.out.println("Questions in the assignment :");
			List<StudentHomeworkDetail> shdList=new StudentHomeworkDetail().getStudentHWdetails(l.id);
			for(StudentHomeworkDetail  shd:shdList){
				Question q=Question.displayQuestion(shd.question_id);
				System.out.println(shd.hint);
				System.out.println("Your score for this question is "+shd.score);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Homework [dataAccess=" + dataAccess + ", id=" + id + ", courseID=" + courseID + ", name=" + name
				+ ", numOfQuestions=" + numOfQuestions + ", pointsIfRight=" + pointsIfRight + ", pointsIfWrong="
				+ pointsIfWrong + ", maxTries=" + maxTries + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", questions=" + questions + ", homeworkType=" + homeworkType + ", topicId=" + topicId + ", sc=" + sc
				+ "]";
	}

}
