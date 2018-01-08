package program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;

//TODO put unqiue constraint on question_id and sth_id
public class StudentHomeworkDetail {
	//TODO add FK for question_id and option_id as well
	int question_id;
	//the option that he chose out of 4 options and not the actual option_id in option table
	int option_id;
	int score;
	boolean correct;
	String hint;
	//foreign key reference to the student takes homework
	int sth_id;
	
	//list of all questions attempted in the homework
	public List<StudentHomeworkDetail> getStudentHWdetails(Integer sthId){
		String getSthwquery="Select * from StudentHomeworkDetail where sth_id="+sthId;
		List<StudentHomeworkDetail> shdList=new ArrayList<StudentHomeworkDetail>();
		try{
			IDataAccess dataAccess=new DataAccess();
			ResultSet rs=dataAccess.executeQuery(getSthwquery);
			StudentHomeworkDetail shd=new StudentHomeworkDetail();
			while(rs.next()){
				shd.question_id=rs.getInt("question_id");
				shd.option_id=rs.getInt("option_id");
				shd.score=rs.getInt("score");
				shd.correct=rs.getBoolean("correct");
				shd.hint=rs.getString("hint");
				shd.sth_id=rs.getInt("sth_id");
				shdList.add(shd);
			}
			return shdList;
		}
		catch(Exception e){
			System.out.println("Something went wrong, Please try again");
			e.printStackTrace();
			return null;
		}
	}
	

	public void saveStudentHomeworkDetail(StudentHomeworkDetail shd){
		String insertQuery="insert into StudentHomeworkDetail(question_id,option_id,score,correct,hint,sth_id) values (?,?,?,?,?,?)";
		try{
			IDataAccess dataAccess=new DataAccess();
			//dataAccess.executeQueryWithoutResult(saveSthwquery);
			Connection con = dataAccess.connect();	
			PreparedStatement stmt=con.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, shd.question_id);
			stmt.setInt(2, shd.option_id);
			stmt.setInt(3, shd.score);
			stmt.setBoolean(4, shd.correct);
			stmt.setString(5, shd.hint);
			stmt.setInt(6, shd.sth_id);
			
			stmt.executeUpdate();
		
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Something went wrong, Please try again");
		}
	}

	@Override
	public String toString() {
		return "StudentHomeworkDetail [question_id=" + question_id + ", option_id=" + option_id + ", score=" + score
				+ ", correct=" + correct + ", hint=" + hint + ", sth_id=" + sth_id + "]";
	}
	
}
