package program;

import java.util.Scanner;

import dataaccess.DataAccess;
import dataaccess.IDataAccess;

public class Program {
	IDataAccess dataAccess;
	Scanner sc = new Scanner(System.in);
	Program(){
		dataAccess = new DataAccess();
	}
	public static void main(String[] arg){
		new Program().login();
	}
	public void login(){
		try {
			while(true){
				String userId, password;
				System.out.println("Enter user id");
				userId = sc.next();
				System.out.println("Enter password");
				password = sc.next();
				Person p = Person.login(userId, password);
				if(p == null)
					System.out.println("Could not find details, please try again");
				else{
					System.out.println("Welcome "+p.name);
					p.showMenu();
				}
			}
		}
		catch (Exception e) {
			System.out.println("Some error occured:" +e.toString());
		}
	}
}
