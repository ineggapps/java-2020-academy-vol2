package d200407;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Ex3BufferedWriter {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathname = "test.txt";
		String s;
		// BufferedWriter:버퍼 출력 문자 스트림
		BufferedWriter bw = null;
		try {
			// FileWriter: 파일 출력 문자 스트림
			bw = new BufferedWriter(new FileWriter(pathname));
			System.out.println("문자열 입력[종료: Ctrl+Z]: ");
			//readLine()은 엔터 값을 버린다.
			while ((s = br.readLine()) != null) {
				bw.write(s);
				bw.newLine();//버린 엔터값 대신에 새로 줄바꿈을 해준다.
			}
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (Exception e2) {
				}
			}
		}

	}
}
