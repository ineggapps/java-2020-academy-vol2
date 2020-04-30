package d200406;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

//실무에서는 문자만을 처리하는 문자 스트림(2byte씩 읽어들임)을 많이 사용하지 않는다.
public class Ex4FileWriterFileReader {
	public static void main(String[] args) {
		Reader rd = new InputStreamReader(System.in);
		int data;
		String pathname = "test.txt";
		try {
//			OutputStreamWriter는 바이트스트림을 문자 출력스트림으로 고치는 것이다.
//			Writer클래스는 최상위 추상 클래스이다.
//			Writer out = new OutputStreamWriter(new FileOutputStream(pathname));
//			위처럼 복잡하게 코드를 작성할 필요 없이 제공되는 FileWriter를 쓰면 
//			파일 출력 문자 출력스트림을 단번에 생성할 수 있다.
			System.out.println("파일 자체를 문자열로 저장하는 예제");
			FileWriter out = new FileWriter(pathname);
			System.out.print("문자열 입력 [Ctrl+Z] > ");
			while ((data = rd.read()) != -1) {
				out.write(data);// 2byte단위로 출력
			}
			out.flush();// 생략이 가능하다.
			out.close();// close해버리면 위의 flush코드는 생략이 가능하다.
			System.out.println("파일 저장 완료...\n");
			// 파일 입력 "문자" 스트림
//			InputStreamReader(FileInputStream이 바이트스트림) => 최종 문자 스트림
//			Reader reader = new InputStreamReader(new FileInputStream(pathname));
			// 위처럼 복잡하게 코드를 작성할 필요 없이 제공되는 FilteReader를 사용하면 된다.
			// 읽어들일 때 파일이 없으면 FileNotFoundException이 발생한다
			FileReader fr = new FileReader(pathname);
			System.out.println("파일에 저장된 내용...");
			while ((data = fr.read()) != -1) {
				System.out.print((char) data); // 변환하지 않으면 ASCII 코드가 출력된다
			}
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
