package d200407;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Quiz1BufferedReaderWriter {
	public static void main(String[] args) {
		String s1 = "score1.txt";
		String s2 = "score2.txt";

		List<ScoreDTO> list = readScores(s1);

		sort(list);
		save(s2, list);
	}

	public static void save(String pathname, List<ScoreDTO> list) {
		BufferedWriter bw = null;
		String s;
		try {
			bw = new BufferedWriter(new FileWriter(pathname));
			for (ScoreDTO score : list) {
				bw.write(score.toString());
				bw.newLine();
			}
			bw.flush();
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

	public static void sort(List<ScoreDTO> list) {

		if (list == null) {
			return;
		}

		list.sort(new Comparator<ScoreDTO>() {
			@Override
			public int compare(ScoreDTO o1, ScoreDTO o2) {
				return o2.getTotal() - o1.getTotal();
			}
		});

		for (ScoreDTO score : list) {
			System.out.println(score);
		}
	}

	public static List<ScoreDTO> readScores(String pathname) {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		String s;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathname));
			while ((s = br.readLine()) != null) {
				s = s.trim();
				if (s.length() == 0) {
					continue;
				}
				String[] line = s.split("\\s");//정규표현식 \\s는 스페이스 ,탭 등의 모든 공백 유형
				if (line.length != 4) {
					continue;
				}
				ScoreDTO dto = new ScoreDTO(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]),
						Integer.parseInt(line[3]));
				list.add(dto);
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

		return list;
	}
}

class ScoreDTO {
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private int average;

	@Override
	public String toString() {
		return name + " " + kor + " " + eng + " " + math + " " + total + " " + average;
	}

	public int getTotal() {
		return kor+eng+math;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getAverage() {
		return getTotal()/3;
	}

	public void setAverage(int average) {
		this.average = average;
	}

	/**
	 * 
	 */
	public ScoreDTO() {
	}

	/**
	 * @param name
	 * @param kor
	 * @param eng
	 * @param math
	 */
	public ScoreDTO(String name, int kor, int eng, int math) {
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}

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

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

}