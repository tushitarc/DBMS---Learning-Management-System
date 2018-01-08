package database.objects;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;

public class Topic {

	static IDataAccess dataAccess = new DataAccess();
	static String selectIDQuery = "select * from Topic where id=?";
	static String insertQuery = "insert into Topic(name) values (?)";
	static String selectAllQuery = "select * from Topic";
	static String selectAllTopicsForACourseQuery="select * from TopicBelongsToCourse where courseId=?";
	
	private long id;
	private String name;
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void save() throws Exception {
		Topic t = Topic.insert(name);
		id = t.id;
	}
	
	public static Topic insert(String name) throws Exception {
		Connection con = dataAccess.connect();	
		PreparedStatement stmt=con.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		
		int affectedRows = stmt.executeUpdate();
		
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        Topic t = new Topic();
        
        try{
        	ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                t.id = generatedKeys.getLong(1);
                t.name = name;
                return t;
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }finally {
        		con.close();
        }
	}
	
	public static Topic getTopicWithID(long id) throws Exception {
		
		Connection con = dataAccess.connect();
		
		PreparedStatement stmt=con.prepareStatement(selectIDQuery);  
		
		stmt.setLong(1,id);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			Topic t = new Topic();
			t.id = rs.getInt(1);
			t.name = rs.getString(2);
			con.close();
			return t;
		}else {
			con.close();
			return null;
		}
	}
	
	public String toString() {
		return id + " " + name;
	}
	
	public static ArrayList<Topic> getAllTopics() throws Exception{
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement(selectAllQuery);
		ResultSet rs = stmt.executeQuery();
		return resultSetToTopics(rs);
		
	}
	
	public static ArrayList<Topic> resultSetToTopics(ResultSet rs) throws SQLException{
		ArrayList<Topic> allTopics = new ArrayList<Topic>();
		while(rs.next()) {
			Topic t = new Topic();
			t.id = rs.getLong(1);
			t.name = rs.getString(2);
			allTopics.add(t);
		}
		
		return allTopics;
	}
	
	public static List<Topic> getAllTopicsForACourse(Integer courseId) throws Exception{
		List<Topic> allTopics = new ArrayList<Topic>();
		Connection connection = dataAccess.connect();
		PreparedStatement sqlStatement = connection.prepareStatement(selectAllTopicsForACourseQuery);
		sqlStatement.setInt(1,courseId);
		ResultSet rs = sqlStatement.executeQuery();
		while(rs.next()){
			long topicId = rs.getLong("topicId");
			Topic t = getTopicWithID(topicId);
			allTopics.add(t);
		}
		return allTopics;
	}
}
