package d200406;

import java.io.File;
import java.io.FileFilter;

public class Ex13File5 {
	public static void main(String[] args) {
		String s = "c:\\windows";
		s = System.getProperty("user.dir");
		MyFileList m = new MyFileList(s);
		m.write();
	}
}

//���� #2 FileFilter�������̽��� accept�޼��带 �������̵��Ͽ� �����Ѵ�.
class MyFileList implements FileFilter {
	private File file;

	public MyFileList(String pathname) {
		file = new File(pathname);
	}

	public void write() {
		if (!file.exists()) {
			return;
		}

		System.out.println("������: " + file.getAbsolutePath());
		if (file.isDirectory()) {
			File[] ff = file.listFiles(this);
			for (File f : ff) {
				System.out.print(f.getName());
				if (f.isFile()) {
					// ���� ������ �ִ� ���ϵ�
					System.out.print("\t" + file.length());
				}
				System.out.println();
			}
		} else if (file.isFile()) {
			// ������ ��� ���� �ٱ��� �����ϴ� ���ϵ�
			System.out.print("\t" + file.length());
		}
	}

	@Override
	public boolean accept(File pathname) {
		// ���ϰ� ���͸� ��� ����Ʈ�� ��ȯ�Ѵ�.
		return pathname.isFile() || pathname.isDirectory();
		// ���ϸ� ��ȯ�Ѵ�.
//		return pathname.isFile();
		// ���͸��� ��ȯ�Ѵ�.
//		return pathname.isDirectory();
		// false�� �ƹ��͵� �������� ����
//		return false;
	}

}