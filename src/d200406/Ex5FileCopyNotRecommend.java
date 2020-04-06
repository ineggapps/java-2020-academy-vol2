package d200406;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//e.zip������ ��Ŭ���� ����ó�� ��뷮 ������ �̸� �Ʒ� ������ ���� �ΰ� �� Ŭ������ ������ ����.
//c:\data ���� ���� e.zip������ �����ϴ� ���α׷� �ۼ�(��ȿ����)
//The most inefficient Program... (�� ���� �ɸ���)
public class Ex5FileCopyNotRecommend {
	public static void main(String[] args) {
		String pathname1, pathname2;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int data;
		try {
			//�ڵ��� �̷� ������ �ϸ� ȸ�翡�� �°� �Ѱܳ���. (��ȭ)
			System.out.print("�������ϸ�? ");
			pathname1 = br.readLine();
			System.out.print("������ ���ο� ���ϸ�? ");
			pathname2 = br.readLine();

			// ������ �ҷ����� FileInputStream��ü ����
			fis = new FileInputStream(pathname1);
			// �纻�� ���� FileOutputStream��ü ����
			fos = new FileOutputStream(pathname2);

			long start = System.currentTimeMillis();
			while ((data = fis.read()) != -1) {// �Է¹޾Ƽ�
				fos.write(data);// �ٷ� FileOutputStream��ü�� ���
			}
			fos.flush();// ����
			long end = System.currentTimeMillis();
			System.out.println("���� �ð�: " + (end - start) + "ms");//2MB ���� ���� 8738ms �ҿ� (�Ļ�� ���� �޶��� �� ����)
			//��뷮�� ��� ���� ���߿� ���������� ��.
			
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
