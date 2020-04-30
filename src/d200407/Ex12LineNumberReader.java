package d200407;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

//FilterStream의 또 다른 예
//스트림이 가공되었으니까...
public class Ex12LineNumberReader {
	public static void main(String[] args) {
		// 현재 입력하고 있는 라인의 번호를 알 필요가 있을 때... (그럴 때가 있을까...)
		LineNumberReader br = new LineNumberReader(new InputStreamReader(System.in));
		String s;
		StringBuilder sb = new StringBuilder();
		try {
			System.out.println("문자열 입력 (종료: Ctrl+Z)...");
			do {
				System.out.print((br.getLineNumber() + 1) + ":");
				s = br.readLine();
				sb.append(s);
			} while (s != null);

			System.out.println("\n-----------------------------------------------");
			System.out.println("입력 내용....");
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
