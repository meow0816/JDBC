package jdbc.day02;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jdbc.util.DBUtils;

/**
  *  使用PreparedStatement对象执行DML语句
 * @author hjh
 *
 */
public class PreparedStatementDemo3 {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			String dml = "UPDATE student_jdbc SET name=? WHERE id=?";
			
			ps = conn.prepareStatement(dml);
			System.out.println(ps);
			
			ps.setString(1, "卡布达");
			ps.setInt(2, 1002);
			int num = ps.executeUpdate();
			System.out.println("受影响的记录数："+num);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
	}
}
