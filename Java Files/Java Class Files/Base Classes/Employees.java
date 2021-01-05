package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;


public class Employees {
	
	private ArrayList<PersonalInfo> EmployeeList= new ArrayList<PersonalInfo>();
	
	
	protected Employees() throws NumberFormatException, ParseException, IOException{
		
		PersonalInfo me;
		File file = new File("C:/Users/drtrt/eclipse-workspace/MyJavaFX/src/application/employeeList"); 
		Scanner sc = new Scanner(file); 
		String [] line;
		System.out.println("Attempting to read from file in: "+file.getCanonicalPath());
		
		while(sc.hasNextLine())
		{
			line = sc.nextLine().split("[,]", 0);
			me = new PersonalInfo(Integer.parseInt(line[0]),line[1],line[2],line[3],line[4],line[5], line[6], line[7], line[8], line[9], line[10]);
			addEmployee(me);
		}
		
		sc.close();
		
	}

	public ArrayList<PersonalInfo> getEmployeeList() {
		return EmployeeList;
	}
	
	public void addEmployee(PersonalInfo employee) {
		EmployeeList.add(employee);
	}
	

	public void modifyEmployee(int id) {
		//Get an employee in the list
		PersonalInfo person; 
		
		for(int i = 0; i < EmployeeList.size(); i++) {
				person = EmployeeList.get(i);
			if(person.getId() == id)
			{
				i=EmployeeList.size();
			}
			
		}
		
		//click a button to to set something for person	
	}

	public void deleteEmployee(int id)
	{
		PersonalInfo person;

		for(int i = 0; i < EmployeeList.size(); i++) {
				person = EmployeeList.get(i);
			if(person.getId() == id)
			{
				//This is where we'd add the other stuff and where to put the 
				//employee.get(personNumber) for tax reasons
				EmployeeList.remove(i);
				i= EmployeeList.size();
			}	
			
		}
	
	}

	//Delete this after testing
//	public void findEmployee(int id)
//	{
//		PersonalInfo person;
//		boolean found = false;
//	
//		for(int i = 0; i < EmployeeList.size(); i++) {
//			person = EmployeeList.get(i);
//			if(person.getId() == id)
//			{
//				found = true;	
//			}
//		
//		
//		}
//		
//		if(!found)
//			System.out.println("It was not found");
//		else
//			System.out.println("It was found");
//	
//	}
	
	
//	public static void main(String[] args) throws NumberFormatException, ParseException, IOException{
//		//When the company initiates the employees are immediately adds into the employeeList
//		Employees company = new Employees();
//		for(int i = 0; i < company.getEmployeeList().size(); i++) {
//			System.out.println(company.getEmployeeList().get(i));
//		}
//	}

}
