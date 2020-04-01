package db.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Salary {
	private Scanner sc = new Scanner(System.in);
	private SalaryDAO dao = new SalaryDAO();
	private Employee emp = null;

	public Salary(Employee emp) {
		this.emp = emp;
	}

	public void salaryManage() {
		int ch;
		while (true) {
			System.out.println("\n[�޿�����]");
			do {
				System.out.print("1.���� 2.���� 3.���� 4.��������Ʈ 5.����˻� 6.����Ʈ 7.�������Ʈ 8.���� => ");
				ch = sc.nextInt();
			} while (ch < 1 || ch > 8);

			if (ch == 8)
				return;

			switch (ch) {
			case 1:
				payment();
				break;
			case 2:
				update();
				break;
			case 3:
				delete();
				break;
			case 4:
				monthList();
				break;
			case 5:
				searchSabeon();
				break;
			case 6:
				list();
				break;
			case 7:
				emp.list();
				break;
			}
		}
	}

	public void payment() {
		System.out.println("\n�޿� ����...");

		try {
			SalaryDTO dto = new SalaryDTO();
			System.out.print("�����ȣ �Է� > ");
			dto.setSabeon(sc.next());

			System.out.print("�޿���� �Է� > ");
			dto.setPayDate(sc.next());

			System.out.print("�ݿ����������Է� > ");
			dto.setPaymentDate(sc.next());

			System.out.print("�⺻�� �Է� > ");
			dto.setPay(sc.nextInt());

			System.out.print("���� �Է� > ");
			dto.setSudang(sc.nextInt());

			// ���� ���
			int total = dto.getPay() + dto.getSudang();
			dto.setTax(calculateTax(total));

			System.out.print("�޸���� �Է� > ");
			dto.setMemo(sc.next());

			int result = dao.insertSalary(dto);
			if (result >= 1) {
				System.out.println("�޿� ������ �ԷµǾ����ϴ�.");
			} else {
				System.out.println("[����] �޿� ������ �Էµ��� �ʾҽ��ϴ�.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update() {
		System.out.println("\n�޿� ����...");
		try {

			System.out.print("������ �޿���ȣ > ");
			int salaryNum = sc.nextInt();
			SalaryDTO dto = dao.readSalary(salaryNum);
			if (dto == null) {
				System.out.println("�޿���ȣ�� �߸� �Է��ϼ̽��ϴ�.");
				return;
			}
			printDTO(dto);

			System.out.print("������ �޿���� > ");
			dto.setPayDate(sc.next());

			System.out.print("������ �޿��������� > ");
			dto.setPaymentDate(sc.next());

			System.out.print("������ �⺻�� > ");
			dto.setPay(sc.nextInt());

			System.out.print("������ ���� > ");
			dto.setSudang(sc.nextInt());

			int total = dto.getPay() + dto.getSudang();
			dto.setTax(calculateTax(total));

			System.out.print("������ �޸�> ");
			dto.setMemo(sc.next());

			int result = dao.updateSalary(dto);
			if (result >= 1) {
				System.out.println("�޿� ������ �����Ǿ����ϴ�.");
			} else {
				System.out.println("[����] �޿����� ���� ����");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int calculateTax(int total) {
		int tax = 0;
		if (total >= 3000000) {
			tax = (int) (total * 0.03);
		} else if (total >= 2000000) {
			tax = (int) (total * 0.02);
		}
		return tax;
	}

	public void delete() {
		System.out.println("\n�޿� ����...");
		try {
			System.out.print("������ �޿���ȣ > ");
			int salaryNum = sc.nextInt();
			int result = dao.deleteSalary(salaryNum);
			if (result >= 1) {
				System.out.println("�ش� �޿������� �����Ǿ����ϴ�");
			} else {
				System.out.println("[����] �޿����� ��ȣ�� �ٽ� �� �� �� Ȯ���� �ּ���.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchSabeon() {
		System.out.println("\n��� �˻�...");
		List<SalaryDTO> list;
		String sabeon;
		String payDate;
		try {
			System.out.print("�����ȣ �Է� > ");
			sabeon = sc.next();
			System.out.print("�޿����[yyyymm] �Է� > ");
			payDate = sc.next();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(SalaryDAO.INPUT_SABEON, sabeon);
			map.put(SalaryDAO.INPUT_PAYDATE, payDate);

			list = dao.listSalary(map);
			printAllDTO(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void monthList() {
		System.out.println("\n���� ����Ʈ...");
		try {
			System.out.print("�޿���� �Է� > ");
			String payDate = sc.next();
			printAllDTO(dao.listSalary(payDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void list() {
		System.out.println("\n�޿� ����Ʈ...");
		printAllDTO(dao.listSalary());
	}

	private void printAllDTO(List<SalaryDTO> list) {
		for (SalaryDTO dto : list) {
			printDTO(dto);
		}
	}

	private void printDTO(SalaryDTO dto) {
		if (dto == null) {
			System.out.println("DTO is NULL...");
			return;
		}
		System.out.print("[");
		System.out.print(dto.getSalaryNum() + ", ");
		System.out.print(dto.getSabeon() + ", ");
		String name = dto.getName();
		if(name!=null && name.length()>0) {			
			System.out.print(name + ", ");
		}
		System.out.print(dto.getPayDate() + ", ");
		System.out.print(dto.getPaymentDate() + ", ");
		System.out.print(dto.getPay() + ", ");
		System.out.print(dto.getSudang() + ", ");
		System.out.print(dto.getTax() + ", ");
		System.out.print(dto.getTot() + ", ");
		System.out.print(dto.getAfterPay() + ", ");
		System.out.print(dto.getMemo() + "]");
		System.out.println();
	}

}
