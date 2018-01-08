package program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;

/**
 * @author himani
 *
 */
public class StudentHomework{

	int id;
	/*Student_id and hw_id combination should be unique*/
	int student_id;
	int hw_id;
	int score;
	int attempt;
	static IDataAccess dataAccess = new DataAccess();
	
	public List<StudentHomework> getStudentHomework(Integer studentId,Integer hwId){
		String getQuery="Select * from StudentHomework where student_id=? and hw_id=?";
		
		List<StudentHomework> sthList=new ArrayList<StudentHomework>();
		
		try{
			IDataAccess dataAccess=new DataAccess();
			Connection con = dataAccess.connect();
			
			PreparedStatement stmt=con.prepareStatement(getQuery);  
			
			stmt.setInt(1,studentId);
			stmt.setInt(2,hwId );
			ResultSet rs = stmt.executeQuery();
			StudentHomework sth=new StudentHomework();
			while(rs.next()){
				sth.id=rs.getInt("id");
				sth.student_id=rs.getInt("student_id");
				sth.hw_id=rs.getInt("hw_id");
				sth.score=rs.getInt("score");
				sth.attempt=rs.getInt("attempt");
				sthList.add(sth);
			}
			return sthList;
		}
		catch(Exception e){
			System.out.println("Something went wrong, Please try again");
			e.printStackTrace();
			return null;
		}
	}
	
	public StudentHomework getStudentHomework(Integer studentId,Integer hwId,Integer attempt){
		String getQuery="Select * from StudentHomework where student_id=? and hw_id=? and attempt=?";
		StudentHomework sth=new StudentHomework();
		try{
			IDataAccess dataAccess=new DataAccess();
			Connection con = dataAccess.connect();
			PreparedStatement stmt=con.prepareStatement(getQuery);  
			stmt.setInt(1,studentId);
			stmt.setInt(2,hwId );
			stmt.setInt(3, attempt);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				sth.id=rs.getInt("id");
				sth.student_id=rs.getInt("student_id");
				sth.hw_id=rs.getInt("hw_id");
				sth.score=rs.getInt("score");
				sth.attempt=rs.getInt("attempt");				
			}
			return sth;
		}
		catch(Exception e){
			System.out.println("Something went wrong, Please try again");
			e.printStackTrace();
			return null;
		}
	}
	//Get student takes homework for a particular id
	public StudentHomework getStudentHomework(Integer sthwId){
		String getSthwquery="Select * from StudentHomework where id="+sthwId;
		List<StudentHomework> sthList=new ArrayList<StudentHomework>();
		try{
			IDataAccess dataAccess=new DataAccess();
			ResultSet rs=dataAccess.executeQuery(getSthwquery);
			StudentHomework sth=new StudentHomework();
			while(rs.next()){
				sth.id=rs.getInt("id");
				sth.student_id=rs.getInt("student_id");
				sth.hw_id=rs.getInt("hw_id");
				sth.score=rs.getInt("score");
				sth.attempt=rs.getInt("attempt");	
				sthList.add(sth);
			}
			if(sthList!=null)
			return sthList.get(0);
			else
				return null;
		}
		catch(Exception e){
			System.out.println("Something went wrong, Please try again");
			e.printStackTrace();
			return null;
		}
	}
	/*get the already existing response if it exists, if it does not then save new entry
	 * if it does just increase number of attempts
	 * if number of attempts are getting exceeded, throw exception
	 */
	public void saveStudentHomework(StudentHomework sthw) throws Exception{
		List<StudentHomework> shwlist=getStudentHomework(sthw.student_id,sthw.hw_id);
		Homework relatedHomework=new Homework().RetrieveHomeworkByID(sthw.hw_id);
		if(relatedHomework.maxTries<=shwlist.size())
			throw new Exception("You cannot attempt homework more than"+relatedHomework.maxTries+"times");
		System.out.println(shwlist.size());
		int attempt=shwlist.size()+1;
		String saveSthwquery="Insert into StudentHomework (student_id,hw_id,score,attempt) "
				+ "values("+ sthw.student_id+","+
				 sthw.hw_id+","+ sthw.score+","+ attempt+
				 ")";
		try{
			IDataAccess dataAccess=new DataAccess();
			dataAccess.executeQueryWithoutResult(saveSthwquery);
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Something went wrong, Please try again");
		}
	}
	//update score
	public void updateStudentHomework(Integer id,Integer score) throws Exception{
		String updateQuery="Update StudentHomework set score="+score+
				"where id="+id;
		try{
			IDataAccess dataAccess=new DataAccess();
			dataAccess.executeQueryWithoutResult(updateQuery);
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Something went wrong, Please try again");
		}
	}
	
	public void viewReportForTA(int courseId, int taId){
		Connection con;
		try {
			con = dataAccess.connect();
			PreparedStatement stmt=con.prepareStatement("EXEC getAllHomeworksForCourseForTA "+courseId+","+taId);
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("Homeworks in the course");
			while(rs.next()) {
				String result = "Id="+rs.getInt("Id")
				+", Name"+rs.getString("Name")+", end date = "+rs.getDate("ENDDATE")
				+", max tries allowed = "+rs.getInt("MAX_TRIES")
				+", home work type = "+rs.getString("HOMEWORK_TYPE")
				+", Score selection = "+rs.getString("ScoreSelection");
				System.out.println(result);
			}
			
			System.out.println("");
			System.out.println("Performances in the course");
			
			stmt=con.prepareStatement("EXEC getScoreForAllHomeworks "+courseId);
			rs = stmt.executeQuery();
			System.out.println("HW id, homework name, student id, student name, policy based score");
			while(rs.next()) {
				System.out.println(rs.getInt("HWID")+","+rs.getString("HWName")+","+rs.getInt("StudentID")+","+rs.getString("StudentName")+","+rs.getFloat("SCORE"));
			}
			
		} catch (Exception e) {
			System.out.println("Could not fetch homeworks "+e.getMessage());
		}
	}

	public void viewReportForInstructor(int courseId, int instructorId){
		Connection con;
		try {
			con = dataAccess.connect();
			PreparedStatement stmt=con.prepareStatement("EXEC getAllHomeworksForCourse "+courseId+","+instructorId);
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("Homeworks in the course");
			while(rs.next()) {
				String result = "Id="+rs.getInt("Id")
				+", Name"+rs.getString("Name")+", end date = "+rs.getDate("ENDDATE")
				+", max tries allowed = "+rs.getInt("MAX_TRIES")
				+", home work type = "+rs.getString("HOMEWORK_TYPE")
				+", Score selection = "+rs.getString("ScoreSelection");
				System.out.println(result);
			}
			
			System.out.println("");
			System.out.println("Performances in the course");
			
			stmt=con.prepareStatement("EXEC GetScoreForAllHomeworks "+courseId);
			//rs = dataAccess.executeQuery("EXEC GetScoreForAllHomeworks "+courseId);
			rs = stmt.executeQuery();
			System.out.println("HW id, homework name, student id, student name, policy based score");
			while(rs.next()) {
				System.out.println(rs.getInt("HWID")+","+rs.getString("HWName")+","+rs.getInt("StudentID")+","+rs.getString("StudentName")+","+rs.getFloat("SCORE"));
			}
			
		} catch (Exception e) {
			System.out.println("Could not fetch homeworks/performances "+e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "Your Id=" + student_id + ", Homework Id=" + hw_id + ", Your score=" + score
				+ ", Your attempt no=" + attempt ;
	}
	
	
}
