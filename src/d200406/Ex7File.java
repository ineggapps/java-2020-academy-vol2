package d200406;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//File클래스
public class Ex7File {
	public static void main(String[] args) {
		String appDir = System.getProperty("user.dir");//현재 작업하고 있는 경로를 말한다.
		String pathname = appDir + File.separator + "test.txt";//File.separator 폴더와 파일을 구분하는 구분자
		//Unix에서는 파일 구분자가 /(슬래시)이고 Windows면 \(역슬래시)이므로 Java가 운영체제별로 알아서 구분자를 지정해 준다.
		// String pathname = "test.txt";와 같음.

		// File: 파일 및 폴더 정보를 관리.
		File f = new File(pathname);
		try {
			if (!f.exists()) {
				System.out.println("존재하지 않는 파일(폴더)");
				return;
			}

			System.out.println("파일 정보 출력 ...");
			System.out.println("현 작업경로: " + appDir);
			System.out.println("파일명: " + f.getName());// 파일명.확장자
			System.out.println("파일 길이(long): " + f.length());// 단위: byte

			// 절대경로, 경로1, 경로2의 결괏값이 모두 똑같다.
			System.out.println("절대경로: " + f.getAbsolutePath());// 경로\파일명.확장자
			System.out.println("경로: " + f.getPath());//
			System.out.println("표준 경로: " + f.getCanonicalPath());// 추상경로에 대한 전제 경로를 반환
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 지정한 날짜 형식대로 Date객체의 결괏값을 출력
			System.out.println("파일작성일: " + new Date(f.lastModified()));
			String s = sdf.format(new Date(f.lastModified()));
			System.out.println("만든 날짜: " + s);

			System.out.println("파일 경로만: " + f.getParent());
			System.out.println("읽기 속성: " + f.canRead());
			System.out.println("쓰기 속성: " + f.canWrite());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
