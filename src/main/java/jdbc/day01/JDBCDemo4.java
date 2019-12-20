package jdbc.day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC(四)
 * 
 * 执行SQL语句
 * 处理结果集
 * @author hjh
 */
public class JDBCDemo4 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "hjh";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);
		String dql = "SELECT * FROM student_jdbc";
		Statement stmt = conn.createStatement();
		/*
		 * ResultSet表示数据库结果集的数据表，通常通过执行查询数据库的语句生成。
		 * ResultSet 对象具有指向其当前数据行的光标。最初，光标被置于第一行之前。
		 * next()方法将光标移动到下一行；因为该方法在 ResultSet 对象没有下一行时返回 false，
		 * 	所以可以在 while 循环中使用它来迭代结果集。 
		 */
		ResultSet rs = stmt.executeQuery(dql);
		//boolean next()方法将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；
		//第一次调用 next 方法使第一行成为当前行；第二次调用使第二行成为当前行，依此类推。
		//当调用 next 方法返回 false 时，光标位于最后一行的后面。
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");//若参数为>0的整数，第一个列是 1，第二个列是 2，…… 
			System.out.println(id+"="+name);
		}
		
		
		//关闭连接
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
