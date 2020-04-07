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
		System.out.println("작업 완료...");
	}
}

//문제 score1.txt파일을 읽어들여 총점, 평균을 구하여 총점이 높은 순으로 내림차순한다.
//홍길동 80 90 70
//심심해 90 90 90
//박길동 80 80 20
//안심심 90 40 22

//다음의 결과물을 score2.txt에 저장한다.
//심심해 90 90 90 270 90
//홍길동 80 90 70 240 80
//박길동 80 80 20 180 60
//안심심 90 40 22 152 50

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
				// 정규표현식 \\s는 스페이스 ,탭 등의 모든 공백 유형
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
			System.out.println(pathname + "은 존재하지 않는 파일입니다.");
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
			System.out.println("등록된 자료가 없습니다.");
			return;
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pathname));
			// 총점 내림차순 정렬하기
			// (+심화) 총점이 같으면 국어 내림차순 정렬하기
			Comparator<ScoreVO> comp = new Comparator<ScoreVO>() {
				@Override
				public int compare(ScoreVO o1, ScoreVO o2) {
					if (o1.getTot() != o2.getTot()) { // 총점이 다르면...
						return o2.getTot() - o1.getTot(); // 총점 가지고 비교하기
					} else {// 총점이 동점이면?
						return o2.getKor() - o1.getKor(); // 국어점수 가지고 비교하기
					}
				}
			};
			Collections.sort(list, comp);
			String s;
			for (ScoreVO vo : list) {
				s = vo.getName() + "\t" + vo.getKor() + "\t" + vo.getEng() + "\t" + vo.getMat() + "\t" + vo.getTot()
						+ "\t" + vo.getAve() + "\n";
				bw.write(s);
				System.out.print(s);// 디버깅
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
			System.out.println("등록된 자료가 없습니다.");
			return;
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pathname));
			// 총점 내림차순 정렬하기
			// (+심화) 총점이 같으면 국어 내림차순 정렬하기
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
				System.out.print(s);// 디버깅
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
