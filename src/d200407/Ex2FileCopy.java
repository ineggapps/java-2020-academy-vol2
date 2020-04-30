package d200407;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//■■■■■ 파일 복사하는 코드는 따로 암기할 필요가 있다.
public class Ex2FileCopy {
	public static void main(String[] args) {
		String s1, s2;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		byte b[] = new byte[2048];

		try {
			System.out.print("복사할 원본 ? ");
			s1 = br.readLine();
			System.out.print("새로운 파일 ? ");
			s2 = br.readLine();

			long start = System.currentTimeMillis();
			// 버퍼를 사용한다는 것 이외에는 새로운 내용이 없음.
			bis = new BufferedInputStream(new FileInputStream(new File(s1)));
			bos = new BufferedOutputStream(new FileOutputStream(new File(s2)));
			//■■■■■■■■■HIGHLIGHT■■■■■■■■■
			int len;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);// len은 읽어들인 길이를 의미한다.
			}
			bos.flush();// 저장하기 위해서는 flush()를 명시해주는 것을 권장한다.
			//■■■■■■■■■HIGHLIGHT■■■■■■■■■
			long end = System.currentTimeMillis();
			System.out.println("저장하였습니다. " + (end - start) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e2) {
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
