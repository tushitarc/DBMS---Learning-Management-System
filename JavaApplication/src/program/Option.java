package program;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;
import database.objects.Course;

public class Option {
	static IDataAccess dataAccess = new DataAccess();
	long id,questionID,setID;
	String text,correct;
	
	static String generateRandomRightOptions = "SELECT TOP 1* ,RAND()*10 "
			+ "FROM [OPTION] "
			+ "WHERE (QuestionId=? AND SetId=? AND UPPER (Correct)='Y') "
			+ "ORDER BY RAND()*10;";
	static String generateRandomWrongOption = "SELECT TOP 3* ,RAND()*10 "
			+ "FROM [OPTION] "
			+ "WHERE (QuestionId=? AND SetId=? AND UPPER (Correct)='N') "
			+ "ORDER BY RAND()*10;";
	static String getOption = "select * from option where id = ?";
	public static void insertOption(int questionId, int setId, String text, char correct){
		String insertQuery = "INSERT INTO [OPTION] (QuestionId, SetId, Text, Correct) VALUES (? , ? , ?, ?)"; 

    	try
	    {
    		Connection conn = dataAccess.connect();
    		PreparedStatement  stmt = conn.prepareStatement(insertQuery);
    		stmt.setInt(1, questionId);
    		stmt.setInt(2, setId);
    		stmt.setString(3, text);	    	    
    		stmt.setString(4, String.valueOf(correct));
    		stmt.executeUpdate();
	    }
    	
	    catch (Exception e) {
	    	System.out.print(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<Option> generateRandomOptions(long questionID, long setID) throws Exception {
		
		ArrayList<Option> optionList = new ArrayList<Option>();
		Connection con = dataAccess.connect();	
		PreparedStatement stmt=con.prepareStatement(generateRandomRightOptions);
		stmt.setLong(1, questionID);
		stmt.setLong(2, setID);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Option o = new Option();
			o.id = rs.getLong(1);
			o.questionID = rs.getLong(2);
			o.setID = rs.getLong(3);
			o.text = rs.getString(4);
			o.correct = rs.getString(5);
			
			optionList.add(o);
		}
		
		stmt=con.prepareStatement(generateRandomWrongOption);
		stmt.setLong(1, questionID);
		stmt.setLong(2, setID);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			Option o = new Option();
			o.id = rs.getLong(1);
			o.questionID = rs.getLong(2);
			o.setID = rs.getLong(3);
			o.text = rs.getString(4);
			o.correct = rs.getString(5);
			
			optionList.add(o);
		}
		con.close();
		Collections.shuffle(optionList);
		return optionList;
	}
	
	
}
