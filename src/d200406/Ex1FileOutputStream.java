package d200406;

import java.io.FileOutputStream;
//바이트 입출력 스트림 예제
public class Ex1FileOutputStream {
	public static void main(String[] args) {
		String pathname = "test.txt";//프로젝트폴더 최상단에 만들어짐!!
		FileOutputStream fos = null;
		int data;
		try {
			//File byte 출력 스트림
			//파일이 없으면 만들고, 존재하면 덮어씀(지우고 만듦)
			fos = new FileOutputStream(pathname);
			
			System.out.print("문자열 입력[종료:Ctrl+Z] > ");
			while ((data = System.in.read()) != 13) {
				System.out.println(data);
				fos.write(data);
			}
			fos.flush();//자원을 닫는 순간 flush되긴 하지만 명시하는 것이 좋기는 함!
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();//닫는 순간에 기본적으로 flush()가 된다.
				} catch (Exception e2) {
				}
			}
		}
		System.out.println("파일 저장 끝!...");
	}
}
