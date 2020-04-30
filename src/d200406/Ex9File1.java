package d200406;

import java.io.File;

//File객체를 이용하여 파일을 저장하는 방법
public class Ex9File1 {
	public static void main(String[] args) {
		// d:\source\data\test.txt
		String pathname = "d:" + File.separator + "source" + File.separator + "data" + File.separator + "test.txt";
		String path = pathname.substring(0, pathname.lastIndexOf(File.separator));
		System.out.println("파일명 포함하여 전체 경로: " + pathname);
		System.out.println("경로: " + path);
		
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();//mkdir메서드가 아니라 mkdirs이다. (존재하지 않는 모든 폴더를 다 만들어주는 메서드가 mkdirs이다)
			System.out.println("폴더가 생성되었습니다.");
		}
	}
}
