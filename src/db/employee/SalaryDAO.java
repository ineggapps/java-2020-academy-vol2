package db.employee;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class SalaryDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertSalary(SalaryDTO dto) {
		int result=0;
		
		return result;
	}
	
	public int updateSalary(SalaryDTO dto) {
		int result=0;
		return result;
	}

	public int deleteSalary(int salaryNum) {
		int result=0;

		return result;
	}
	
	public SalaryDTO readSalary(int salaryNum) {
		SalaryDTO dto=null;
		
		return dto;
	}
	
	public List<SalaryDTO> listSalary(String payDate) {
		List<SalaryDTO> list=new ArrayList<>();
		
		return list;
	}
	
	public List<SalaryDTO> listSalary(Map<String, Object> map) {
		List<SalaryDTO> list=new ArrayList<>();
		
		return list;
	}

	public List<SalaryDTO> listSalary() {
		List<SalaryDTO> list=new ArrayList<>();
		
		
		return list;
	}

}
