package d200407;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class Ex5EncodingMS949 {
	public static void main(String[] args) {
		// ���ڵ�
		// MS949(euc-kr)�� ����
		int data;
		try {
			// ���� �Է� ��Ʈ��(MS949)
//			Reader rd = new InputStreamReader(System.in);//Default ���� (�ü���� ������ ����)
			Reader rd = new InputStreamReader(System.in, "MS949"); // �ü�� ������� ������ MS949�� ����!

			// ���� ��� ���� ��Ʈ�� (MS949)
			// ���� ���ڵ��� �����Ͽ����Ƿ� ����Ʈ��Ʈ���� ������ ����� �� ����.
			FileOutputStream fos = new FileOutputStream("test.txt"); // byte stream
			Writer wt = new OutputStreamWriter(fos, "MS949");

			System.out.println("���ڿ� �Է�(����: Ctrl+Z) - �� ������ MS949 ���ڵ����� �����");
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
