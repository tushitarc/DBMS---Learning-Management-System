package program;
import java.sql.ResultSet;
import java.util.List;

import database.objects.Course;
import dataaccess.IDataAccess;
public abstract class Person {
	String name;
	int id;
	String userId;
	String password;
	
	static IDataAccess dataAccess;
	
	public static Person login(String userId, String password){
		dataAccess = new dataaccess.DataAccess();
		try {
			ResultSet rs = dataAccess.executeQuery("EXEC GetLoginDetails "+userId+","+password);
			if(rs.next()){
				String type = rs.getString("TYPE");
				Person p = null;
				if(type.equalsIgnoreCase("Student")){
					Student s = new Student();
					s.level = rs.getString("Level");
					Student.LOGGEDINSTUDENT=rs.getInt("Id");
					p=s;
				}
				else if(type.equalsIgnoreCase("TeachingAssistant")){
					p = new TeachingAssistant();
				}
				else if(type.equalsIgnoreCase("Instructor")){
					p = new Instructor();
				}
				p.id = rs.getInt("Id");
				p.name = rs.getString("Name");
				p.userId = rs.getString("UserId");
				return p;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public abstract void showMenu() throws Exception;
	
	public String viewProfile(int id, String type) throws Exception{
		ResultSet rs = dataAccess.executeQuery("SELECT * FROM "+type+ " WHERE Id = '"+id+"'");
		if(rs.next()){
			String userId = rs.getString("UserId");
			String name = rs.getString("Name");
			String details = "User id = "+userId+", Name = "+name;
			return details;
		}
		else{
			return "Could not find details, some error occured";
		}
	}
	
}
