package program;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;
import database.objects.Course;


public class Parameter {
	static IDataAccess dataAccess = new DataAccess();
	
	long id, questionID, setID;
	String value;
	
	public static String getParameters = "Select * from Parameter where QuestionId = ? and SetId = ?"; 
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(long questionID) {
		this.questionID = questionID;
	}

	public long getSetID() {
		return setID;
	}

	public void setSetID(long setID) {
		this.setID = setID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getGetParameters() {
		return getParameters;
	}

	public static void setGetParameters(String getParameters) {
		Parameter.getParameters = getParameters;
	}

	public static void insertParameter(int questionId, int setId, int paramId, String value){
		String insertQuery = "INSERT INTO Parameter (QuestionId, SetId, Id, Value) VALUES (? , ? , ? , ? )"; 

    	try
	    {
    		Connection conn = dataAccess.connect();
    		PreparedStatement  stmt = conn.prepareStatement(insertQuery);
    		stmt.setInt(1, questionId);
    		stmt.setInt(2, setId);
    		stmt.setInt(3, paramId);
    		stmt.setString(4, value);	    	    
    		
    		stmt.executeUpdate();
	    }
    	
	    catch (Exception e) {
	    	System.out.print(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<Parameter> getParameters(long questionID, long setID)throws Exception{
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		Connection conn = dataAccess.connect();
		PreparedStatement  stmt = conn.prepareStatement(getParameters);
		stmt.setLong(1, questionID);
		stmt.setLong(2, setID);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Parameter p = new Parameter();
			p.questionID = rs.getLong(1);
			p.setID = rs.getLong(2);
			p.id = rs.getLong(3);
			p.value = rs.getString(4);
			
			parameters.add(p);
		}
		return parameters;
	}
	
}
