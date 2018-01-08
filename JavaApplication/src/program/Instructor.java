package program;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import database.objects.Course;
import program.Option;
import program.Parameter;
import program.Person;
import program.Question;
import program.Student;
import program.TeachingAssistant;


public class Instructor extends Person{
	Scanner sc = new Scanner(System.in);
	public void showMenu() throws Exception {
		boolean loop = true;
		while(loop){
			System.out.println("Enter the correct choice");
			System.out.println("1. View students");
			System.out.println("2. View courses");
			System.out.println("3. Enroll student for a course");
			System.out.println("4. View teaching assistants");
			System.out.println("5. Add a teaching assistant to a course");
			System.out.println("6. Drop a student from a course");
			System.out.println("7. View profile");
			System.out.println("8. Add course");
			System.out.println("9. Add a question");
			System.out.println("10. View Course Report");
			System.out.println("11. View Homework Exercise Menu"); 
			System.out.println("12. Add a new teaching assistant");
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
				viewTeachingAssistants();
				break;
			case 5:
				addTeachingAssistant();
				break;
			case 6:
				dropStudent();
				break;
			case 7: 
				viewProfile();
				break;
			case 8:
				addCourse();
				break;
			case 9:
				addQuestion();
				break;
			case 10:
				viewReport();
				break;
			case 11:
				new Homework().showHWMenuForInstructor();
				break;
			case 12:
				addNewTA();
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
		new StudentHomework().viewReportForInstructor(courseId, this.id);
	}
	
	public void viewCourses() {
		try {
			List<Course> courses = Course.getAllCoursesForInstructor(this.id);
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
	
	public void addQuestion(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int questionId = -1;
		try{
			questionId = Question.insertQuestion();
			Question q = Question.getQuestionByID(questionId);
			
			System.out.println("Enter number of parameters");
			int numberOfParams = Integer.parseInt(br.readLine());
			for(int i=1;i<=q.numberOfSets;i++){
				for(int j=1;j<=numberOfParams;j++){
					System.out.println(String.format("Enter value for set %d, parameter %d",i, j));
					Parameter.insertParameter(questionId, i, j, br.readLine());
				}
				System.out.println("Enter number of options for this set");
				int numberOfOptions = Integer.parseInt(br.readLine());
				for(int o = 1; o<=numberOfOptions;o++){
					System.out.println("Enter value for option "+o);
					String value = br.readLine();
					System.out.println("Enter Y if this option is correct. Else enter N");
					char correct = (char)br.readLine().charAt(0);
					Option.insertOption(questionId, i, value, correct);
				}
			}
		}
		catch(Exception e){
			System.out.println("Some error occured. Please choose again: "+e.toString());
			try{
				Question.deleteQuestion(questionId);
			}
			catch(Exception ex){
				System.out.println("Could not delete question. Corrupt data in the system. Please delete manually. "+ex.getMessage());
			}
		}
		
	}
	
	public void addCourse(){
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter course name");
		String name = br.readLine();
		System.out.println("Enter start date in MM/dd/yyyy format");
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date startDate = formatter.parse(sc.next());
		System.out.println("Enter end date in MM/dd/yyyy format");
		Date endDate = formatter.parse(sc.next());
		System.out.println("Enter max students allowed to enroll");
		int maxEnrollment = sc.nextInt();
		System.out.println("Enter course code");
		String courseCode = sc.next();
			Course.insert(name, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()), (long)this.id, maxEnrollment, courseCode);
			System.out.println("Successfully added course.");
		}
		catch(Exception ex){
			System.out.println("Some error occured: "+ex.toString());
		}
	}
	
	
	
	public void viewProfile(){
		try{
			String basicDetails = super.viewProfile(id, "Instructor");
			ResultSet rs = dataAccess.executeQuery("EXEC getCoursesForInstructor '"+id+"'");
			basicDetails += ".\nCourses created and taught: ";
			while(rs.next()){
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
			dataAccess.executeQueryWithoutResult("EXEC enroll "+studentId+","+courseId+","+this.id);
			System.out.println("Successfully enrolled for the course.");
		}
		catch(Exception e){
			System.out.println("Could not enroll student: "+e.getMessage());
		}
	}
	
	public void viewStudents() throws Exception{
		List<Student> allStudents = Student.getAllStudents();
		for(int i=0;i<allStudents.size();i++){
			Student s = allStudents.get(i);
			System.out.println(s.id+","+s.name);
		}
	}
	
	public void addTeachingAssistant() throws Exception{
		System.out.println("Enter TA id");
		int taId = sc.nextInt();
		System.out.println("Enter course id");
		int courseId = sc.nextInt();
		try{
			dataAccess.executeQueryWithoutResult("EXEC addTA "+taId+","+courseId);
			System.out.println("Successfully added TA for the course.");
		}
		catch(Exception e){
			System.out.println("Some issue occured. Could not add TA for the course. "+e.getMessage());
		}
			
	}
	
	public void addNewTA() throws Exception{
		System.out.println("Enter student id");
		int sid = sc.nextInt();
		try{
			dataAccess.executeQueryWithoutResult("EXEC addNewTA "+sid);
			System.out.println("Successfully added new TA. Username is appended with ta_");
		}
		catch(Exception e){
			System.out.println("The following error occured. "+e.getMessage());
		}
	}
	
	public void viewTeachingAssistants() throws Exception{
		List<TeachingAssistant> allTeachingAssistant = TeachingAssistant.getAllTeachingAssistant();
		for(int i=0;i<allTeachingAssistant.size();i++){
			TeachingAssistant ta = allTeachingAssistant.get(i);
			System.out.println(ta.id+","+ta.name);
		}
	}
	
	public void dropStudent(){
		System.out.println("Enter student id");
		int studentId = sc.nextInt();
		System.out.println("Enter course id");
		int courseId = sc.nextInt();
		try{
			dataAccess.executeQueryWithoutResult("EXEC dropStudent "+studentId+","+courseId+","+this.id);
			System.out.println("Successfully dropped student from the course if enrolled.");
		}
		catch(Exception e){
			System.out.println("Some error occured. Please check." + e.getMessage());
		}
	}
}
