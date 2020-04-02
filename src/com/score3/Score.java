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
		System.out.println("\n������ �߰�...");
		ScoreDTO dto = new ScoreDTO();
		try {
			System.out.print("�й�? ");
			dto.setHak(br.readLine());
			System.out.print("�̸�? ");
			dto.setName(br.readLine());
			System.out.print("����? ");
			dto.setBirth(br.readLine());
			System.out.print("����? ");
			dto.setKor(Integer.parseInt(br.readLine()));
			System.out.print("����? ");
			dto.setEng(Integer.parseInt(br.readLine()));
			System.out.print("����? ");
			dto.setMat(Integer.parseInt(br.readLine()));
			// DB ������ �����û
			int result = dao.insertScore(dto);
			if (result != 0) {
				System.out.println("�ڷḦ �߰��߽��ϴ�.");
			} else {
				System.out.println("�ڷ� �߰��� �����߽��ϴ�.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		System.out.println("\n������ ����...");
		try {
			ScoreDTO dto = new ScoreDTO();
			System.out.print("�й�? ");
			dto.setHak(br.readLine());
			System.out.print("�̸�: ");
			dto.setName(br.readLine());
			System.out.print("����: ");
			dto.setBirth(br.readLine());
			System.out.print("����: ");
			dto.setKor(Integer.parseInt(br.readLine()));
			System.out.print("����: ");
			dto.setEng(Integer.parseInt(br.readLine()));
			System.out.print("����: ");
			dto.setMat(Integer.parseInt(br.readLine()));

			int result = dao.updateScore(dto);
			if (result != 0) {
				System.out.println("�����Ͽ����ϴ�.");
			} else {
				System.out.println("������ �����߽��ϴ�.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		System.out.println("\n������ ����...");
		try {
			System.out.print("������ �й�? ");
			String hak = br.readLine();
			int result = dao.deleteScore(hak);
			if (result != 0) {
				System.out.println("���� ����.");
			} else {
				System.out.println("���� ����.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByHak() {
		ScoreDTO dto;
		System.out.println("\n�й� �˻�...");
		try {
			System.out.print("��ȸ�� �й�? ");
			String hak = br.readLine();
			dto = dao.readScore(hak);
			if (dto == null) {
				System.out.println(hak + "... ��ϵ� �ڷᰡ �����ϴ�.\n");
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
		System.out.println("\n�̸� �˻�...");
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
				System.out.println("�˻� ����� �����ϴ�.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void listAll() {
		System.out.println("\n��ü ����Ʈ...");
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
