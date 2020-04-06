package d200406;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

//�ǹ������� ���ڸ��� ó���ϴ� ���� ��Ʈ��(2byte�� �о����)�� ���� ������� �ʴ´�.
public class Ex4FileWriterFileReader {
	public static void main(String[] args) {
		Reader rd = new InputStreamReader(System.in);
		int data;
		String pathname = "test.txt";
		try {
//			OutputStreamWriter�� ����Ʈ��Ʈ���� ���� ��½�Ʈ������ ��ġ�� ���̴�.
//			WriterŬ������ �ֻ��� �߻� Ŭ�����̴�.
//			Writer out = new OutputStreamWriter(new FileOutputStream(pathname));
//			��ó�� �����ϰ� �ڵ带 �ۼ��� �ʿ� ���� �����Ǵ� FileWriter�� ���� 
//			���� ��� ���� ��½�Ʈ���� �ܹ��� ������ �� �ִ�.
			System.out.println("���� ��ü�� ���ڿ��� �����ϴ� ����");
			FileWriter out = new FileWriter(pathname);
			System.out.print("���ڿ� �Է� [Ctrl+Z] > ");
			while ((data = rd.read()) != -1) {
				out.write(data);// 2byte������ ���
			}
			out.flush();// ������ �����ϴ�.
			out.close();// close�ع����� ���� flush�ڵ�� ������ �����ϴ�.
			System.out.println("���� ���� �Ϸ�...\n");
			// ���� �Է� "����" ��Ʈ��
//			InputStreamReader(FileInputStream�� ����Ʈ��Ʈ��) => ���� ���� ��Ʈ��
//			Reader reader = new InputStreamReader(new FileInputStream(pathname));
			// ��ó�� �����ϰ� �ڵ带 �ۼ��� �ʿ� ���� �����Ǵ� FilteReader�� ����ϸ� �ȴ�.
			// �о���� �� ������ ������ FileNotFoundException�� �߻��Ѵ�
			FileReader fr = new FileReader(pathname);
			System.out.println("���Ͽ� ����� ����...");
			while ((data = fr.read()) != -1) {
				System.out.print((char) data); // ��ȯ���� ������ ASCII �ڵ尡 ��µȴ�
			}
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
