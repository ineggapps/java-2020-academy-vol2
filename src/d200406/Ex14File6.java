package d200406;

import java.io.File;

public class Ex14File6 {
	public static void main(String[] args) {
		String pathname = "test.txt";
		File f = new File(pathname);
		if (!f.exists()) {
			System.out.println("존재하지 않습니다");
			return;
		}
		System.out.println("전체 경로: " + f.getAbsolutePath());

		// 이름 변경
		String pathname2 = "test2.txt";
		File f2 = new File(pathname2);
		f.renameTo(f2);
		System.out.println("파일 이름이 변경되었습니다."); //당연히 실행 후에 파일명이 바뀌었으니까 재실행 시 test.txt파일이 없으면 존재하지 않는다고 하겠지?	
	}
}
