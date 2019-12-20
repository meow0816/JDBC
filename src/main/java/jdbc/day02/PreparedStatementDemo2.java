package jdbc.day02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.util.DBUtils;

/**
  *  使用PreparedStatement对象执行DQL语句
 * @author hjh
 *
 */
public class PreparedStatementDemo2 {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			String dql = "SELECT * FROM (SELECT ROWNUM rn,t.* FROM "
							+ "(SELECT * FROM student_jdbc ORDER BY id) t WHERE ROWNUM<=?) tt "
							+ "WHERE rn>=?";
			ps = conn.prepareStatement(dql);
			ps.setInt(1, 4);
			ps.setInt(2, 2);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int rn = rs.getInt("rn");
				int id = rs.getInt("id");
				String name = rs.getString("name");
				System.out.println(rn+"==="+id+"==="+name);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
	}
}
