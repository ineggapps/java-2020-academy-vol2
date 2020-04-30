package db.employee;

import java.util.List;
import java.util.Scanner;

public class Employee {
	private Scanner sc = new Scanner(System.in);
	private EmployeeDAO dao = new EmployeeDAO();

	public void employeeManage() {
		int ch;
		while (true) {
			System.out.println("\n[사원관리]");
			do {
				System.out.print("1.사원등록 2.정보수정 3.사번검색 4.이름검색 5.리스트 6.메인 => ");
				ch = sc.nextInt();
			} while (ch < 1 || ch > 6);

			if (ch == 6)
				return;

			switch (ch) {
			case 1:
				insert();
				break;
			case 2:
				update();
				break;
			case 3:
				searchSabeon();
				break;
			case 4:
				searchName();
				break;
			case 5:
				list();
				break;
			}
		}
	}

	public void insert() {
		System.out.println("\n사원 등록...");

		EmployeeDTO dto = new EmployeeDTO();
		try {
			System.out.print("사원번호 > ");
			String sabeon = sc.next();
			
			if(dao.readEmployee(sabeon)!=null) {
				System.out.println("이미 존재하는 사번입니다.");
				return;
			}
			
			dto.setSabeon(sabeon);

			System.out.print("이름 > ");
			dto.setName(sc.next());

			System.out.print("생일 >");
			dto.setBirth(sc.next());

			System.out.print("전화번호 >");
			dto.setTel(sc.next());

			dao.insertEmployee(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update() {
		System.out.println("\n사원 정보 수정...");

		try {
			EmployeeDTO dto = null;
			System.out.print("수정할 사원번호 > ");
			String sabeon = sc.next();
			dto = dao.readEmployee(sabeon);
			if (dto == null) {
				System.out.println("사원번호를 잘못 입력하셨습니다. (" + sabeon + ")");
				return;
			}
			printDTO(dto);

			System.out.print("수정할 이름 > ");
			dto.setName(sc.next());

			System.out.print("수정할 생일> ");
			dto.setBirth(sc.next());

			System.out.print("수정할 전화번호 > ");
			dto.setTel(sc.next());

			int result = dao.updateEmployee(dto);

			if (result >= 1) {
				System.out.println("사원 정보가 수정되었습니다.");
				printDTO(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchSabeon() {
		System.out.println("\n사번 검색...");
		
		try {
			System.out.print("사번 입력 > ");
			String sabeon = sc.next();
			EmployeeDTO dto = dao.readEmployee(sabeon);
			if(dto==null) {
				System.out.println("검색 결과가 없습니다. ("+ sabeon + ")");
				return;
			}
			printDTO(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchName() {
		System.out.println("\n이름 검색...");

		try {
			System.out.print("검색할 이름 > ");
			String name = sc.next();
			List<EmployeeDTO> list= dao.listEmployee(name);
			if(list==null || list.size()==0) {
				System.out.println("검색 결과가 없습니다.");
				return;
			}
			printAllDTO(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void list() {
		System.out.println("\n사원 리스트...");
		printAllDTO(dao.listEmployee());
	}
	
	private void printAllDTO(List<EmployeeDTO> list) {
		for(EmployeeDTO dto: list) {
			printDTO(dto);
		}
	}

	private void printDTO(EmployeeDTO dto) {
		if (dto == null) {
			System.out.println("DTO is NULL");
			return;
		}
		System.out.print("[");
		System.out.print(dto.getSabeon() + ", ");
		System.out.print(dto.getName() + ", ");
		System.out.print(dto.getBirth() + ", ");
		System.out.print(dto.getTel() + "]");
		System.out.println();
	}

}
