package d200406;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//e.zip파일은 이클립스 파일처럼 고용량 파일을 미리 아래 폴더에 갖다 두고 이 클래스를 실행해 본다.
//c:\data 폴더 이하 e.zip파일을 복사하는 프로그램 작성
//바람직하게 고쳐 본 코드
public class Ex8FileCopyEnhanced {
	public static void main(String[] args) {
		String pathname1, pathname2;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f1, f2;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		byte[] b = new byte[2048];
		int len; // 파일 전체의 길이를
		try {
			System.out.print("원본파일명? ");
			pathname1 = br.readLine();
			System.out.print("복사할 새로운 파일명? ");
			pathname2 = br.readLine();

			f1 = new File(pathname1);
			if (!f1.exists()) {
				System.out.println(pathname1 + " 파일이 없습니다.");
				return;
			}
			char ch;
			f2 = new File(pathname2);
			if (f2.exists()) {
				System.out.println("파일이 이미 존재합니다. 덮어쓸 건가요(y/n) ?");
				ch = (char) System.in.read();// 3자가 된다. 엔터(2자) 포함..
				System.in.skip(2);// 엔터 2글잣값은 읽어서 버리라는 이야기임.
				if (ch == 'n' || ch == 'N') {
					System.out.println("복사 작업이 취소되었습니다.");
					return; // n을 선택한 경우 취소
				}
			}

			// 원본을 불러들일 FileInputStream객체 생성
			fis = new FileInputStream(f1);// FileInputStream에 File객체를 넣어도 생성이 된다.
			// 사본을 만들 FileOutputStream객체 생성
			fos = new FileOutputStream(f2);// FileInputStream에 File객체를 넣어도 생성이 된다.

			long start = System.currentTimeMillis();
			// 인자가 없는 read값은 1byte를 읽어서 ASCII코드값으로 넘기는 것이다.
			// fis.read(b,0,b.length)는 byte형배열인 b를 0부터 b길이의 끝까지 읽어낸다는 뜻이다.
			// len은 b길이의 끝을 저장한 것.
			// fis.read(b)와 같은 의미이다.
			while ((len = fis.read(b, 0, b.length)) != -1) {// 입력받아서
				fos.write(b, 0, len);// write시에는 인자를 3개를 넘겨야 한다. 파일의 끝은 꼭 지정한 크기로 나눠 떨어지지 않을 수 있으므로
				// 크기를 지정하지 않으면 쓰레깃값(-1)까지 포함되므로 꼭 인자 3개를 쓰도록 한다.
				// b를 0부터 정해진 길이(len)만큼을 바로 파일에 써낸다는 의미이다.
			}
			fos.flush();// 저장
			long end = System.currentTimeMillis();
			System.out.println("복사 시간: " + (end - start) + "ms");
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
