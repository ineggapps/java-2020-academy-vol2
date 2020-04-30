package d200406;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//e.zip파일은 이클립스 파일처럼 고용량 파일을 미리 아래 폴더에 갖다 두고 이 클래스를 실행해 본다.
//c:\data 폴더 이하 e.zip파일을 복사하는 프로그램 작성(비효율적)
//The most inefficient Program... (꽤 오래 걸린다)
public class Ex5FileCopyNotRecommend {
	public static void main(String[] args) {
		String pathname1, pathname2;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int data;
		try {
			//코딩을 이런 식으로 하면 회사에서 맞고 쫓겨난다. (실화)
			System.out.print("원본파일명? ");
			pathname1 = br.readLine();
			System.out.print("복사할 새로운 파일명? ");
			pathname2 = br.readLine();

			// 원본을 불러들일 FileInputStream객체 생성
			fis = new FileInputStream(pathname1);
			// 사본을 만들 FileOutputStream객체 생성
			fos = new FileOutputStream(pathname2);

			long start = System.currentTimeMillis();
			while ((data = fis.read()) != -1) {// 입력받아서
				fos.write(data);// 바로 FileOutputStream객체로 출력
			}
			fos.flush();// 저장
			long end = System.currentTimeMillis();
			System.out.println("복사 시간: " + (end - start) + "ms");//2MB 파일 기준 8738ms 소요 (컴사양 따라 달라질 수 있음)
			//고용량인 경우 실행 도중에 강제종료할 것.
			
		} catch (FileNotFoundException e) {
			// 파일을 불러올 때 파일경로에 해당하는 파일이 존재하지 않을 수도 있다.
			System.out.println(e.toString());
		} catch (IOException e) {
			// 입출력 시 예외가 발생할 수 있음.
			System.out.println(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e2) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
