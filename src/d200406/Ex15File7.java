package d200406;

import java.io.File;

//getParentFile()�޼��带 �����ϱ� ���� Ŭ������!!!
public class Ex15File7 {
	public static void main(String[] args) {
		String s = "c:" + File.separator + "data" + File.separator + "test.txt";
		File f = new File(s);
		//���� File��ü�� c:\data\test.txt �� ��η� �Ͽ� �����Ͽ���.
		
		//���� test.txt�� �������� ParentFile�� data������ �ǰ�, �� ������ �̸��� data�� �ȴ�.
		String s2 = f.getParentFile().getName();
		//�׷��Ƿ� "data"���ڿ��� ��µȴ�.
		System.out.println(s2);
	}
}
