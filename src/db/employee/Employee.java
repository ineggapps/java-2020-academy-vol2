package db.employee;

import java.util.Scanner;

public class Employee {
	private Scanner sc=new Scanner(System.in);
	private EmployeeDAO dao=new EmployeeDAO();
	
	public void employeeManage() {
		int ch;
		while(true) {
			System.out.println("\n[사원관리]");
			do {
				System.out.print("1.사원등록 2.정보수정 3.사번검색 4.이름검색 5.리스트 6.메인 => ");
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
		System.out.println("\n사원 등록...");
		
	}
	
	public void update() {
		System.out.println("\n사원 정보 수정...");
		
	}
	
	public void searchSabeon() {
		System.out.println("\n사번 검색...");
		
	}

	public void searchName() {
		System.out.println("\n이름 검색...");
		
	}
	
	public void list() {
		System.out.println("\n사원 리스트...");

	}
	
}
