package d200407;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Quiz1Answer {
	public static void main(String[] args) {
		Score s = new Score();
		s.loadFile("score1.txt");
//		s.saveOrderByTot("score2.txt");
		s.saveOrderByName("score2.txt");
		System.out.println("�۾� �Ϸ�...");
	}
}

//���� score1.txt������ �о�鿩 ����, ����� ���Ͽ� ������ ���� ������ ���������Ѵ�.
//ȫ�浿 80 90 70
//�ɽ��� 90 90 90
//�ڱ浿 80 80 20
//�Ƚɽ� 90 40 22

//������ ������� score2.txt�� �����Ѵ�.
//�ɽ��� 90 90 90 270 90
//ȫ�浿 80 90 70 240 80
//�ڱ浿 80 80 20 180 60
//�Ƚɽ� 90 40 22 152 50

class Score {
	private List<ScoreVO> list = new ArrayList<ScoreVO>();

	public void loadFile(String pathname) {
		BufferedReader br = null;
		String s;
		list.clear();
		try {
			File f = new File(pathname);
			br = new BufferedReader(new FileReader(f));
			while ((s = br.readLine()) != null) {
				s = s.trim();
				if (s.length() == 0) {
					continue;
				}
				ScoreVO vo = new ScoreVO();
				String[] ss = s.split("\\s");
				// ����ǥ���� \\s�� �����̽� ,�� ���� ��� ���� ����
				if (ss.length != 4) {
					continue;
				}
				vo.setName(ss[0]);
				vo.setKor(Integer.parseInt(ss[1]));
				vo.setEng(Integer.parseInt(ss[2]));
				vo.setMat(Integer.parseInt(ss[3]));
				list.add(vo);
			}
		} catch (FileNotFoundException e) {
			System.out.println(pathname + "�� �������� �ʴ� �����Դϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
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

	public void saveOrderByTot(String pathname) {
		if (list.size() == 0) {
			System.out.println("��ϵ� �ڷᰡ �����ϴ�.");
			return;
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pathname));
			// ���� �������� �����ϱ�
			// (+��ȭ) ������ ������ ���� �������� �����ϱ�
			Comparator<ScoreVO> comp = new Comparator<ScoreVO>() {
				@Override
				public int compare(ScoreVO o1, ScoreVO o2) {
					if (o1.getTot() != o2.getTot()) { // ������ �ٸ���...
						return o2.getTot() - o1.getTot(); // ���� ������ ���ϱ�
					} else {// ������ �����̸�?
						return o2.getKor() - o1.getKor(); // �������� ������ ���ϱ�
					}
				}
			};
			Collections.sort(list, comp);
			String s;
			for (ScoreVO vo : list) {
				s = vo.getName() + "\t" + vo.getKor() + "\t" + vo.getEng() + "\t" + vo.getMat() + "\t" + vo.getTot()
						+ "\t" + vo.getAve() + "\n";
				bw.write(s);
				System.out.print(s);// �����
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
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
	public void saveOrderByName(String pathname) {
		if (list.size() == 0) {
			System.out.println("��ϵ� �ڷᰡ �����ϴ�.");
			return;
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pathname));
			// ���� �������� �����ϱ�
			// (+��ȭ) ������ ������ ���� �������� �����ϱ�
			Comparator<ScoreVO> comp = new Comparator<ScoreVO>() {
				@Override
				public int compare(ScoreVO o1, ScoreVO o2) {
					return o1.getName().compareTo(o2.getName());
				}
			};
			Collections.sort(list, comp);
			String s;
			for (ScoreVO vo : list) {
				s = vo.getName() + "\t" + vo.getKor() + "\t" + vo.getEng() + "\t" + vo.getMat() + "\t" + vo.getTot()
						+ "\t" + vo.getAve() + "\n";
				bw.write(s);
				System.out.print(s);// �����
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
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

class ScoreVO {
	private String name;
	private int kor;
	private int eng;
	private int mat;
	private int tot;
	private int ave;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMat() {
		return mat;
	}

	public void setMat(int mat) {
		this.mat = mat;
	}

	public int getTot() {
		return tot = kor + eng + mat;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}

	public int getAve() {
		return ave = getTot() / 3;
	}

	public void setAve(int ave) {
		this.ave = ave;
	}

}
