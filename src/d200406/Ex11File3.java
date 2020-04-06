package d200406;

import java.io.File;

public class Ex11File3 {
	public static void main(String[] args) {
		String s;
		double a, b, c, d;

		File[] file = File.listRoots();
		for (File f : file) {
			s = f.getAbsolutePath();

			// 단위는 byte
			a = f.getTotalSpace() / Math.pow(1024, 3); // 전체 디스크 공간의 크기 (단위를 byte에서 GB로 고쳤음)
			b = f.getUsableSpace() / Math.pow(1024, 3); // 사용 가능한 공간의 크기 (단위를 byte에서 GB로 고쳤음)
			c = f.getFreeSpace() / Math.pow(1024, 3); // 잔여 공간의 크기 (단위를 byte에서 GB로 고쳤음)
			d = a - b; // 사용한 용량 구하기

			System.out.println("디스크 드라이브: " + s);
			System.out.println("\t디스크 전체 용량: " + a);
			System.out.println("\t디스크 사용 가능한 용량 : " + b);
			System.out.println("\t디스크 잔여 용량: " + c);
			System.out.println("\t디스크 사용한 용량: " + d);
		}
	}
}
