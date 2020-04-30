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
			//※ 본인 주관 아님...
			map.put("java","어려워");
			map.put("html","쉽지않아");
			map.put("css","돌아버려");
			map.put("javascript","몰라");
			
			// ObjectOutputStream: 상단에 클래스 정보를 저장하므로 append를 하면 클래스 정보가 또 저장되므로 파일을 읽을 수 없다.
			// 따라서 append하지 않는다.
			oos = new ObjectOutputStream(new FileOutputStream(pathname));
			oos.writeObject(map);
			
			System.out.println("파일 저장 완료");
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
