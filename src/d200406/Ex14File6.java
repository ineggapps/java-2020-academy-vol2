package d200406;

import java.io.File;

public class Ex14File6 {
	public static void main(String[] args) {
		String pathname = "test.txt";
		File f = new File(pathname);
		if (!f.exists()) {
			System.out.println("�������� �ʽ��ϴ�");
			return;
		}
		System.out.println("��ü ���: " + f.getAbsolutePath());

		// �̸� ����
		String pathname2 = "test2.txt";
		File f2 = new File(pathname2);
		f.renameTo(f2);
		System.out.println("���� �̸��� ����Ǿ����ϴ�."); //�翬�� ���� �Ŀ� ���ϸ��� �ٲ�����ϱ� ����� �� test.txt������ ������ �������� �ʴ´ٰ� �ϰ���?	
	}
}
