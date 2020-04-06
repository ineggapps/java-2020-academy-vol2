package d200406;

import java.io.File;

//getParentFile()메서드를 설명하기 위한 클래스임!!!
public class Ex15File7 {
	public static void main(String[] args) {
		String s = "c:" + File.separator + "data" + File.separator + "test.txt";
		File f = new File(s);
		//현재 File객체는 c:\data\test.txt 을 경로로 하여 생성하였음.
		
		//따라서 test.txt를 기준으로 ParentFile은 data폴더가 되고, 그 폴더의 이름은 data가 된다.
		String s2 = f.getParentFile().getName();
		//그러므로 "data"문자열이 출력된다.
		System.out.println(s2);
	}
}
