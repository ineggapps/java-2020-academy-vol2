package db.employee;

import java.util.Scanner;

public class Employee {
	private Scanner sc=new Scanner(System.in);
	private EmployeeDAO dao=new EmployeeDAO();
	
	public void employeeManage() {
		int ch;
		while(true) {
			System.out.println("\n[�������]");
			do {
				System.out.print("1.������ 2.�������� 3.����˻� 4.�̸��˻� 5.����Ʈ 6.���� => ");
				ch = sc.nextInt();
			}while(ch<1||ch>6);
			
			if(ch==6) return;
			
			switch(ch) {
			case 1: insert(); break;
			case 2: update(); break;
			case 3: searchSabeon(); break;
			case 4: searchName(); break;
			case 5: list(); break;
			}
		}
	}
	
	public void insert() {
		System.out.println("\n��� ���...");
		
	}
	
	public void update() {
		System.out.println("\n��� ���� ����...");
		
	}
	
	public void searchSabeon() {
		System.out.println("\n��� �˻�...");
		
	}

	public void searchName() {
		System.out.println("\n�̸� �˻�...");
		
	}
	
	public void list() {
		System.out.println("\n��� ����Ʈ...");

	}
	
}
