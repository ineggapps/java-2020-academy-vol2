package db.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class EmployeeDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertEmployee(EmployeeDTO dto) {
		int result=0;
		
		return result;
	}
	
	public int updateEmployee(EmployeeDTO dto) {
		int result=0;
		
		return result;
	}
	
	public EmployeeDTO readEmployee(String sabeon) {
		EmployeeDTO dto=null;

		return dto;
	}
	
	public List<EmployeeDTO> listEmployee() {
		List<EmployeeDTO> list=new ArrayList<>();
		
		
		return list;
	}
	
	public List<EmployeeDTO> listEmployee(String name) {
		List<EmployeeDTO> list=new ArrayList<>();
		
		return list;
	}
}
