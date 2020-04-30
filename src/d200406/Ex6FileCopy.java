package d200406;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//e.zip파일은 이클립스 파일처럼 고용량 파일을 미리 아래 폴더에 갖다 두고 이 클래스를 실행해 본다.
//c:\data 폴더 이하 e.zip파일을 복사하는 프로그램 작성
//바람직하게 고쳐 본 코드
public class Ex6FileCopy {
	public static void main(String[] args) {
		String pathname1, pathname2;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//		int data; 대신에 사용하는 아래의 변수.
		byte[] b = new byte[2048];// int data를 byte배열로 바꿨음.
		// byte[1024], 2MB 파일 기준 12ms 소요 (컴사양 따라 달라질 수 있음)
		// byte[2048], 2MB 파일 기준 7ms 소요 (but, byte의 배열의 크기를 늘린다고 무조건 실행 속도가 빠른 것은 아니다)
		int len; // 파일 전체의 길이를
		/*
	 	왜 이런 현상이 나타날까? (파일 실행속도가 빨라졌을까?)
	 	- 데이터를 읽어들일 때는 blocking하므로 속도가 저하된다.
	 	- 내부적으로 버퍼의 임시 크기를 가지고 있다.
	 	- 그런데 이 클래스에서는 한 번에 2048byte만큼 읽어들여서 버퍼에 축적한다. 
	 	(항상 지정한 크기만큼 읽는 것은 아니다. 파일의 끝에서는 지정한 크기로 나눠 떨어지지 않을 수 있음.)
	 	- 메모리의 정해진 버퍼의 크기가 있으므로 키운다고 해서 빨라지는 것은 아니다.
	 	- (권장) 1024~2048byte 정도의 크기를 주면 된다.
	 	- 1byte씩 찍는 것과 1024~2048bytes씩 뭉쳐서 버퍼에 저장하는 방법 중 당연히 후자가 속도 면에서 빠르다. 
	  */
		try {
			System.out.print("원본파일명? ");
			pathname1 = br.readLine();
			System.out.print("복사할 새로운 파일명? ");
			pathname2 = br.readLine();

			// 원본을 불러들일 FileInputStream객체 생성
			fis = new FileInputStream(pathname1);
			// 사본을 만들 FileOutputStream객체 생성
			fos = new FileOutputStream(pathname2);

			long start = System.currentTimeMillis();
			//인자가 없는 read값은 1byte를 읽어서 ASCII코드값으로 넘기는 것이다.
			//fis.read(b,0,b.length)는 byte형배열인 b를 0부터 b길이의 끝까지 읽어낸다는 뜻이다.
			//len은 b길이의 끝을 저장한 것.
			//fis.read(b)와 같은 의미이다.
			while ((len = fis.read(b, 0, b.length)) != -1) {// 입력받아서
				fos.write(b, 0, len);//write시에는 인자를 3개를 넘겨야 한다. 파일의 끝은 꼭 지정한 크기로 나눠 떨어지지 않을 수 있으므로
				//크기를 지정하지 않으면 쓰레깃값(-1)까지 포함되므로 꼭 인자 3개를 쓰도록 한다.
				//b를 0부터 정해진 길이(len)만큼을 바로 파일에 써낸다는 의미이다.
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
