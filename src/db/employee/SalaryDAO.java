package db.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
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
		String sql = "SELECT salarynum, name, e.sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary s "
				+ "JOIN employee e on s.sabeon = e.sabeon " + "WHERE salarynum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
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
		String sql = "SELECT salarynum, name, s.sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary s "
				+ "JOIN employee e ON s.sabeon = e.sabeon " + "WHERE paydate=? " + "ORDER BY sabeon, paydate desc";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, payDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
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
		String sql = "SELECT salarynum, name, s.sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary s "
				+ "JOIN employee e ON s.sabeon = e.sabeon " + "WHERE s.sabeon=? and paydate=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get(INPUT_SABEON));
			pstmt.setString(2, (String) map.get(INPUT_PAYDATE));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
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
		String sql = "SELECT salarynum, name, s.sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary s JOIN employee e ON s.sabeon = e.sabeon";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salarynum"));
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
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

	// 연도별 월별 급여별
	public List<GroupByDTO> listGroupBy() {
		List<GroupByDTO> list = new ArrayList<GroupByDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT  SUBSTR(paydate,1,4) year, SUBSTR(paydate,5,2) month, sum(pay) pay_sum, trunc(avg(pay)) pay_average"
				+ " " + "FROM salary " + "GROUP BY SUBSTR(paydate,1,4), SUBSTR(paydate,5,2)";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GroupByDTO dto = new GroupByDTO();
				dto.setYear(rs.getString("year"));
				dto.setMonth(rs.getString("month"));
				dto.setSum(rs.getInt("pay_sum"));
				dto.setAverage(rs.getInt("pay_average"));
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

	public Map<String, Integer> listPivot(String year) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//줄바꿈 없애기 https://www.textfixer.com/tools/remove-line-breaks.php
		String sql = "SELECT * FROM ( SELECT SUBSTR(paydate, 5, 2) month, pay FROM salary WHERE substr(paydate,1,4)=?) PIVOT ( sum(pay) FOR month IN ( '01' AS \"M01\", '02' AS \"M02\", '03' AS \"M03\", '04' AS \"M04\", '05' AS \"M05\", '06' AS \"M06\", '07' AS \"M07\", '08' AS \"M08\", '09' AS \"M09\", '10' AS \"M10\", '11' AS \"M11\", '12' AS \"M12\" ) )";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, year);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				for (int i = 1; i <= 12; i++) {
					String key = String.format("M%02d", i);
					Integer value = (Integer) rs.getInt(key);
					map.put(key, value);
				}
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

		return map;
	}
}