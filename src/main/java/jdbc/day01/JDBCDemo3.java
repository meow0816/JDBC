package jdbc.day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC(三)
 * 
 * 执行DML语句
 * @author hjh
 */
public class JDBCDemo3 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "hjh";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
//		String dml = "INSERT INTO student_jdbc (id,name) "+
//					 				"VALUES(1516040817,'小麦')";

//		String dml = "UPDATE student_jdbc SET name='小菊花' WHERE id=1516040817";
		
		String dml = "DELETE FROM student_jdbc WHERE name='小菊花'";
		
		//执行给定 SQL 语句，该语句可能为 INSERT、UPDATE 或 DELETE 语句，
		//返回值：(1) 对于 SQL 数据操作语言 (DML) 语句，返回行计数 (2) 对于什么都不返回的 SQL 语句，返回 0 
		int num = stmt.executeUpdate(dml);
		System.out.println("受影响的记录数："+num);
		
		if (stmt!=null) {
			//立即释放此 Statement 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。
			//注：关闭 Statement 对象时，还将同时关闭其当前的 ResultSet 对象（如果有）。 


			stmt.close();
		}
		if (conn!=null) {
			conn.close();
		}
		
	}
}
