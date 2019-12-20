package jdbc.day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.DBUtils;

/**
 * 分页查询用户
 * @author hjh
  *    计算区间公式
 * pageSize:每页显示的条目数
 * page:页数
 * 
 * start:(page-1)*pageSize+1
 * end:pageSize*page
 */
public class PagingQueryDemo {
	public static void main(String[] args) {
		//每页显示几条？
		int pageSize = 10;
		
		//点击了第几页？
		int page = 6;
		
		//起始行
		int start = (page-1)*pageSize+1;
		//终止行
		int end = pageSize*page;
		
		Connection conn = null;
		PreparedStatement ps = null;
		String dql = "SELECT * "
				+ "FROM(SELECT ROWNUM rn,t.* "
					+ "FROM (SELECT id,name,pwd FROM user_jdbc ORDER BY id) t "
					+ "WHERE ROWNUM<=?) tt "
				+ "WHERE rn>=?";
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(dql);
			ps.setInt(1, end);
			ps.setInt(2, start);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int rn = rs.getInt("rn");
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String pwd = rs.getString("pwd");
				System.out.println(rn+","+id+","+name+","+pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
