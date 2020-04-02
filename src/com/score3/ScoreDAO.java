package com.score3;

import java.util.List;
import java.util.Map;

public interface ScoreDAO {
	public int insertScore(ScoreDTO dto);
	public int updateScore(ScoreDTO dto);
	public int deleteScore(String hak);
	
	public ScoreDTO readScore(String hak);
	public List<ScoreDTO> listScore(String name);
	public List<ScoreDTO> listScore();
	
	//±¹¿µ¼ö ÃÑÁ¡
	public Map<String, Integer> averageScore();
}
