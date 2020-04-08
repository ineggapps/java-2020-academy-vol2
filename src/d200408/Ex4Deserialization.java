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
			// ObjectInputStream이 파일의 끝에 도달하면 EOFException 예외를 발생시킨다.
			//그러므로 이 객체는 finally 블록에서 close하도록 한다.
			System.out.println("파일의 끝...");
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
