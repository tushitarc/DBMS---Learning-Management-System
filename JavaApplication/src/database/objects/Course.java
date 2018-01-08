package database.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;
import database.objects.Topic;


public class Course {
	
	static IDataAccess dataAccess = new DataAccess();
	static String selectIDQuery = "select * from Course where id=?";
	static String insertQuery = "insert into Course(name,startDate,endDate,instructorID,studentsEnrolled,maxEnrollment, CourseCode) values (?,?,?,?,?,?,?)";
	static String selectAllQuery = "select * from Course";
	static String addTopic = "insert into TopicBelongsToCourse values(?,?)";
	static String getAllTopics = "select T.* from Course C"
			+ "inner join TopicBelongsToCourse TBC"
			+ "on C.id = TBC.courseId"
			+ "inner join Topic T"
			+ "on T.id = TBC.topicID";
	
	private long id;
	private String name;
	private Date startDate;
	private Date endDate;
	private long instructructorID;
	private int studentsEnrolled;
	private int maxEnrollment;
	
	public int getStudentsEnrolled() {
		return studentsEnrolled;
	}
	public void setStudentsEnrolled(int studentsEnrolled) {
		this.studentsEnrolled = studentsEnrolled;
	}
	public int getMaxEnrollment() {
		return maxEnrollment;
	}
	public void setMaxEnrollment(int maxEnrollment) {
		this.maxEnrollment = maxEnrollment;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getInstructructorID() {
		return instructructorID;
	}
	public void setInstructructorID(long instructructorID) {
		this.instructructorID = instructructorID;
	}
	
	public static Course insert(String name, Date startDate, Date endDate,long instructorID,int maxEnrollment, String courseCode) throws Exception {
		Connection con = dataAccess.connect();	
		PreparedStatement stmt=con.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		stmt.setDate(2, startDate);
		stmt.setDate(3, endDate);
		stmt.setLong(4, instructorID);
		stmt.setInt(5, 0);
		stmt.setInt(6, maxEnrollment);
		stmt.setString(7, courseCode);
		
		int affectedRows = stmt.executeUpdate();
		
        if (affectedRows == 0) {
            throw new SQLException("Creating course failed, no rows affected.");
        }

        Course c = new Course();
        
        try{
        	ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                c.id = generatedKeys.getLong(1);
                c.name = name;
                c.startDate = startDate;
                c.endDate = endDate;
                c.instructructorID = instructorID;
                c.maxEnrollment = maxEnrollment;
                	c.studentsEnrolled = 0;
                return c;
            }
            else {
                throw new SQLException("Creating course failed, no ID obtained.");
            }
        }finally {
        		con.close();
        }
	}
	
	public static Course getCourseWithID(long id) throws Exception {
		
		Connection con = dataAccess.connect();
		
		PreparedStatement stmt=con.prepareStatement(selectIDQuery);  
		
		stmt.setLong(1,id);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			Course c = new Course();
			c.id = rs.getLong(1);
			c.name = rs.getString(2);
			c.startDate = new Date(rs.getTimestamp(3).getTime());
			c.endDate = new Date(rs.getTimestamp(4).getTime());
			c.instructructorID = rs.getLong(5);
			c.studentsEnrolled = rs.getInt(7);
			c.maxEnrollment = rs.getInt(6);
			con.close();
			return c;
		}else {
			con.close();
			return null;
		}
	}
	
	public String toString() {
		return id + " " + name + " " + startDate + " " + endDate + " " + instructructorID;
	}
	
	public static ArrayList<Course> getAllCoursesForInstructor(int instructor_id) throws Exception{
		ArrayList<Course> allCourses = new ArrayList<Course>();
		
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement("EXEC getCoursesForInstructor "+instructor_id);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Course c = new Course();
			c.id = rs.getLong(1);
			c.name = rs.getString(2);
			c.startDate = new Date(rs.getTimestamp(3).getTime());
			c.endDate = new Date(rs.getTimestamp(4).getTime());
			c.instructructorID = rs.getLong(5);
			c.studentsEnrolled = rs.getInt(7);
			c.maxEnrollment = rs.getInt(6);
			
			allCourses.add(c);
		}
		
		return allCourses;
	}
	
	public static ArrayList<Course> getAllCoursesForTeachingAssistant(int ta_id) throws Exception{
		ArrayList<Course> allCourses = new ArrayList<Course>();
		
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement("EXEC getCoursesForTeachingAssistant "+ta_id);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Course c = new Course();
			c.id = rs.getLong(1);
			c.name = rs.getString(2);
			c.startDate = new Date(rs.getTimestamp(3).getTime());
			c.endDate = new Date(rs.getTimestamp(4).getTime());
			c.instructructorID = rs.getLong(5);
			c.studentsEnrolled = rs.getInt(7);
			c.maxEnrollment = rs.getInt(6);
			
			allCourses.add(c);
		}
		
		return allCourses;
	}
	
	public static ArrayList<Course> getAllCoursesForStudent(int student_id) throws Exception{
		ArrayList<Course> allCourses = new ArrayList<Course>();
		
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement("EXEC getAllCoursesForStudent "+student_id);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Course c = new Course();
			c.id = rs.getLong(1);
			c.name = rs.getString(2);
			c.startDate = new Date(rs.getTimestamp(3).getTime());
			c.endDate = new Date(rs.getTimestamp(4).getTime());
			c.instructructorID = rs.getLong(5);
			c.studentsEnrolled = rs.getInt(7);
			c.maxEnrollment = rs.getInt(6);
			
			allCourses.add(c);
		}
		
		return allCourses;
	}
	
	public ArrayList<Topic> addTopic(Topic topic) throws Exception{
		Connection con = dataAccess.connect();
		PreparedStatement stmt = con.prepareStatement(addTopic);
		stmt.setLong(1,topic.getId());
		stmt.setLong(2, this.id);
		ResultSet rs = stmt.executeQuery();
		return Topic.resultSetToTopics(rs);
	}
	
	
	
}
