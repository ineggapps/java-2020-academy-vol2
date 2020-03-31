package score1;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ch;
		Score ss = new Score();
		try {
			while (true) {
				do {
					System.out.print("1.�߰� 2.���� 3.���� 4.�й��˻� 5.�̸��˻� 6.����Ʈ 7.���� > ");
					ch = sc.nextInt();
				} while (ch < 1 || ch > 7);

				if(ch==7) {
					break;
				}

				switch(ch) {
				case 1: ss.insert();break;
				case 2: ss.update();break;
				case 3: ss.delete();break;
				case 4: ss.findByHak();break;
				case 5: ss.findByName();break;
				case 6: ss.listAll();break;
				}
			}
		} finally {
			sc.close();
		}
	}
}
