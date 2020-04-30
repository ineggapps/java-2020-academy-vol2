package d200407;

import java.io.BufferedReader;
import java.io.FileReader;

public class Ex4BufferedReader {
	public static void main(String[] args) {
		String pathname = "test.txt";
		String s;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathname));
//			br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname)));
			while ((s = br.readLine()) != null) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
