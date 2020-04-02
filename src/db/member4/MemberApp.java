package db.member4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MemberApp {
	public static void main(String[] args) {
		int ch;
		Member member = new Member();
		System.out.println("ȸ������ ���α׷� with Ʈ�����");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				do {
					System.out.print("1.��� 2.���� 3.���� 4.���̵�˻� 5.�̸��˻� 6.����Ʈ 7.���� > ");
					ch = Integer.parseInt(br.readLine());
				} while (ch < 1 || ch > 7);

				if(ch==7) {
					break;
				}
				
				switch(ch) {
					case 1: member.insertMember(); break;
					case 2: member.updateMember(); break;
					case 3: member.deleteMember(); break;
					case 4: member.findById(); break;
					case 5: member.findByName(); break;
					case 6: member.listMember(); break;
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
