package d200407;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//������ ���� �����ϴ� �ڵ�� ���� �ϱ��� �ʿ䰡 �ִ�.
public class Ex2FileCopy {
	public static void main(String[] args) {
		String s1, s2;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		byte b[] = new byte[2048];

		try {
			System.out.print("������ ���� ? ");
			s1 = br.readLine();
			System.out.print("���ο� ���� ? ");
			s2 = br.readLine();

			long start = System.currentTimeMillis();
			// ���۸� ����Ѵٴ� �� �̿ܿ��� ���ο� ������ ����.
			bis = new BufferedInputStream(new FileInputStream(new File(s1)));
			bos = new BufferedOutputStream(new FileOutputStream(new File(s2)));
			//����������HIGHLIGHT����������
			int len;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);// len�� �о���� ���̸� �ǹ��Ѵ�.
			}
			bos.flush();// �����ϱ� ���ؼ��� flush()�� ������ִ� ���� �����Ѵ�.
			//����������HIGHLIGHT����������
			long end = System.currentTimeMillis();
			System.out.println("�����Ͽ����ϴ�. " + (end - start) + "ms");
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
