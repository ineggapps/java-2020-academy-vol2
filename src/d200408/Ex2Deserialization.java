package d200408;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;

public class Ex2Deserialization {
	@SuppressWarnings("unchecked") // 단 아무 때나 @SupressWarnings를 입력하지 말 것.
	public static void main(String[] args) {
		HashMap<String, String> map;
		ObjectInputStream ois = null;
		String pathname = "test.txt"; //물론 파일이 존재해야만 객체를 불러올 수 있음. (EX1예제 실행 후 본 클래스 실행)
		try {
			// Ex1 Serialization 예제와 같이 확인할 것.
			ois = new ObjectInputStream(new FileInputStream(pathname));
			map = (HashMap<String, String>) ois.readObject(); // 제네릭은 캐스팅이 본래 되지 않지만 현재의 경우에는 어쩔 수 없이 캐스팅이 필요함.
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
