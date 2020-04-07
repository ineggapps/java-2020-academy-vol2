package d200407;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class Ex6EncodingUTF8 {
	public static void main(String[] args) {
		// ���ڵ�
		// UTF-8�� ����
		int data;
		try {
			// ���� �Է� ��Ʈ��(MS949)
			Reader rd = new InputStreamReader(System.in, "MS949"); // �ü�� ������� ������ MS949�� ����!

			// ���� ��� ���� ��Ʈ�� (MS949)
			// ���� ���ڵ��� �����Ͽ����Ƿ� ����Ʈ��Ʈ���� ������ ����� �� ����.
			FileOutputStream fos = new FileOutputStream("test.txt"); // byte stream
			Writer wt = new OutputStreamWriter(fos, "UTF-8");

			System.out.println("���ڿ� �Է�(����: Ctrl+Z) - �� ������ UTF-8�� �����");
			while ((data = rd.read()) != -1) {
				wt.write(data);
			}
			wt.flush();
			wt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
