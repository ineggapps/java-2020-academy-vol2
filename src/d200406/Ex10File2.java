package d200406;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Ex10File2 {
	public static void dirList(String pathname) {
		File f = new File(pathname);
		File[] ff = f.listFiles();//�ش� ��ο� ��� �ִ� ��� ���ϰ� ������ü�� File�� List��ü�� �̿��Ͽ� ��°�� �޾� �´�.

		// ���� ��ο� �����̳� ������ �������� �ʴ� ��� üũ
		if (ff == null || ff.length == 0) {
			return;
		}

		try {
			for (File file : ff) {
				if (file.isDirectory()) {
					// ���� file��ü�� ������ ����Ű��?
					System.out.println("������: " + file.getName());
					dirList(file.getAbsolutePath()); //���ȣ��
				} else if (file.isFile()) {
					// ���� file��ü�� �����̸�?
					System.out.println("\t���ϸ�: " + file.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("��θ� �Է�: "); // C:\windows �Է�
			String pathname = br.readLine();
			if(pathname==null||pathname.length()==0) {
				pathname = System.getProperty("user.dir");
			}
			//���ĵ��� ���� ���·� ��µ� ����!
			dirList(pathname);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
}
