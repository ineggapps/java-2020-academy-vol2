package d200406;

import java.io.FileOutputStream;
//����Ʈ ����� ��Ʈ�� ����
public class Ex3FileOutputStreamAppendOption {
	public static void main(String[] args) {
		FileOutputStream fos = null;
		int data;
		try {
//			���� ������ �����ϸ� �����. (����� �ٽ� ����)
//			fos = new FileOutputStream("test.txt");
//			���� ������ ������ ���� ���������� �߰��Ѵ�
			fos = new FileOutputStream("test.txt",true);//2��° �Ű������� boolean append ������. true�� ���ٿ� ����.
			System.out.print("���� �Է�[����: Ctrl+z] > ");
			while ((data = System.in.read()) != 13) {
				fos.write(data);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
