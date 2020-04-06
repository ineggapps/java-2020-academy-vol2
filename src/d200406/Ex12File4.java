package d200406;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
 ������ ����(�Է� ��: c:\windows) ���Ͽ� �����ϴ� ���� �� ������ 
 VO��ü�� ���� List��ü�� ��� �ȿ� �ִ� ��� ��ü���� ���
 to sort
 */
public class Ex12File4 {
	public static void dirList(String pathname) {
		// �˰����� ����Ǵ� �κ� (Ex10File2 ���� => Ex12File4 �ڵ� ����)
		File file = new File(pathname);
		File[] ff = file.listFiles();

		if (ff == null || ff.length == 0) {
			return;
		}

		List<DirVO> list = new LinkedList<DirVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s;

		try {
			for (File f : ff) {
				s = sdf.format(new Date(f.lastModified()));
				if (f.isFile()) {// ������ ���
					DirVO vo = new DirVO();
					vo.setName(f.getName());
					vo.setModified(s);
					vo.setPathType("����");
					vo.setType(1);
					vo.setLength(f.length());
					list.add(vo);
				} else if (f.isDirectory()) {// ������ ���
					DirVO vo = new DirVO();
					vo.setName(f.getName());
					vo.setModified(s);
					vo.setPathType("����");
					vo.setType(0);
					list.add(vo);
				}
			}

			// ���� #1 Comparator�� ����Ѵ�.
			Comparator<DirVO> comp = new Comparator<DirVO>() {
				@Override
				public int compare(DirVO o1, DirVO o2) {
					//type=0 ����, type=1 ����
					//1����: ����, ���� �� ������ ���� ������ ����
					if (o1.getType() > o2.getType()) {
						return 1;
					} else if (o2.getType() < o2.getType()) {
						return -1;
					} else {
						//2���� A: ������ �뷮�� ���� ������ ����
//						return (int)(o1.getLength()-o2.getLength());
						//2���� B: �̸������� �����ϱ�
						return o1.getName().compareTo(o2.getName());
					}
				}
			};
			
			Collections.sort(list, comp);

			System.out.println("�̸�, ������¥, ����, ũ��");
			for (DirVO vo : list) {
				System.out.print(vo.getName() + ", ");
				System.out.print(vo.getModified() + ", ");
				System.out.print(vo.getPathType() + ", ");
				if (vo.getType() == 1) {// ������ ��츸 ���̰� �����ϹǷ�...
					System.out.print(vo.getLength());
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("��θ� �Է�: "); // C:\windows �Է�
			String pathname = br.readLine();
			if (pathname == null || pathname.length() == 0) {
				pathname = System.getProperty("user.dir");
			}
			dirList(pathname);
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

class DirVO {
	private String name;
	private String modified;
	private String pathType;
	private int type;
	private long length;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

}
