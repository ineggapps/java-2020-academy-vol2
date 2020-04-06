package d200406;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//e.zip������ ��Ŭ���� ����ó�� ��뷮 ������ �̸� �Ʒ� ������ ���� �ΰ� �� Ŭ������ ������ ����.
//c:\data ���� ���� e.zip������ �����ϴ� ���α׷� �ۼ�
//�ٶ����ϰ� ���� �� �ڵ�
public class Ex6FileCopy {
	public static void main(String[] args) {
		String pathname1, pathname2;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//		int data; ��ſ� ����ϴ� �Ʒ��� ����.
		byte[] b = new byte[2048];// int data�� byte�迭�� �ٲ���.
		// byte[1024], 2MB ���� ���� 12ms �ҿ� (�Ļ�� ���� �޶��� �� ����)
		// byte[2048], 2MB ���� ���� 7ms �ҿ� (but, byte�� �迭�� ũ�⸦ �ø��ٰ� ������ ���� �ӵ��� ���� ���� �ƴϴ�)
		int len; // ���� ��ü�� ���̸�
		/*
	 	�� �̷� ������ ��Ÿ����? (���� ����ӵ��� ����������?)
	 	- �����͸� �о���� ���� blocking�ϹǷ� �ӵ��� ���ϵȴ�.
	 	- ���������� ������ �ӽ� ũ�⸦ ������ �ִ�.
	 	- �׷��� �� Ŭ���������� �� ���� 2048byte��ŭ �о�鿩�� ���ۿ� �����Ѵ�. 
	 	(�׻� ������ ũ�⸸ŭ �д� ���� �ƴϴ�. ������ �������� ������ ũ��� ���� �������� ���� �� ����.)
	 	- �޸��� ������ ������ ũ�Ⱑ �����Ƿ� Ű��ٰ� �ؼ� �������� ���� �ƴϴ�.
	 	- (����) 1024~2048byte ������ ũ�⸦ �ָ� �ȴ�.
	 	- 1byte�� ��� �Ͱ� 1024~2048bytes�� ���ļ� ���ۿ� �����ϴ� ��� �� �翬�� ���ڰ� �ӵ� �鿡�� ������. 
	  */
		try {
			System.out.print("�������ϸ�? ");
			pathname1 = br.readLine();
			System.out.print("������ ���ο� ���ϸ�? ");
			pathname2 = br.readLine();

			// ������ �ҷ����� FileInputStream��ü ����
			fis = new FileInputStream(pathname1);
			// �纻�� ���� FileOutputStream��ü ����
			fos = new FileOutputStream(pathname2);

			long start = System.currentTimeMillis();
			//���ڰ� ���� read���� 1byte�� �о ASCII�ڵ尪���� �ѱ�� ���̴�.
			//fis.read(b,0,b.length)�� byte���迭�� b�� 0���� b������ ������ �о�ٴ� ���̴�.
			//len�� b������ ���� ������ ��.
			//fis.read(b)�� ���� �ǹ��̴�.
			while ((len = fis.read(b, 0, b.length)) != -1) {// �Է¹޾Ƽ�
				fos.write(b, 0, len);//write�ÿ��� ���ڸ� 3���� �Ѱܾ� �Ѵ�. ������ ���� �� ������ ũ��� ���� �������� ���� �� �����Ƿ�
				//ũ�⸦ �������� ������ �����갪(-1)���� ���ԵǹǷ� �� ���� 3���� ������ �Ѵ�.
				//b�� 0���� ������ ����(len)��ŭ�� �ٷ� ���Ͽ� �᳽�ٴ� �ǹ��̴�.
			}
			fos.flush();// ����
			long end = System.currentTimeMillis();
			System.out.println("���� �ð�: " + (end - start) + "ms");
		} catch (FileNotFoundException e) {
			// ������ �ҷ��� �� ���ϰ�ο� �ش��ϴ� ������ �������� ���� ���� �ִ�.
			System.out.println(e.toString());
		} catch (IOException e) {
			// ����� �� ���ܰ� �߻��� �� ����.
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
