package d200408;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Ex1Serialization {
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		ObjectOutputStream oos = null;
		String pathname = "test.txt";
		try {
			//�� ���� �ְ� �ƴ�...
			map.put("java","�����");
			map.put("html","�����ʾ�");
			map.put("css","���ƹ���");
			map.put("javascript","����");
			
			// ObjectOutputStream: ��ܿ� Ŭ���� ������ �����ϹǷ� append�� �ϸ� Ŭ���� ������ �� ����ǹǷ� ������ ���� �� ����.
			// ���� append���� �ʴ´�.
			oos = new ObjectOutputStream(new FileOutputStream(pathname));
			oos.writeObject(map);
			
			System.out.println("���� ���� �Ϸ�");
			oos.flush();
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
