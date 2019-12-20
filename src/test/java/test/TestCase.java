package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import jdbc.util.DBUtils;

public class TestCase {
	@Test
	public void test1() {
		//事务Demo
		Connection conn = null;
		//获得连接
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			String dql = "SELECT * FROM account_jdbc";
			ResultSet rs = stmt.executeQuery(dql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Double balance = rs.getDouble("balance");
				System.out.println("查询结果："+id+","+name+","+balance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
			DBUtils.close(conn);
		}
		
			
			
			
		
	}
	
}
