package d200406;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Ex10File2 {
	public static void dirList(String pathname) {
		File f = new File(pathname);
		File[] ff = f.listFiles();//해당 경로에 들어 있는 모든 파일과 폴더객체인 File을 List객체를 이용하여 통째로 받아 온다.

		// 현재 경로에 파일이나 폴더가 존재하지 않는 경우 체크
		if (ff == null || ff.length == 0) {
			return;
		}

		try {
			for (File file : ff) {
				if (file.isDirectory()) {
					// 현재 file객체가 폴더를 가리키면?
					System.out.println("폴더명: " + file.getName());
					dirList(file.getAbsolutePath()); //재귀호출
				} else if (file.isFile()) {
					// 현재 file객체가 파일이면?
					System.out.println("\t파일명: " + file.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("경로명 입력: "); // C:\windows 입력
			String pathname = br.readLine();
			if(pathname==null||pathname.length()==0) {
				pathname = System.getProperty("user.dir");
			}
			//정렬되지 않은 상태로 출력될 것임!
			dirList(pathname);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
}
