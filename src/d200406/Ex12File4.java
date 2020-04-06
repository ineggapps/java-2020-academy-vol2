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
 지정한 폴더(입력 예: c:\windows) 이하에 존재하는 파일 및 폴더를 
 VO객체로 만들어서 List객체에 담고 안에 있는 모든 객체들을 출력
 to sort
 */
public class Ex12File4 {
	public static void dirList(String pathname) {
		// 알고리즘이 변경되는 부분 (Ex10File2 참조 => Ex12File4 코드 변형)
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
				if (f.isFile()) {// 파일인 경우
					DirVO vo = new DirVO();
					vo.setName(f.getName());
					vo.setModified(s);
					vo.setPathType("파일");
					vo.setType(1);
					vo.setLength(f.length());
					list.add(vo);
				} else if (f.isDirectory()) {// 폴더인 경우
					DirVO vo = new DirVO();
					vo.setName(f.getName());
					vo.setModified(s);
					vo.setPathType("폴더");
					vo.setType(0);
					list.add(vo);
				}
			}

			// 정렬 #1 Comparator을 사용한다.
			Comparator<DirVO> comp = new Comparator<DirVO>() {
				@Override
				public int compare(DirVO o1, DirVO o2) {
					//type=0 폴더, type=1 파일
					//1순위: 파일, 폴더 중 폴더를 먼저 나오게 정렬
					if (o1.getType() > o2.getType()) {
						return 1;
					} else if (o2.getType() < o2.getType()) {
						return -1;
					} else {
						//2순위 A: 파일의 용량이 작은 순부터 정렬
//						return (int)(o1.getLength()-o2.getLength());
						//2순위 B: 이름순으로 정렬하기
						return o1.getName().compareTo(o2.getName());
					}
				}
			};
			
			Collections.sort(list, comp);

			System.out.println("이름, 수정날짜, 유형, 크기");
			for (DirVO vo : list) {
				System.out.print(vo.getName() + ", ");
				System.out.print(vo.getModified() + ", ");
				System.out.print(vo.getPathType() + ", ");
				if (vo.getType() == 1) {// 파일인 경우만 길이가 존재하므로...
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
			System.out.print("경로명 입력: "); // C:\windows 입력
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
