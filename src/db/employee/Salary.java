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
			System.out.println("\n[�޿�����]");
			do {
				System.out.print("1.���� 2.���� 3.���� 4.��������Ʈ 5.����˻� 6.����Ʈ 7.�������Ʈ 8.���� => ");
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
		System.out.println("\n�޿� ����...");
		
	}
	
	public void update() {
		System.out.println("\n�޿� ����...");
		
	}

	public void delete() {
		System.out.println("\n�޿� ����...");
		
	}

	public void searchSabeon() {
		System.out.println("\n��� �˻�...");
		
		
	}

	public void monthList() {
		System.out.println("\n���� ����Ʈ...");
		
	}
	
	public void list() {
		System.out.println("\n�޿� ����Ʈ...");
	
	}
}
