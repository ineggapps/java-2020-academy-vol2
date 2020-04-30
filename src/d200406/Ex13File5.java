package d200406;

import java.io.File;
import java.io.FileFilter;

public class Ex13File5 {
	public static void main(String[] args) {
		String s = "c:\\windows";
		s = System.getProperty("user.dir");
		MyFileList m = new MyFileList(s);
		m.write();
	}
}

//정렬 #2 FileFilter인터페이스의 accept메서드를 오버라이딩하여 정렬한다.
class MyFileList implements FileFilter {
	private File file;

	public MyFileList(String pathname) {
		file = new File(pathname);
	}

	public void write() {
		if (!file.exists()) {
			return;
		}

		System.out.println("절대경로: " + file.getAbsolutePath());
		if (file.isDirectory()) {
			File[] ff = file.listFiles(this);
			for (File f : ff) {
				System.out.print(f.getName());
				if (f.isFile()) {
					// 폴더 하위에 있는 파일들
					System.out.print("\t" + file.length());
				}
				System.out.println();
			}
		} else if (file.isFile()) {
			// 지정한 경로 가장 바깥에 존재하는 파일들
			System.out.print("\t" + file.length());
		}
	}

	@Override
	public boolean accept(File pathname) {
		// 파일과 디렉터리 모두 리스트를 반환한다.
		return pathname.isFile() || pathname.isDirectory();
		// 파일만 반환한다.
//		return pathname.isFile();
		// 디렉터리만 반환한다.
//		return pathname.isDirectory();
		// false는 아무것도 가져오지 않음
//		return false;
	}

}