package com.score3;

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
			// DB 쿼리문 실행요청
			int result = dao.insertScore(dto);
			if (result != 0) {
				System.out.println("자료를 추가했습니다.");
			} else {
				System.out.println("자료 추가를 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		System.out.println("\n데이터 수정...");
		try {
			ScoreDTO dto = new ScoreDTO();
			System.out.print("학번? ");
			dto.setHak(br.readLine());
			System.out.print("이름: ");
			dto.setName(br.readLine());
			System.out.print("생일: ");
			dto.setBirth(br.readLine());
			System.out.print("국어: ");
			dto.setKor(Integer.parseInt(br.readLine()));
			System.out.print("영어: ");
			dto.setEng(Integer.parseInt(br.readLine()));
			System.out.print("수학: ");
			dto.setMat(Integer.parseInt(br.readLine()));

			int result = dao.updateScore(dto);
			if (result != 0) {
				System.out.println("수정하였습니다.");
			} else {
				System.out.println("수정에 실패했습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		System.out.println("\n데이터 삭제...");
		try {
			System.out.print("삭제할 학번? ");
			String hak = br.readLine();
			int result = dao.deleteScore(hak);
			if (result != 0) {
				System.out.println("삭제 성공.");
			} else {
				System.out.println("삭제 실패.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByHak() {
		ScoreDTO dto;
		System.out.println("\n학번 검색...");
		try {
			System.out.print("조회할 학번? ");
			String hak = br.readLine();
			dto = dao.readScore(hak);
			if (dto == null) {
				System.out.println(hak + "... 등록된 자료가 없습니다.\n");
				return;
			}
			System.out.print(dto.getHak() + "\t\t");
			System.out.print(dto.getName() + "\t\t");
			System.out.print(dto.getBirth() + "\t\t");
			System.out.print(dto.getKor() + "\t\t");
			System.out.print(dto.getEng() + "\t\t");
			System.out.print(dto.getMat() + "\t\t");
			System.out.print(dto.getTot() + "\t\t");
			System.out.print(dto.getAve() + "\t\t");
			System.out.print(dto.getRank());
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByName() {
		System.out.println("\n이름 검색...");
		try {
			List<ScoreDTO> list = dao.listScore(br.readLine());
			for (ScoreDTO dto : list) {
				System.out.print(dto.getHak() + "\t\t");
				System.out.print(dto.getName() + "\t\t");
				System.out.print(dto.getBirth() + "\t\t");
				System.out.print(dto.getKor() + "\t\t");
				System.out.print(dto.getEng() + "\t\t");
				System.out.print(dto.getMat() + "\t\t");
				System.out.print(dto.getTot() + "\t\t");
				System.out.print(dto.getAve() + "\t\t");
				System.out.println();
			}
			if (list.size() == 0) {
				System.out.println("검색 결과가 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void listAll() {
		System.out.println("\n전체 리스트...");
		List<ScoreDTO> list = dao.listScore();
		for (ScoreDTO dto : list) {
			System.out.print(dto.getHak() + "\t\t");
			System.out.print(dto.getName() + "\t\t");
			System.out.print(dto.getBirth() + "\t\t");
			System.out.print(dto.getKor() + "\t\t");
			System.out.print(dto.getEng() + "\t\t");
			System.out.print(dto.getMat() + "\t\t");
			System.out.print(dto.getTot() + "\t\t");
			System.out.print(dto.getAve() + "\t\t");
			System.out.print(dto.getRank());
			System.out.println();
		}
	}
}
