package d200407;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class Ex5EncodingMS949 {
	public static void main(String[] args) {
		// 인코딩
		// MS949(euc-kr)로 저장
		int data;
		try {
			// 문자 입력 스트림(MS949)
//			Reader rd = new InputStreamReader(System.in);//Default 설정 (운영체제에 영향을 받음)
			Reader rd = new InputStreamReader(System.in, "MS949"); // 운영체제 관계없이 무조건 MS949로 고정!

			// 파일 출력 문자 스트림 (MS949)
			// 문자 인코딩을 지정하였으므로 바이트스트림만 가지고 사용할 수 없다.
			FileOutputStream fos = new FileOutputStream("test.txt"); // byte stream
			Writer wt = new OutputStreamWriter(fos, "MS949");

			System.out.println("문자열 입력(종료: Ctrl+Z) - 본 파일은 MS949 인코딩으로 저장됨");
			while ((data = rd.read()) != -1) {
				wt.write(data);
			}
			wt.flush();
			wt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
