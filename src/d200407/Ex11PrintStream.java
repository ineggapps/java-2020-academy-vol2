package d200407;

import java.io.PrintStream;
import java.util.Calendar;

public class Ex11PrintStream {
	public static void main(String[] args) {
		try {
			PrintStream ps = new PrintStream("test.txt");
			ps.printf("%1tF %1$tA %1$tT\n", Calendar.getInstance());// ���ϳ�, ����, �ð�
			ps.println("���ѹα�");
			ps.println(65);
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
