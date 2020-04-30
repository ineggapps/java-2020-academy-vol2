package db.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

/*
 * desc employee;
이름     널?       유형           
------ -------- ------------ 
SABEON NOT NULL VARCHAR2(30) 
NAME   NOT NULL VARCHAR2(50) 
BIRTH  NOT NULL DATE         
TEL             VARCHAR2(30) 
 */
public class EmployeeDAO {
	private Connection conn = DBConn.getConnection();

	public int insertEmployee(EmployeeDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO employee(sabeon, name, birth, tel) VALUES(?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSabeon());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getBirth());
			pstmt.setString(4, dto.getTel());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	public int updateEmployee(EmployeeDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "UPDATE employee SET name=?, birth=?, tel=? WHERE sabeon=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getSabeon());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	public EmployeeDTO readEmployee(String sabeon) {
		EmployeeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT sabeon, name, birth, tel FROM employee WHERE sabeon=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sabeon);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new EmployeeDTO();
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setTel(rs.getString("tel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}

	public List<EmployeeDTO> listEmployee() {
		List<EmployeeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT sabeon, name, birth, tel FROM employee";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setTel(rs.getString("tel"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

	public List<EmployeeDTO> listEmployee(String name) {
		List<EmployeeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT sabeon, name, birth, tel FROM employee WHERE INSTR(name, ?) >=1";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setTel(rs.getString("tel"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}
}
