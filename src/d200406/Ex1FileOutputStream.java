package d200406;

import java.io.FileOutputStream;
//����Ʈ ����� ��Ʈ�� ����
public class Ex1FileOutputStream {
	public static void main(String[] args) {
		String pathname = "test.txt";//������Ʈ���� �ֻ�ܿ� �������!!
		FileOutputStream fos = null;
		int data;
		try {
			//File byte ��� ��Ʈ��
			//������ ������ �����, �����ϸ� ���(����� ����)
			fos = new FileOutputStream(pathname);
			
			System.out.print("���ڿ� �Է�[����:Ctrl+Z] > ");
			while ((data = System.in.read()) != 13) {
				System.out.println(data);
				fos.write(data);
			}
			fos.flush();//�ڿ��� �ݴ� ���� flush�Ǳ� ������ ����ϴ� ���� ����� ��!
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();//�ݴ� ������ �⺻������ flush()�� �ȴ�.
				} catch (Exception e2) {
				}
			}
		}
		System.out.println("���� ���� ��!...");
	}
}
