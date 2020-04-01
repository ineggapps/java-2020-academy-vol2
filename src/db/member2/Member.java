package db.member2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Member {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private MemberDAO dao = new MemberDAOImpl();

	private void printTitle(String title) {
		System.out.println("#########################");
		System.out.println(title);
		System.out.println("#########################");
	}

	public void insertMember() {
		printTitle("ȸ�� ���");
		MemberDTO dto = new MemberDTO();
		try {

			System.out.print("�ű� ���̵� �Է� > ");
			String id = br.readLine();
			dto.setId(id);

			// ���̵� �ߺ�üũ
			if (dao.readMember(dto.getId()) != null) {
				System.out.println("���� �� " + id + "�� ����Ͻ� �� �����ϴ�.");
				System.out.println();
				return;
			}

			System.out.print("�̸� �Է� > ");
			dto.setName(br.readLine());

			System.out.print("��й�ȣ �Է� > ");
			dto.setPwd(br.readLine());

			System.out.print("������� �Է� > ");
			dto.setBirth(br.readLine());

			System.out.print("�̸��� �Է� > ");
			dto.setEmail(br.readLine());

			System.out.print("��ȭ��ȣ �Է� > ");
			dto.setTel(br.readLine());

			int result = dao.insertMember(dto);
//			System.out.println(result + "�� ��� �Ϸ�!");
			System.out.println("��( ��٩��)�� ȸ�� ��� ����...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMember() {
		MemberDTO dto = null;
		printTitle("ȸ�� ����");
		try {
			System.out.print("������ ȸ�� ���̵� > ");
			String id = br.readLine();
			dto = dao.readMember(id);

			if (dto == null) {
				System.out.println("���� �� ��ϵ� ���̵� �ƴմϴ�.");
				return;
			}
			printDTO(dto);

			System.out.println("##### ���� �۾��� �����մϴ�.");
			System.out.print("��й�ȣ > ");
			dto.setPwd(br.readLine());
			System.out.print("���� > ");
			dto.setBirth(br.readLine());
			System.out.print("�̸��� > ");
			dto.setEmail(br.readLine());
			System.out.print("��ȭ��ȣ > ");
			dto.setTel(br.readLine());

			int result = dao.updateMember(dto);
			System.out.println("(^��^) ȸ�������� ����Ǿ����ϴ�.");
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMember() {
		printTitle("ȸ�� ����");

		try {
			System.out.print("������ ���̵� �Է� > ");
			String id = br.readLine();
			int result = dao.deleteMember(id);
			if (result == 0) {
				System.out.println("���أ� ���̵� �������� �ʽ��ϴ�. (" + id + ")");
				return;
			}
			System.out.println(id + "d=====(�����*)b ������ �����Ͽ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void listMember() {
		printTitle("ȸ�� ��� ��ȸ");

		List<MemberDTO> list = dao.listMember();
		for (MemberDTO dto : list) {
			printDTO(dto);
		}
	}

	public void findById() {
		printTitle("���̵�� ȸ�� �˻�");
		try {
			System.out.print("���̵� > ");
			String id = br.readLine();
			MemberDTO dto = dao.readMember(id);
			if (dto == null) {
				System.out.println("�˻� ����� �����ϴ�");
				return;
			}
			printDTO(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByName() {
		printTitle("�̸����� ȸ�� �˻�");
		try {
			System.out.print("�̸� > ");
			String name = br.readLine();
			List<MemberDTO> list = dao.listMember(name);
			if (list.size() == 0) {
				System.out.println("�˻� ����� �����ϴ�");
				return;
			}
			System.out.println(list.size() + "���� �˻� ����� �ֽ��ϴ�.");
			for (MemberDTO dto : list) {
				printDTO(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printDTO(MemberDTO dto) {
		if (dto != null) {
			System.out.print("[" + dto.getId() + ", ");
			System.out.print(dto.getName() + ", ");
			System.out.print(dto.getBirth() + ", ");
			System.out.print(dto.getEmail() + ", ");
			System.out.print(dto.getTel() + "]");
			System.out.println();
			return;
		}
		System.out.println("DTO is NULL... on printDTO(...)");
	}
}
