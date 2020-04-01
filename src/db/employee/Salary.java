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
			System.out.println("\n[급여관리]");
			do {
				System.out.print("1.지급 2.수정 3.삭제 4.월별리스트 5.사번검색 6.리스트 7.사원리스트 8.메인 => ");
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
		System.out.println("\n급여 지급...");

		try {
			SalaryDTO dto = new SalaryDTO();
			System.out.print("사원번호 입력 > ");
			dto.setSabeon(sc.next());

			System.out.print("급여년월 입력 > ");
			dto.setPayDate(sc.next());

			System.out.print("금여지급일자입력 > ");
			dto.setPaymentDate(sc.next());

			System.out.print("기본급 입력 > ");
			dto.setPay(sc.nextInt());

			System.out.print("수당 입력 > ");
			dto.setSudang(sc.nextInt());

			// 세금 계산
			int total = dto.getPay() + dto.getSudang();
			dto.setTax(calculateTax(total));

			System.out.print("메모사항 입력 > ");
			dto.setMemo(sc.next());

			int result = dao.insertSalary(dto);
			if (result >= 1) {
				System.out.println("급여 사항이 입력되었습니다.");
			} else {
				System.out.println("[실패] 급여 사항이 입력되지 않았습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update() {
		System.out.println("\n급여 수정...");
		try {

			System.out.print("수정할 급여번호 > ");
			int salaryNum = sc.nextInt();
			SalaryDTO dto = dao.readSalary(salaryNum);
			if (dto == null) {
				System.out.println("급여번호를 잘못 입력하셨습니다.");
				return;
			}
			printDTO(dto);

			System.out.print("수정할 급여년월 > ");
			dto.setPayDate(sc.next());

			System.out.print("수정할 급여지급일자 > ");
			dto.setPaymentDate(sc.next());

			System.out.print("수정할 기본급 > ");
			dto.setPay(sc.nextInt());

			System.out.print("수정할 수당 > ");
			dto.setSudang(sc.nextInt());

			int total = dto.getPay() + dto.getSudang();
			dto.setTax(calculateTax(total));

			System.out.print("수정할 메모> ");
			dto.setMemo(sc.next());

			int result = dao.updateSalary(dto);
			if (result >= 1) {
				System.out.println("급여 정보가 수정되었습니다.");
			} else {
				System.out.println("[실패] 급여정보 수정 실패");
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
		System.out.println("\n급여 삭제...");
		try {
			System.out.print("삭제할 급여번호 > ");
			int salaryNum = sc.nextInt();
			int result = dao.deleteSalary(salaryNum);
			if (result >= 1) {
				System.out.println("해당 급여내역이 삭제되었습니다");
			} else {
				System.out.println("[실패] 급여내역 번호를 다시 한 번 더 확인해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchSabeon() {
		System.out.println("\n사번 검색...");
		List<SalaryDTO> list;
		String sabeon;
		String payDate;
		try {
			System.out.print("사원번호 입력 > ");
			sabeon = sc.next();
			System.out.print("급여년월[yyyymm] 입력 > ");
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
		System.out.println("\n월별 리스트...");
		try {
			System.out.print("급여년월 입력 > ");
			String payDate = sc.next();
			printAllDTO(dao.listSalary(payDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void list() {
		System.out.println("\n급여 리스트...");
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
