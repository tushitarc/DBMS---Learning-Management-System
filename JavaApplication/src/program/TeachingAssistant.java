package program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.objects.Course;

public class TeachingAssistant extends Person{
	static Scanner sc = new Scanner(System.in);
	static String selectAllQuery = "select * from TeachingAssistant";
	
	@Override
	public void showMenu() throws Exception {
		boolean loop = true;
		while(loop){
			System.out.println("Enter the correct choice");
			System.out.println("1. View students");
			System.out.println("2. View courses");
			System.out.println("3. Enroll student for a course");
			System.out.println("4. Drop student from a course");
			System.out.println("6. View profile");
			System.out.println("7. View Course Report");
			System.out.println("8. View Homeworks by course");
			System.out.println("9. View brief summary of past homework for a student");
			System.out.println("10 View complete summary of past homework for a student");
			System.out.println("0. Exit");
			int choice = sc.nextInt();
			switch(choice){
			case 1:
				viewStudents();
				break;
			case 2:
				viewCourses();
				break;
			case 3:
				enrollStudent();
				break;
			case 4:
				dropStudent();
				break;
			case 5:
				break;
			case 6:
				viewProfile();
				break;
			case 7:
				viewReport();
				break;
			case 8:
				new Homework().ViewHomeworksbyCourse();
				break;
			case 9:
				new Homework().viewPastHomeworkBriefSummary();
				break;
			case 10:
				new Homework().viewPastHomeworkCompleteSummary();
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
	
	public void viewReport(){
		System.out.println("Enter course id");
		int courseId = sc.nextInt();
		new StudentHomework().viewReportForTA(courseId, this.id);
	}
	
	public void viewCourses() {
		try {
			List<Course> courses = Course.getAllCoursesForTeachingAssistant(this.id);
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
	
		public void viewProfile(){
			try{
				String basicDetails = super.viewProfile(id, "TeachingAssistant");
				ResultSet rs = dataAccess.executeQuery("EXEC getCoursesForTA '"+id+"'");
				basicDetails += ".\nCourses assigned: ";
				if(rs.next()){
					basicDetails = basicDetails + rs.getString("CourseName") +",";
				}
				System.out.println(basicDetails);
			}
			catch(Exception e){
				System.out.println("Some error occured");
			}
		}
	
	public void enrollStudent() {
		System.out.println("Enter student id");
		int studentId = sc.nextInt();
		System.out.println("Enter course id");
		int courseId = sc.nextInt();
		try{
			dataAccess.executeQueryWithoutResult("EXEC enroll "+studentId+","+courseId);
			System.out.println("Successfully enrolled for the course.");
		}
		catch(Exception e){
			System.out.println("Could not enroll student: "+e.getMessage());
		}
	}
	
	public void dropStudent(){
		System.out.println("Enter student id");
		int studentId = sc.nextInt();
		System.out.println("Enter course id");
		int courseId = sc.nextInt();
		try{
			dataAccess.executeQueryWithoutResult("EXEC dropStudent "+studentId+","+courseId);
			System.out.println("Successfully dropped student from the course if enrolled.");
		}
		catch(Exception e){
			System.out.println("Some error occured. Please check." + e.toString());
		}
	}
	
	public void viewStudents() throws Exception{
		List<Student> allStudents = Student.getAllStudents();
		for(int i=0;i<allStudents.size();i++){
			Student s = allStudents.get(i);
			System.out.println(s.id+","+s.name);
		}
	}

	public static List<TeachingAssistant> getAllTeachingAssistant() throws Exception {
		ArrayList<TeachingAssistant> allTeachingAssistants = new ArrayList<TeachingAssistant>();
		
		Connection con = dataAccess.connect();
		PreparedStatement stmt=con.prepareStatement(selectAllQuery);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			TeachingAssistant ta = new TeachingAssistant();
			ta.id = rs.getInt("Id");
			ta.name = rs.getString("Name");
			ta.userId = rs.getString("UserId");
			
			allTeachingAssistants.add(ta);
		}
		
		return allTeachingAssistants;
	}
	
}
