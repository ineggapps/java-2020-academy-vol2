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
		printTitle("회원 등록");
		MemberDTO dto = new MemberDTO();
		try {

			System.out.print("신규 아이디 입력 > ");
			String id = br.readLine();
			dto.setId(id);

			// 아이디 중복체크
			if (dao.readMember(dto.getId()) != null) {
				System.out.println("￣へ ￣ " + id + "는 사용하실 수 없습니다.");
				System.out.println();
				return;
			}

			System.out.print("이름 입력 > ");
			dto.setName(br.readLine());

			System.out.print("비밀번호 입력 > ");
			dto.setPwd(br.readLine());

			System.out.print("생년월일 입력 > ");
			dto.setBirth(br.readLine());

			System.out.print("이메일 입력 > ");
			dto.setEmail(br.readLine());

			System.out.print("전화번호 입력 > ");
			dto.setTel(br.readLine());

			int result = dao.insertMember(dto);
//			System.out.println(result + "건 등록 완료!");
			System.out.println("○( ＾皿＾)っ 회원 등록 성공...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMember() {
		MemberDTO dto = null;
		printTitle("회원 수정");
		try {
			System.out.print("수정할 회원 아이디 > ");
			String id = br.readLine();
			dto = dao.readMember(id);

			if (dto == null) {
				System.out.println("￣へ ￣ 등록된 아이디가 아닙니다.");
				return;
			}
			printDTO(dto);

			System.out.println("##### 변경 작업을 시작합니다.");
			System.out.print("비밀번호 > ");
			dto.setPwd(br.readLine());
			System.out.print("생일 > ");
			dto.setBirth(br.readLine());
			System.out.print("이메일 > ");
			dto.setEmail(br.readLine());
			System.out.print("전화번호 > ");
			dto.setTel(br.readLine());

			int result = dao.updateMember(dto);
			System.out.println("(^人^) 회원정보가 변경되었습니다.");
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMember() {
		printTitle("회원 삭제");

		try {
			System.out.print("삭제할 아이디 입력 > ");
			String id = br.readLine();
			int result = dao.deleteMember(id);
			if (result == 0) {
				System.out.println("￣へ￣ 아이디가 존재하지 않습니다. (" + id + ")");
				return;
			}
			System.out.println(id + "d=====(￣▽￣*)b 계정을 삭제하였습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void listMember() {
		printTitle("회원 목록 조회");

		List<MemberDTO> list = dao.listMember();
		for (MemberDTO dto : list) {
			printDTO(dto);
		}
	}

	public void findById() {
		printTitle("아이디로 회원 검색");
		try {
			System.out.print("아이디 > ");
			String id = br.readLine();
			MemberDTO dto = dao.readMember(id);
			if (dto == null) {
				System.out.println("검색 결과가 없습니다");
				return;
			}
			printDTO(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByName() {
		printTitle("이름으로 회원 검색");
		try {
			System.out.print("이름 > ");
			String name = br.readLine();
			List<MemberDTO> list = dao.listMember(name);
			if (list.size() == 0) {
				System.out.println("검색 결과가 없습니다");
				return;
			}
			System.out.println(list.size() + "개의 검색 결과가 있습니다.");
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
