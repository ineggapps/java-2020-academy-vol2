package d200406;

import java.io.File;

public class Ex11File3 {
	public static void main(String[] args) {
		String s;
		double a, b, c, d;

		File[] file = File.listRoots();
		for (File f : file) {
			s = f.getAbsolutePath();

			// ������ byte
			a = f.getTotalSpace() / Math.pow(1024, 3); // ��ü ��ũ ������ ũ�� (������ byte���� GB�� ������)
			b = f.getUsableSpace() / Math.pow(1024, 3); // ��� ������ ������ ũ�� (������ byte���� GB�� ������)
			c = f.getFreeSpace() / Math.pow(1024, 3); // �ܿ� ������ ũ�� (������ byte���� GB�� ������)
			d = a - b; // ����� �뷮 ���ϱ�

			System.out.println("��ũ ����̺�: " + s);
			System.out.println("\t��ũ ��ü �뷮: " + a);
			System.out.println("\t��ũ ��� ������ �뷮 : " + b);
			System.out.println("\t��ũ �ܿ� �뷮: " + c);
			System.out.println("\t��ũ ����� �뷮: " + d);
		}
	}
}
