package d200407;

public class Ex7Skip {
	public static void main(String[] args) {
		// 입력: abcdefg
		int ch;
		System.out.println("문자열 입력[abcdefg]");
		try {
			ch = System.in.read(); // 문자열 하나씩 입력받는다. a가 들어가겠지?
			System.out.println("입력1: " + (char) ch);
			System.in.skip(3); // bcd가 버려짐

			ch = System.in.read();// e가 들어감
			System.out.println("입력2: " + (char) ch);

			System.in.read();// f가 버려짐
			ch = System.in.read(); // g가 ch에 들어감
			System.out.println("입력3: " + (char) ch);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
