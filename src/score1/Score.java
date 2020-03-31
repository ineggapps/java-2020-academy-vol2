package score1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Score {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private ScoreDAO dao = new ScoreDAOImpl();

	public void insert() {
		System.out.println("\n데이터 추가...");
		ScoreDTO dto = new ScoreDTO();
		try {
			System.out.print("학번? ");
			dto.setHak(br.readLine());
			System.out.print("이름? ");
			dto.setName(br.readLine());
			System.out.print("생일? ");
			dto.setBirth(br.readLine());
			System.out.print("국어? ");
			dto.setKor(Integer.parseInt(br.readLine()));
			System.out.print("영어? ");
			dto.setEng(Integer.parseInt(br.readLine()));
			System.out.print("수학? ");
			dto.setMat(Integer.parseInt(br.readLine()));
			//DB 쿼리문 실행요청
			int result = dao.insertScore(dto);
			System.out.println(result +"행이 추가되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		System.out.println("\n데이터 수정...");
	}

	public void delete() {
		System.out.println("\n데이터 삭제...");
	}

	public void findByHak() {
		System.out.println("\n학번 검색...");
	}

	public void findByName() {
		System.out.println("\n이름 검색...");
	}

	public void listAll() {
		System.out.println("\n전체 리스트...");
		List<ScoreDTO> list = dao.listScore();
		for(ScoreDTO dto: list) {
			System.out.print(dto.getHak() + "\t\t");
			System.out.print(dto.getName() + "\t\t");
			System.out.print(dto.getBirth() + "\t\t");
			System.out.print(dto.getKor() + "\t\t");
			System.out.print(dto.getEng() + "\t\t");
			System.out.print(dto.getMat() + "\t\t");
			System.out.print(dto.getTot() + "\t\t");
			System.out.print(dto.getAve() + "\t\t");
			System.out.println(dto.getRank());
		}
	}
}
