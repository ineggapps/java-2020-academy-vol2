package d200407;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Ex13SerializationBadCode {
	public static void main(String[] args) {
		String pathname = "test.txt";
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(pathname));

			UserVO vo; // UserVO�� ����ȭ�� �Ǿ� ���� �����Ƿ� ��ü�� ������ �� ����.
			vo = new UserVO("ȫ�浿", 30, "010-0000-0000");

			// ����ȭ: JAVA(JVM) �ܺο����� ���� �� �ֵ��� byte ���·� ��ȯ�ϴ� �۾� (Serialization)
			// ������ȭ: byte���·� ����� JAVAŬ������ ��ü�� �ٽ� JVM ���ο��� ����� �� �ֵ��� ��ȯ���ִ� �۾� (������ ��ȯ)
			
			//�⺻������ ����ȭ�� �Ǵ� ��
			//1. �⺻�ڷ��� (int, char, boolean ...)
			//2. Serializable �������̽��� ������ Ŭ������ ��ü
			
			// java.io.NotSerializableException ���� �߻�...
			oos.writeObject(vo); // ������ ������ ��ü�� �ּڰ��� �Ѱ��ִ� ���ε�... �ּڰ��� ������ ��?

			vo = new UserVO("�ɽ���", 10, "010-1111-1111");
			oos.writeObject(vo);

			System.out.println("��ü ���� �Ϸ�!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}

class UserVO {
	private String name;
	private int age;
	private String tel;

	/**
	 * 
	 */
	public UserVO() {
	}

	/**
	 * @param name
	 * @param age
	 * @param tel
	 */
	public UserVO(String name, int age, String tel) {
		this.name = name;
		this.age = age;
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getTel() {
		return tel;
	}

}
