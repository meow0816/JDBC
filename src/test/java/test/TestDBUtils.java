package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import jdbc.util.DBUtils;

public class TestDBUtils {
	@Test
	public void test1() {
		//测试DBUtils类
		
		Connection conn = null;
		//获得连接
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			String dql = "SELECT 'hello' AS a FROM dual";
			ResultSet rs = stmt.executeQuery(dql);
			while (rs.next()) {
				String str = rs.getString("a");
				System.out.println("查询结果："+str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
			DBUtils.close(conn);
		}
		
			
			
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
