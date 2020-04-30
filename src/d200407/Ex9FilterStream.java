package d200407;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Ex9FilterStream {
	public static void main(String[] args) {
		//FilterStream ...
		
		try {
			FileOutputStream fos = new FileOutputStream("test.txt");
			//DataOutputStream으로 저장한 내용은 DataInputStream으로만 읽을 수 있다.
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeUTF("대한민국");
			dos.writeByte(10);
			dos.writeChar('A');
			dos.writeInt(5);
			dos.writeUTF("서울");
			dos.close();
			//불러올 때도 저장할 때의 순서대로 읽어야 한다.
			FileInputStream fis = new FileInputStream("test.txt");
			DataInputStream dis = new DataInputStream(fis);
			
			//저장한 순서대로 불러들여야 함.
			System.out.println(dis.readUTF());//대한민국
			System.out.println(dis.readByte());//10
			System.out.println(dis.readChar());//A
			System.out.println(dis.readInt());//5
			System.out.println(dis.readUTF());//서울

			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
