package d200408;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Ex4Deserialization {
	public static void main(String[] args) {
		String pathname = "test.txt";
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(pathname));
			while (true) {
				UserVO vo = (UserVO) ois.readObject();
				System.out.println(vo.getName() + ", " + vo.getAge() + ", " + vo.getTel());
			}
		} catch (EOFException e) {
			// ObjectInputStream�� ������ ���� �����ϸ� EOFException ���ܸ� �߻���Ų��.
			//�׷��Ƿ� �� ��ü�� finally ��Ͽ��� close�ϵ��� �Ѵ�.
			System.out.println("������ ��...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
