package program;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.objects.Course;


public class Student extends Person{
	String level;
	Scanner sc = new Scanner(System.in);
	static String selectAllQuery = "select * from Student";
	static int LOGGEDINSTUDENT;
	public void showMenu(){
		
		boolean loop = true;
		while(loop){
			System.out.println("Enter the correct choice");
			System.out.println("1. View courses");
			System.out.println("2. Take homework");
			System.out.println("3. See past homeworks-Brief Summary");
			System.out.println("4. See past homeworks-Detailed Summary");
			System.out.println("5. View past homeworks");
			System.out.println("6. View available homeworks");
			System.out.println("7. View profiles");
			System.out.println("0. Exit");
			int choice = sc.nextInt();
			switch(choice){
			case 1:
				viewCourses();
				break;
			case 2:
				try{
					new Homework().takeHomework();
				}
				catch(Exception e){
					System.out.println("Something went wrong while taking Homework, Contact TA");
					e.printStackTrace();
				}
				break;
			case 3:
				new Homework().viewPastHomeworkBriefSummary();
				break;
			case 4:
				try{
					new Homework().viewPastHomeworkCompleteSummary();
				}
				catch(Exception e){
					System.out.println("Something went wrong while fetching Homework, Contact TA");
					e.printStackTrace();
				}
				break;
			case 5:
				viewHomeworks("past");
				break;
			case 6:
				viewHomeworks("new");
				break;
			case 7:
				viewProfile();
				break;
			case 0:
				loop = false;
				System.out.println("Thank you for your time");
				break;
			default:
				System.out.println("You entered a wrong choice, please select again");
			}
		}
	}
	
	public void viewHomeworks(String pastOrNew){
		String spName = pastOrNew.equals("past") ? "getPastHomeworks" : "getAvailableHomeworks";
		Connection con;
		try {
			con = dataAccess.connect();
			PreparedStatement stmt=con.prepareStatement("EXEC " + spName +" "+this.id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String result = "Id="+rs.getInt("Id")
				+", Name"+rs.getString("Name")+", end date = "+rs.getDate("ENDDATE")
				+", max tries allowed = "+rs.getInt("MAX_TRIES")
				+", home work type = "+rs.getString("HOMEWORK_TYPE")
				+", Score selection = "+rs.getString("ScoreSelection");
				System.out.println(result);
			}
			
		} catch (Exception e) {
			System.out.println("Could not fetch homeworks "+e.getMessage());
		}
	}
	
	public void viewPastHomeworks() throws Exception{
		
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement("EXEC getPastHomeworks "+this.id);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			
		}
	}
	
	public void viewProfile(){
		
		try{
			String basicDetails = super.viewProfile(id, "Student")+"\n";
			ResultSet rs = dataAccess.executeQuery("SELECT [LEVEL] FROM Student WHERE Id = '"+id+"'");
			if(rs.next()){
				basicDetails += "Level: "+ rs.getString("level");
			}
			System.out.println(basicDetails);
		}
		catch(Exception e){
			System.out.println("Some error occured");
		}
	}
	
	public void viewCourses() {
		try {
			List<Course> courses = Course.getAllCoursesForStudent(this.id);
			for(int i=0;i<courses.size();i++){
				Course course = courses.get(i);
				String status = "Available";
				if(course.getStudentsEnrolled() >= course.getMaxEnrollment())
					status = "Full";
				System.out.println(course.getId()+","+course.getName()+","+status);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Student> getAllStudents() throws Exception{
		ArrayList<Student> allStudents = new ArrayList<Student>();
		
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement(selectAllQuery);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Student s = new Student();
			s.id = rs.getInt(1);
			s.name = rs.getString(2);
			s.level = rs.getString(5);
			s.userId = rs.getString(4);
			
			allStudents.add(s);
		}
		
		return allStudents;
	}
	
}