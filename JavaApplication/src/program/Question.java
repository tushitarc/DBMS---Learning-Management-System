package program;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;
import database.objects.Course;

public class Question{
	static IDataAccess dataAccess = new DataAccess();
	int id;
	String text;
	int difficultylevel;
	String hint;
	String explanation;
	int numberOfSets;
	
	public static String getAllQuestionsByID = "SELECT * FROM QUESTION WHERE ID=?";
	
	//For concrete questions
	long setID = -1;
	List<Option> options;
	List<Parameter> parameters;
	
	public Question()
	{
		text = "";
		difficultylevel = 0;
		hint= "";
		explanation="";	
		dataAccess = new DataAccess();
		numberOfSets = 0;
	}

	public long getSetID() {
		return setID;
	}

	public void setSetID(long setID) {
		this.setID = setID;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public static IDataAccess getDataAccess() {
		return dataAccess;
	}

	public static void setDataAccess(IDataAccess dataAccess) {
		Question.dataAccess = dataAccess;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDifficultylevel() {
		return difficultylevel;
	}

	public void setDifficultylevel(int difficultylevel) {
		this.difficultylevel = difficultylevel;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getNumberOfSets() {
		return numberOfSets;
	}

	public void setNumberOfSets(int numberOfSets) {
		this.numberOfSets = numberOfSets;
	}

	public static String getGetAllQuestionsByID() {
		return getAllQuestionsByID;
	}

	public static void setGetAllQuestionsByID(String getAllQuestionsByID) {
		Question.getAllQuestionsByID = getAllQuestionsByID;
	}
	
	public static void deleteQuestion(int questionId) throws Exception{
		Connection conn = dataAccess.connect();
		PreparedStatement st = conn.prepareStatement("DELETE FROM Question WHERE ID = '" + questionId + "';");
		st.executeUpdate();
	}
	
	public static int insertQuestion()
	{
		 	Scanner src= new Scanner(System.in);
		    System.out.print("\n Adding a new question ... ");
		    
		    System.out.print("\n Enter Question Text : ");
		    String text = src.nextLine();
		    
		    System.out.print("\n Enter Difficulty level :");
		    int difficultylevel = src.nextInt();
		    src.nextLine();
		    
		    System.out.print("\n Enter Hint :");
		    String hint = src.nextLine();
		    
		    System.out.print("\n Enter Explanation : ");
		    String explanation = src.nextLine();
		    
		    System.out.println("\n Enter number of sets");
		    int numberOfSets = src.nextInt();
		    
		    String insertQuery = "INSERT INTO QUESTION (Text, DifficultyLevel, Hint, Explanation, NumberOfSets) VALUES (? , ? , ? , ?, ? )"; 

	    	try
		    {
	    		Connection conn = dataAccess.connect();
	    		PreparedStatement  stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
	    		stmt.setString(1, (text).equals("")?null:text);
	    		stmt.setInt(2, difficultylevel);
	    		stmt.setString(3, hint);
	    		stmt.setString(4, explanation);
	    		stmt.setInt(5, numberOfSets);
	    		
	    		 int affectedRows = stmt.executeUpdate();
	    		 ResultSet generatedKeys = stmt.getGeneratedKeys();
	    		 if (generatedKeys.next()) {
	                 return generatedKeys.getInt(1);
	             }
		    }
	    	
		    catch (Exception e) {
		    	System.out.print(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;

	}
	
	public Question RetrieveQuestionByID()
	{
		Question retrievedQuestion = new Question();
		Scanner src= new Scanner(System.in);
	    System.out.print("\n Retrieving a question object ... ");
	    
	    System.out.print("\n Enter Question ID : ");
	    id = src.nextInt();
	    
	    String retrieveQuery = "SELECT * FROM QUESTION WHERE ID="+id;
	    
	    try {
			ResultSet rs = dataAccess.executeQuery(retrieveQuery);
			
			while(rs.next()){
				retrievedQuestion.id  = rs.getInt("ID");
				retrievedQuestion.text = rs.getString("TEXT");
				retrievedQuestion.difficultylevel = rs.getInt("DIFFICULTYLEVEL");
				retrievedQuestion.explanation = rs.getString("EXPLANATION");
				retrievedQuestion.hint = rs.getString("HINT");
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	    
	    return retrievedQuestion;
	}

	public List<Question> RetrieveQuestionsByTopic()
	{
		List<Question> retrievedQuestions = new ArrayList<Question>();
		Scanner src= new Scanner(System.in);
	    
	    System.out.print("Enter the topic for which you want to obtain the list of questions : ");
	    String topic = src.nextLine();
	    
	    String retrieveQuery = "SELECT Q.* FROM QUESTION Q " + 
	    		"INNER JOIN TopicQuestionMapping TQM ON Q.Id = TQM.question_id " + 
	    		"INNER JOIN Topic T ON TQM.topic_id = T.id " + 
	    		"WHERE UPPER(T.name) = UPPER('"+topic+"');";
	    
	    try {
			ResultSet rs = dataAccess.executeQuery(retrieveQuery);
			
			while(rs.next()){
				Question retrievedQuestion = new Question();
				retrievedQuestion.id  = rs.getInt("ID");
				retrievedQuestion.text = rs.getString("TEXT");
				retrievedQuestion.difficultylevel = rs.getInt("DIFFICULTYLEVEL");
				retrievedQuestion.explanation = rs.getString("EXPLANATION");
				retrievedQuestion.hint = rs.getString("HINT");
				retrievedQuestions.add(retrievedQuestion);
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	    return retrievedQuestions;
	}
	
	public void insertQuestionParameter()
	{
		
	}
	
	static Question getQuestionByID(long id) throws Exception {
		Connection con = dataAccess.connect();
		
		PreparedStatement stmt=con.prepareStatement(getAllQuestionsByID);  
		
		stmt.setLong(1,id);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()){
			Question q = new Question();
			q.id  = rs.getInt("ID");
			q.text = rs.getString("TEXT");
			q.difficultylevel = rs.getInt("DIFFICULTYLEVEL");
			q.explanation = rs.getString("EXPLANATION");
			q.hint = rs.getString("HINT");
			q.numberOfSets = rs.getInt("NUMBEROFSETS");
			return q;
			}else {
			con.close();
			return null;
		}
	}
	
	public static Question generateQuestion(long questionID) throws Exception {
		Question q = Question.getQuestionByID(questionID);
		Random r = new Random();
		long setID = r.nextInt(q.numberOfSets) + 1;
		q.options = Option.generateRandomOptions(questionID, setID);
		q.parameters = Parameter.getParameters(questionID, setID);
		for(Parameter p : q.parameters) {
			q.text = q.text.replaceFirst("\\?", p.value);
		}
		q.setID = setID;
		return q;
	}
	
	public static Question displayQuestion(long questionID) throws Exception {
		Question q = generateQuestion(questionID);
		System.out.println(q.text);
		for(Option o : q.options) {
			System.out.println(o.text);
		}
		return q;
	}
	
	public boolean isCorrectOption(int index) {
		Option o = this.options.get(index);
		return o.correct.toUpperCase().equalsIgnoreCase("Y");
	}
	
}
