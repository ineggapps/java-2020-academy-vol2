package db.employee;

import java.util.Scanner;

import com.util.DBConn;

public class App {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		Employee emp=new Employee();
		Salary sal=new Salary(emp);
		
		System.out.println("�λ���� ���α׷�");
		
		int ch;
		while(true) {
			System.out.println("\n[Main]");
			do {
				System.out.print("1.������� 2.�޿����� 3.���� => ");
				ch = sc.nextInt();
			}while(ch<1||ch>3);
			
			if(ch==3) break;
			
			switch(ch) {
			case 1:emp.employeeManage();break;
			case 2:sal.salaryManage();break;
			}
		}

		sc.close();
		DBConn.close(); 
	}
}
