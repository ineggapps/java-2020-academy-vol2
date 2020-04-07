package d200407;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class FileDeleteEx {
	public static void removeDir(String pathname) {
		try {
			File f = new File(pathname);
			if (f.isDirectory()) { // ������ ������ �� ������ ����� ���� ���� ���� ���� ������ ��� �����Ϸ��� �õ�
				removeSubDir(f.getPath());// ���ȣ��
			}
			f.delete();// ���� �Ǵ� ���� �����ϱ� (��, �� ������ ����)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void removeSubDir(String pathname) {
		// ���� ���� ��� ���� �Ǵ� ���� ���
		File[] ff = new File(pathname).listFiles();
		if (ff.length == 0) {
			return;
		}
		try {
			for (File f : ff) {
				if (f.isDirectory()) {
					removeSubDir(f.getPath());// ���ȣ��
				}
				f.delete();// ���� �Ǵ� ���� �����ϱ�
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathname;
		try {
			System.out.print("������ ���� �Ǵ� ��� ? ");
			pathname = br.readLine();
			pathname = "c:\\data";// ������ġ
			removeDir(pathname);
			System.out.println("���� �Ϸ�...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
