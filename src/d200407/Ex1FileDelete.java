package d200407;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Ex1FileDelete {
	public static void removeDir(String pathname) {
		try {
			File f = new File(pathname);
			if (f.isDirectory()) { // 폴더면 폴더를 빈 폴더로 만들기 위해 하위 폴더 내의 내용을 모두 삭제하려고 시도
				removeSubDir(f.getPath());// 재귀호출
			}
			f.delete();// 파일 또는 폴더 제거하기 (단, 빈 폴더에 한함)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void removeSubDir(String pathname) {
		// 폴더 안의 모든 폴더 또는 파일 목록
		File[] ff = new File(pathname).listFiles();
		if (ff.length == 0) {
			return;
		}
		try {
			for (File f : ff) {
				if (f.isDirectory()) {
					removeSubDir(f.getPath());// 재귀호출
				}
				f.delete();// 파일 또는 폴더 제거하기
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathname;
		try {
			System.out.print("삭제할 파일 또는 경로 ? ");
			pathname = br.readLine();
			pathname = "c:\\data";// 안전장치
			removeDir(pathname);
			System.out.println("삭제 완료...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
