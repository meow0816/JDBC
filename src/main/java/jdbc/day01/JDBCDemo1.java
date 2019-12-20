package jdbc.day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC(一)
 * 
 * 1.加载驱动
 * 2.建立连接
 * 3.执行sql
 * 4.处理结果集
 * 5.关闭连接
 * @author hjh
 *
 */
public class JDBCDemo1 {
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static final String USER = "hjh";
	private static final String PASSWORD = "root";
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//实现java.sql.Driver的驱动类可能在静态块里实例化了Driver对象并使用
			//DriverManager.registerDriver(new oracle.jdbc.OracleDriver());来注册驱动
			//1.注册驱动
			Class.forName("oracle.jdbc.OracleDriver");
			// oracle.jdbc.OracleDriver实现了java.sql.Driver接口
			//DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			
			//2.创建连接
			//试图建立到给定数据库 URL 的连接。
			//DriverManager 试图从已注册的 JDBC 驱动程序集中选择一个适当的驱动程序。 
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//Connection可以获取执行SQL的对象、管理事务
			//System.out.println(conn);
			//System.out.println(conn.getClass());
			
			String ddl = "CREATE TABLE student_jdbc("+
								"id NUMBER(10),"+
								"name VARCHAR2(50)"+
						 ")";
			//3.创建Statement对象来执行SQL语句,
			//Statement用于执行静态 SQL 语句并返回它所生成结果的对象。 
			Statement stmt = conn.createStatement();
			//该方法执行给定的 SQL 语句，该语句可能返回多个结果。多用于执行DDL
			//若已存在该数据库对象则抛出异常：java.sql.SQLException: ORA-00955: 名称已由现有对象使用
			//如果没有发生异常，则表创建成功！
			boolean flag = stmt.execute(ddl);
			System.out.println("表student_jdbc是否创建成功："+flag);
			/*
			 * true：表示有结果集
			 * false：表示没有结果集
			 */
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn!=null) {
				try {
					//关闭连接
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
		
		
	}
}
