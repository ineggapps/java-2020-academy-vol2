package d200407;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Ex13SerializationBadCode {
	public static void main(String[] args) {
		String pathname = "test.txt";
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(pathname));

			UserVO vo; // UserVO가 직렬화가 되어 있지 않으므로 객체를 저장할 수 없다.
			vo = new UserVO("홍길동", 30, "010-0000-0000");

			// 직렬화: JAVA(JVM) 외부에서도 사용될 수 있도록 byte 형태로 변환하는 작업 (Serialization)
			// 역직렬화: byte형태로 저장된 JAVA클래스의 객체를 다시 JVM 내부에서 사용할 수 있도록 변환해주는 작업 (역으로 변환)
			
			//기본적으로 직렬화가 되는 것
			//1. 기본자료형 (int, char, boolean ...)
			//2. Serializable 인터페이스를 구현한 클래스의 객체
			
			// java.io.NotSerializableException 예외 발생...
			oos.writeObject(vo); // 엄밀히 따지면 객체의 주솟값을 넘겨주는 것인데... 주솟값을 저장할 거?

			vo = new UserVO("심심해", 10, "010-1111-1111");
			oos.writeObject(vo);

			System.out.println("객체 저장 완료!");

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
