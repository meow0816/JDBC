package jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC 数据库连接管理(一)
 * 
 * 编写一个工具类来简化JDBC的开发步骤
 * 封装创建连接数据库的过程。
 * 注册驱动，创建连接和关闭连接...
 * 
 * java.util.Properties类可以读取*.properties文件
 * Properties实现了Map接口，内部时散列表，限定key和value
 *   都是String类型。
 * 
 * @author hjh
 *
 */
public class DBTool {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	//执行顺序静态代码块>构造代码块>构造函数>普通代码块　
	//初始化连接参数及注册驱动
	static {
		//使用java.util.Properties类读取properties文件
		Properties prop = new Properties();
		//使用ClassLoader从classes下读取db.properties
		ClassLoader classLoader = DBTool.class.getClassLoader();
		try {
			//加载配置文件
			prop.load(classLoader.getResourceAsStream("db.properties"));
			//System.out.println(prop);
			//System.out.println(prop.size());
			
			//读取properties文件来初始化连接参数
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
			try {
				//注册驱动，只需注册一次
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("找不到这个驱动！",e);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载db.properties失败！",e);
		}
		
		
	}
	
	//获取连接
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	//关闭Connection连接
	public static void close(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭连接失败！",e);
			}
		}
	}
	//关闭Statement
	public static void close(Statement stmt) {
		if (stmt!=null) {
			try {
				//关闭 Statement 对象时，还将同时关闭其当前的 ResultSet 对象（如果有）。 
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭Statement失败！",e);
			}
		}
	}
	
	
	
	
	
	
	
	
}
