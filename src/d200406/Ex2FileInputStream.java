package d200406;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//����Ʈ ����� ��Ʈ�� ����
public class Ex2FileInputStream {
	public static void main(String[] args) {
		String pathname = "test.txt";
		FileInputStream fis = null;
		int data;
		try {
			//�����Է� byte��Ʈ��
			fis = new FileInputStream(pathname);//������ �������� �ʴ� ��� FileNotFoundException �߻�
			System.out.println("���� ���� ��� ����...");
			while ((data = fis.read()) != -1) {
				//System.out.print() �޼���� 2����Ʈ ������ �����Ƿ�(?) ����� ����� ���� �ʴ´�.
				System.out.write(data);//1����Ʈ�� �״�� 1����Ʈ�� ���
			}
			System.out.flush();//���۰� ���� �����Ƿ� �Է����� ������ ������ ���� ���� ����.
		} catch (FileNotFoundException e) {
			//�ҷ������� �ϴ� ������ �������� �ʴ� ��� FileNotFoundException�� Catch�ϰ� �ȴ�.
			System.out.println(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
