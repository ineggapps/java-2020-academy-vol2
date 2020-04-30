package d200406;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//바이트 입출력 스트림 예제
public class Ex2FileInputStream {
	public static void main(String[] args) {
		String pathname = "test.txt";
		FileInputStream fis = null;
		int data;
		try {
			//파일입력 byte스트림
			fis = new FileInputStream(pathname);//파일이 존재하지 않는 경우 FileNotFoundException 발생
			System.out.println("파일 내용 출력 시작...");
			while ((data = fis.read()) != -1) {
				//System.out.print() 메서드는 2바이트 단위로 읽으므로(?) 출력이 제대로 되지 않는다.
				System.out.write(data);//1바이트를 그대로 1바이트로 출력
			}
			System.out.flush();//버퍼가 차지 않으므로 입력하지 않으면 나오지 않을 수도 있음.
		} catch (FileNotFoundException e) {
			//불러오고자 하는 파일이 존재하지 않는 경우 FileNotFoundException을 Catch하게 된다.
			System.out.println(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
