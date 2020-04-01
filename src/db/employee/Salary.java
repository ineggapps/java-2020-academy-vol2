package db.employee;

import java.util.Scanner;

public class Salary {
	private Scanner sc=new Scanner(System.in);
	private SalaryDAO dao=new SalaryDAO();
	private Employee emp=null;
	
	public Salary(Employee emp) {
		this.emp = emp;
	}
	
	public void salaryManage() {
		int ch;
		while(true) {
			System.out.println("\n[급여관리]");
			do {
				System.out.print("1.지급 2.수정 3.삭제 4.월별리스트 5.사번검색 6.리스트 7.사원리스트 8.메인 => ");
				ch = sc.nextInt();
			}while(ch<1||ch>8);
			
			if(ch==8) return;
			
			switch(ch) {
			case 1:payment(); break;
			case 2:update(); break;
			case 3:delete(); break;
			case 4:monthList(); break;
			case 5:searchSabeon(); break;
			case 6:list(); break;
			case 7:emp.list(); break;
			}
		}
	}
	
	public void payment() {
		System.out.println("\n급여 지급...");
		
	}
	
	public void update() {
		System.out.println("\n급여 수정...");
		
	}

	public void delete() {
		System.out.println("\n급여 삭제...");
		
	}

	public void searchSabeon() {
		System.out.println("\n사번 검색...");
		
		
	}

	public void monthList() {
		System.out.println("\n월별 리스트...");
		
	}
	
	public void list() {
		System.out.println("\n급여 리스트...");
	
	}
}
