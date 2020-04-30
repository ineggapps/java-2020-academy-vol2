package d200406;

import java.io.FileOutputStream;
//바이트 입출력 스트림 예제
public class Ex3FileOutputStreamAppendOption {
	public static void main(String[] args) {
		FileOutputStream fos = null;
		int data;
		try {
//			기존 파일이 존재하면 덮어쓴다. (지우고 다시 만듦)
//			fos = new FileOutputStream("test.txt");
//			기존 파일이 있으면 파일 끝에서부터 추가한다
			fos = new FileOutputStream("test.txt",true);//2번째 매개변수는 boolean append 여부임. true면 덧붙여 쓴다.
			System.out.print("내용 입력[종료: Ctrl+z] > ");
			while ((data = System.in.read()) != 13) {
				fos.write(data);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
