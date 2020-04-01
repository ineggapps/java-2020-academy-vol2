package db.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

/*
 * desc salary
이름          널?       유형             
----------- -------- -------------- 
SALARYNUM   NOT NULL NUMBER         
SABEON      NOT NULL VARCHAR2(30)   
PAYDATE     NOT NULL VARCHAR2(30)   
PAYMENTDATE NOT NULL DATE           
PAY                  NUMBER(8)      
SUDANG               NUMBER(8)      
TAX                  NUMBER(8)      
MEMO                 VARCHAR2(1000) 

CREATE SEQUENCE salary_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

 * */

public class SalaryDAO {
	private Connection conn = DBConn.getConnection();
	public static final String INPUT_PAYDATE = "paydate";
	public static final String INPUT_SABEON = "sabeon";

	public int insertSalary(SalaryDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO salary(salarynum, sabeon, paydate, paymentdate, pay, sudang, tax, memo) "
				+ "VALUES(salary_seq.NEXTVAL, ?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSabeon());
			pstmt.setString(2, dto.getPayDate());
			pstmt.setString(3, dto.getPaymentDate());
			pstmt.setInt(4, dto.getPay());
			pstmt.setInt(5, dto.getSudang());
			pstmt.setInt(6, dto.getTax());
			pstmt.setString(7, dto.getMemo());
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

	public int updateSalary(SalaryDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "UPDATE salary SET paydate=?, paymentdate=TO_DATE(?,'YYYYMMDD'), pay=?, sudang=?, tax=?, memo=? WHERE salarynum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPayDate());
			pstmt.setString(2, dto.getPaymentDate());
			pstmt.setInt(3, dto.getPay());
			pstmt.setInt(4, dto.getSudang());
			pstmt.setInt(5, dto.getTax());
			pstmt.setString(6, dto.getMemo());
			pstmt.setInt(7, dto.getSalaryNum());
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

	public int deleteSalary(int salaryNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM salary WHERE salarynum = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryNum);
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

	public SalaryDTO readSalary(int salaryNum) {
		SalaryDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salarynum, sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary WHERE salarynum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setPayDate(rs.getString("paydate"));
				dto.setPaymentDate(rs.getDate("paymentdate").toString());
				dto.setPay(rs.getInt("pay"));
				dto.setSudang(rs.getInt("sudang"));
				dto.setTax(rs.getInt("tax"));
				dto.setTot(dto.getPay() + dto.getSudang());// 실급여
				dto.setAfterPay(dto.getTot() - dto.getTax());// 세제후급여
				dto.setMemo(rs.getString("memo"));
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

	public List<SalaryDTO> listSalary(String payDate) {
		List<SalaryDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salarynum, sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary WHERE paydate=? "
				+ "ORDER BY sabeon, paydate desc";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, payDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setPayDate(rs.getString("paydate"));
				dto.setPaymentDate(rs.getDate("paymentdate").toString());
				dto.setPay(rs.getInt("pay"));
				dto.setSudang(rs.getInt("sudang"));
				dto.setTax(rs.getInt("tax"));
				dto.setTot(dto.getPay() + dto.getSudang());// 실급여
				dto.setAfterPay(dto.getTot() - dto.getTax());// 세제후급여
				dto.setMemo(rs.getString("memo"));
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

	public List<SalaryDTO> listSalary(Map<String, Object> map) {
		List<SalaryDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salarynum, sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary WHERE sabeon=? and paydate=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get(INPUT_SABEON));
			pstmt.setString(2, (String) map.get(INPUT_PAYDATE));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setPayDate(rs.getString("paydate"));
				dto.setPaymentDate(rs.getDate("paymentdate").toString());
				dto.setPay(rs.getInt("pay"));
				dto.setSudang(rs.getInt("sudang"));
				dto.setTax(rs.getInt("tax"));
				dto.setTot(dto.getPay() + dto.getSudang());// 실급여
				dto.setAfterPay(dto.getTot() - dto.getTax());// 세제후급여
				dto.setMemo(rs.getString("memo"));
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

	public List<SalaryDTO> listSalary() {
		List<SalaryDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salarynum, sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setPayDate(rs.getString("paydate"));
				dto.setPaymentDate(rs.getDate("paymentdate").toString());
				dto.setPay(rs.getInt("pay"));
				dto.setSudang(rs.getInt("sudang"));
				dto.setTax(rs.getInt("tax"));
				dto.setTot(dto.getPay() + dto.getSudang());// 실급여
				dto.setAfterPay(dto.getTot() - dto.getTax());// 세제후급여
				dto.setMemo(rs.getString("memo"));
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
