package d200406;

import java.io.File;

//File��ü�� �̿��Ͽ� ������ �����ϴ� ���
public class Ex9File1 {
	public static void main(String[] args) {
		// d:\source\data\test.txt
		String pathname = "d:" + File.separator + "source" + File.separator + "data" + File.separator + "test.txt";
		String path = pathname.substring(0, pathname.lastIndexOf(File.separator));
		System.out.println("���ϸ� �����Ͽ� ��ü ���: " + pathname);
		System.out.println("���: " + path);
		
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();//mkdir�޼��尡 �ƴ϶� mkdirs�̴�. (�������� �ʴ� ��� ������ �� ������ִ� �޼��尡 mkdirs�̴�)
			System.out.println("������ �����Ǿ����ϴ�.");
		}
	}
}
