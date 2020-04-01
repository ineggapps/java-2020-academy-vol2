package db.employee;

import java.util.List;
import java.util.Scanner;

public class Employee {
	private Scanner sc = new Scanner(System.in);
	private EmployeeDAO dao = new EmployeeDAO();

	public void employeeManage() {
		int ch;
		while (true) {
			System.out.println("\n[�������]");
			do {
				System.out.print("1.������ 2.�������� 3.����˻� 4.�̸��˻� 5.����Ʈ 6.���� => ");
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
		System.out.println("\n��� ���...");

		EmployeeDTO dto = new EmployeeDTO();
		try {
			System.out.print("�����ȣ > ");
			dto.setSabeon(sc.next());

			System.out.print("�̸� > ");
			dto.setName(sc.next());

			System.out.print("���� >");
			dto.setBirth(sc.next());

			System.out.print("��ȭ��ȣ >");
			dto.setTel(sc.next());

			dao.insertEmployee(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update() {
		System.out.println("\n��� ���� ����...");

		try {
			EmployeeDTO dto = null;
			System.out.print("������ �����ȣ > ");
			String sabeon = sc.next();
			dto = dao.readEmployee(sabeon);
			if (dto == null) {
				System.out.println("�����ȣ�� �߸� �Է��ϼ̽��ϴ�. (" + sabeon + ")");
				return;
			}
			printDTO(dto);

			System.out.print("������ �̸� > ");
			dto.setName(sc.next());

			System.out.print("������ ����> ");
			dto.setBirth(sc.next());

			System.out.print("������ ��ȭ��ȣ > ");
			dto.setTel(sc.next());

			int result = dao.updateEmployee(dto);

			if (result >= 1) {
				System.out.println("��� ������ �����Ǿ����ϴ�.");
				printDTO(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchSabeon() {
		System.out.println("\n��� �˻�...");
		
		try {
			System.out.print("��� �Է� > ");
			String sabeon = sc.next();
			EmployeeDTO dto = dao.readEmployee(sabeon);
			if(dto==null) {
				System.out.println("�˻� ����� �����ϴ�. ("+ sabeon + ")");
				return;
			}
			printDTO(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchName() {
		System.out.println("\n�̸� �˻�...");

		try {
			System.out.print("�˻��� �̸� > ");
			String name = sc.next();
			List<EmployeeDTO> list= dao.listEmployee(name);
			if(list==null || list.size()==0) {
				System.out.println("�˻� ����� �����ϴ�.");
				return;
			}
			printAllDTO(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void list() {
		System.out.println("\n��� ����Ʈ...");
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