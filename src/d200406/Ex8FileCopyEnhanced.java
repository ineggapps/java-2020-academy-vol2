package d200406;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//e.zip������ ��Ŭ���� ����ó�� ��뷮 ������ �̸� �Ʒ� ������ ���� �ΰ� �� Ŭ������ ������ ����.
//c:\data ���� ���� e.zip������ �����ϴ� ���α׷� �ۼ�
//�ٶ����ϰ� ���� �� �ڵ�
public class Ex8FileCopyEnhanced {
	public static void main(String[] args) {
		String pathname1, pathname2;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f1, f2;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		byte[] b = new byte[2048];
		int len; // ���� ��ü�� ���̸�
		try {
			System.out.print("�������ϸ�? ");
			pathname1 = br.readLine();
			System.out.print("������ ���ο� ���ϸ�? ");
			pathname2 = br.readLine();

			f1 = new File(pathname1);
			if (!f1.exists()) {
				System.out.println(pathname1 + " ������ �����ϴ�.");
				return;
			}
			char ch;
			f2 = new File(pathname2);
			if (f2.exists()) {
				System.out.println("������ �̹� �����մϴ�. ��� �ǰ���(y/n) ?");
				ch = (char) System.in.read();// 3�ڰ� �ȴ�. ����(2��) ����..
				System.in.skip(2);// ���� 2���㰪�� �о ������� �̾߱���.
				if (ch == 'n' || ch == 'N') {
					System.out.println("���� �۾��� ��ҵǾ����ϴ�.");
					return; // n�� ������ ��� ���
				}
			}

			// ������ �ҷ����� FileInputStream��ü ����
			fis = new FileInputStream(f1);// FileInputStream�� File��ü�� �־ ������ �ȴ�.
			// �纻�� ���� FileOutputStream��ü ����
			fos = new FileOutputStream(f2);// FileInputStream�� File��ü�� �־ ������ �ȴ�.

			long start = System.currentTimeMillis();
			// ���ڰ� ���� read���� 1byte�� �о ASCII�ڵ尪���� �ѱ�� ���̴�.
			// fis.read(b,0,b.length)�� byte���迭�� b�� 0���� b������ ������ �о�ٴ� ���̴�.
			// len�� b������ ���� ������ ��.
			// fis.read(b)�� ���� �ǹ��̴�.
			while ((len = fis.read(b, 0, b.length)) != -1) {// �Է¹޾Ƽ�
				fos.write(b, 0, len);// write�ÿ��� ���ڸ� 3���� �Ѱܾ� �Ѵ�. ������ ���� �� ������ ũ��� ���� �������� ���� �� �����Ƿ�
				// ũ�⸦ �������� ������ �����갪(-1)���� ���ԵǹǷ� �� ���� 3���� ������ �Ѵ�.
				// b�� 0���� ������ ����(len)��ŭ�� �ٷ� ���Ͽ� �᳽�ٴ� �ǹ��̴�.
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
