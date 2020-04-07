package d200407;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Ex3BufferedWriter {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathname = "test.txt";
		String s;
		// BufferedWriter:���� ��� ���� ��Ʈ��
		BufferedWriter bw = null;
		try {
			// FileWriter: ���� ��� ���� ��Ʈ��
			bw = new BufferedWriter(new FileWriter(pathname));
			System.out.println("���ڿ� �Է�[����: Ctrl+Z]: ");
			//readLine()�� ���� ���� ������.
			while ((s = br.readLine()) != null) {
				bw.write(s);
				bw.newLine();//���� ���Ͱ� ��ſ� ���� �ٹٲ��� ���ش�.
			}
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (Exception e2) {
				}
			}
		}

	}
}
