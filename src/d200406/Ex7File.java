package d200406;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//FileŬ����
public class Ex7File {
	public static void main(String[] args) {
		String appDir = System.getProperty("user.dir");//���� �۾��ϰ� �ִ� ��θ� ���Ѵ�.
		String pathname = appDir + File.separator + "test.txt";//File.separator ������ ������ �����ϴ� ������
		//Unix������ ���� �����ڰ� /(������)�̰� Windows�� \(��������)�̹Ƿ� Java�� �ü������ �˾Ƽ� �����ڸ� ������ �ش�.
		// String pathname = "test.txt";�� ����.

		// File: ���� �� ���� ������ ����.
		File f = new File(pathname);
		try {
			if (!f.exists()) {
				System.out.println("�������� �ʴ� ����(����)");
				return;
			}

			System.out.println("���� ���� ��� ...");
			System.out.println("�� �۾����: " + appDir);
			System.out.println("���ϸ�: " + f.getName());// ���ϸ�.Ȯ����
			System.out.println("���� ����(long): " + f.length());// ����: byte

			// ������, ���1, ���2�� �ᱣ���� ��� �Ȱ���.
			System.out.println("������: " + f.getAbsolutePath());// ���\���ϸ�.Ȯ����
			System.out.println("���: " + f.getPath());//
			System.out.println("ǥ�� ���: " + f.getCanonicalPath());// �߻��ο� ���� ���� ��θ� ��ȯ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// ������ ��¥ ���Ĵ�� Date��ü�� �ᱣ���� ���
			System.out.println("�����ۼ���: " + new Date(f.lastModified()));
			String s = sdf.format(new Date(f.lastModified()));
			System.out.println("���� ��¥: " + s);

			System.out.println("���� ��θ�: " + f.getParent());
			System.out.println("�б� �Ӽ�: " + f.canRead());
			System.out.println("���� �Ӽ�: " + f.canWrite());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
