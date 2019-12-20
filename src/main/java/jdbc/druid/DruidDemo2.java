package jdbc.druid;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import jdbc.druid.util.JDBCUtils;

/**
 * druid连接池
 * JDBCUtils类测试
 * @author hjh
 *
 */
public class DruidDemo2 {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = JDBCUtils.getConnection();
//			System.out.println(conn);
			stmt = conn.createStatement();
			String sql = "SELECT SYSDATE FROM DUAL";
			ResultSet rs = stmt.executeQuery(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (rs.next()) {
				Date date = rs.getDate(1);
				System.out.println(sdf.format(new java.util.Date(date.getTime())));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取连接失败",e);
		} finally {
			JDBCUtils.close(stmt, conn);
		}
		
		
	}
}
