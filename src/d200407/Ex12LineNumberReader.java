package d200407;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

//FilterStream�� �� �ٸ� ��
//��Ʈ���� �����Ǿ����ϱ�...
public class Ex12LineNumberReader {
	public static void main(String[] args) {
		// ���� �Է��ϰ� �ִ� ������ ��ȣ�� �� �ʿ䰡 ���� ��... (�׷� ���� ������...)
		LineNumberReader br = new LineNumberReader(new InputStreamReader(System.in));
		String s;
		StringBuilder sb = new StringBuilder();
		try {
			System.out.println("���ڿ� �Է� (����: Ctrl+Z)...");
			do {
				System.out.print((br.getLineNumber() + 1) + ":");
				s = br.readLine();
				sb.append(s);
			} while (s != null);

			System.out.println("\n-----------------------------------------------");
			System.out.println("�Է� ����....");
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
