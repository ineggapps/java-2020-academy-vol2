package d200409;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Ex2Network {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String web, s;
		try {
			System.out.print("주소[https://www.google.com] ? ");
			web = br.readLine();

			URL url = new URL(web);
			InputStream is = url.openStream();//입력한 웹사이트에 접속하여 얻은 HTML코드를 가져온다.
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((s = br2.readLine()) != null) {
				System.out.println(s);
			}
			br2.close();
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
