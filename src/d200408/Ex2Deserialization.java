package d200408;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;

public class Ex2Deserialization {
	@SuppressWarnings("unchecked") // �� �ƹ� ���� @SupressWarnings�� �Է����� �� ��.
	public static void main(String[] args) {
		HashMap<String, String> map;
		ObjectInputStream ois = null;
		String pathname = "test.txt"; //���� ������ �����ؾ߸� ��ü�� �ҷ��� �� ����. (EX1���� ���� �� �� Ŭ���� ����)
		try {
			// Ex1 Serialization ������ ���� Ȯ���� ��.
			ois = new ObjectInputStream(new FileInputStream(pathname));
			map = (HashMap<String, String>) ois.readObject(); // ���׸��� ĳ������ ���� ���� ������ ������ ��쿡�� ��¿ �� ���� ĳ������ �ʿ���.
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = map.get(key);
				System.out.println(key + ": " + value);
			}
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
