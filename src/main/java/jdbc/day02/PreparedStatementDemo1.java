package jdbc.day02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.util.DBUtils;

/**
 * public interface PreparedStatement extends Statement
 * PreparedStatement接口继承了Statement
 * PreparedStatement表示预编译的 SQL 语句的对象。 
 * SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。 
 * @author hjh
 *
 */
public class PreparedStatementDemo1 {
	public static void main(String[] args) {
		Connection conn = null;
		
		//创建带参数的SQL语句。
		String dml = "INSERT INTO student_jdbc (id,name) VALUES(?,?)";
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			//创建一个 PreparedStatement 对象来将参数化的 SQL 语句发送到数据库。
			/*
			 * 注：为了处理受益于预编译的带参数 SQL 语句，此方法进行了优化。
			 * 如果驱动程序支持预编译，则 prepareStatement 方法将该语句发送给数据库进行预编译。
			 * 一些驱动程序可能不支持预编译。在这种情况下，执行 PreparedStatement 对象之前无法将语句发送给数据库。
			 * 这对用户没有直接影响；但它的确会影响哪些方法将抛出某些 SQLException 对象。 
			 */
			ps = conn.prepareStatement(dml);
			//给参数?赋值，preparedstatement.set类型(?的序号,?的值)
			ps.setInt(1, 1007);
			ps.setString(2, "潘森");
			int num = ps.executeUpdate();//执行SQL语句
			System.out.println("受影响的记录数："+num);
			
			
			ps.setInt(1, 1006);
			ps.setString(2, "辛德拉");
			num = ps.executeUpdate();//执行SQL语句
			System.out.println("受影响的记录数："+num);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
		
		
		
		
	}
}
