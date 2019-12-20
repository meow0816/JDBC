package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import jdbc.util.DBTool;

public class TestDBTool {
	@Test
	public void test1() {
		//测试DBTool工具类
		Connection conn = null;
		Statement stmt = null;
		try {
			//1.获取连接
			conn = DBTool.getConnection();
			//2.创建Statement执行SQL语句
			stmt = conn.createStatement();
			String dql = "SELECT * FROM student_jdbc";
			ResultSet rs = stmt.executeQuery(dql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				System.out.println(id+"==="+name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取连接失败！",e);
		} finally {
			//关闭连接资源
			//关闭 Statement 对象时，还将同时关闭其当前的 ResultSet 对象（如果有）。 
			DBTool.close(stmt);
			DBTool.close(conn);
		}
		
		
	}
}
