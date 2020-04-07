package d200407;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Ex10FilterStream2 {
	public static void main(String[] args) {
		try {
			FileOutputStream fis = new FileOutputStream("test.txt");
			DataOutputStream dos = new DataOutputStream(fis);

			dos.writeUTF("ȫ�浿");
			dos.writeInt(80);
			dos.writeInt(90);
			dos.writeInt(70);

			dos.writeUTF("�ɽ���");
			dos.writeInt(70);
			dos.writeInt(80);
			dos.writeInt(90);

			dos.writeUTF("�����");
			dos.writeInt(60);
			dos.writeInt(70);
			dos.writeInt(50);

			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		DataInputStream dis = null;
		try {
			String name;
			int kor, eng, mat;
			String s;
			dis = new DataInputStream(new FileInputStream("test.txt"));

			while (true) {
				name = dis.readUTF();
				kor = dis.readInt();
				eng = dis.readInt();
				mat = dis.readInt();
				s = String.format("%s %d %d %d", name, kor, eng, mat);
				System.out.println(s);
			}

		} 
		catch(EOFException e) {
			//DataInputStream�� ������ ���� �����ϸ� EOFException�� �߻��Ѵ�.
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(dis!=null) {
				try {
					dis.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
