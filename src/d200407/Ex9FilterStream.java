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
			//DataOutputStream���� ������ ������ DataInputStream���θ� ���� �� �ִ�.
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeUTF("���ѹα�");
			dos.writeByte(10);
			dos.writeChar('A');
			dos.writeInt(5);
			dos.writeUTF("����");
			dos.close();
			//�ҷ��� ���� ������ ���� ������� �о�� �Ѵ�.
			FileInputStream fis = new FileInputStream("test.txt");
			DataInputStream dis = new DataInputStream(fis);
			
			//������ ������� �ҷ��鿩�� ��.
			System.out.println(dis.readUTF());//���ѹα�
			System.out.println(dis.readByte());//10
			System.out.println(dis.readChar());//A
			System.out.println(dis.readInt());//5
			System.out.println(dis.readUTF());//����

			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
