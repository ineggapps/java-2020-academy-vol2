package d200407;

public class Ex7Skip {
	public static void main(String[] args) {
		// �Է�: abcdefg
		int ch;
		System.out.println("���ڿ� �Է�[abcdefg]");
		try {
			ch = System.in.read(); // ���ڿ� �ϳ��� �Է¹޴´�. a�� ������?
			System.out.println("�Է�1: " + (char) ch);
			System.in.skip(3); // bcd�� ������

			ch = System.in.read();// e�� ��
			System.out.println("�Է�2: " + (char) ch);

			System.in.read();// f�� ������
			ch = System.in.read(); // g�� ch�� ��
			System.out.println("�Է�3: " + (char) ch);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
