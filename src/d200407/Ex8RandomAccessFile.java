package d200407;

import java.io.RandomAccessFile;

public class Ex8RandomAccessFile {
	public static void main(String[] args) {
		byte b;
		try {
			//rw: read, write ��� RandomAccessFile�� �а� ���Ⱑ ���ÿ� �����ϴ�.
			RandomAccessFile raw = new RandomAccessFile("ex.txt", "rw");
			for (int i = 65; i <= 90; i++) {
				raw.write(i);
			}
			//�ε����� 0���� ���� 0=A, 1=B, 2=C, 3=D, 4=E, 5=F...
			raw.seek(5);//F�� Ŀ��
			b = raw.readByte();
			System.out.println((char)b);

			raw.seek(10);//K�� Ŀ��
			b = raw.readByte();
			System.out.println((char)b);

			System.out.flush();
			System.out.println("\n-----------------------------------");
			
			for(int i=0;i<(int)raw.length();i++) {
				raw.seek(i);
				System.out.print((char)raw.readByte());
			}
			System.out.println();
			raw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
