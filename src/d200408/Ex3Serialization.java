package d200408;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Ex3Serialization {
	public static void main(String[] args) {
		String pathname = "test.txt";
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(pathname));
			oos.writeObject(new UserVO("������", 80, "010-0101-0101"));
			oos.writeObject(new UserVO("������", 80, "010-0101-0102"));
			oos.writeObject(new UserVO("�ٴٴ�", 80, "010-0101-0103"));
			oos.writeObject(new UserVO("����", 80, "010-0101-0104"));

//			Serializable�� implement���� ������ ������ �߻��Ѵ�.
//			java.io.NotSerializableException: d200408.UserVO
//			at java.io.ObjectOutputStream.writeObject0(Unknown Source)
//			at java.io.ObjectOutputStream.writeObject(Unknown Source)
//			at d200408.Ex3SerializationAndDeserialization.main(Ex3SerializationAndDeserialization.java:14)

			System.out.println("���� �Ϸ�...");

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
