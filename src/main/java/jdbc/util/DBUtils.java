package jdbc.util;
/**
 * JDBC 数据库连接管理(二)
 * 
 * @author hjh
 *	数据库连接池：
 *		系统初始化运行时，主动建立足够的连接，组成一个池。每次应用程序
 *		请求数据库连接时，无需重新打开连接，而是从连接池中取出已有的连接，
 *		使用完后，不再关闭，而是归还。
 *	使用Apache DBCP连接池：
 *		DBCP(DateBase connection pool)：数据库连接池。
 *	        是Apache的一个Java连接池开源项目，同时也是Tomcat使用的连接池组件。
 *		连接池是创建和管理连接的缓冲池技术，将连接准备好被任何需要他们的英语使用。
 *		
 *	导入DBCP的jar包
 *	
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtils {
	//连接池对象-由DBCP提供，BasicDataSource实现了javax.sql.DataSource接口
	private static BasicDataSource ds;
	
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	private static Integer initialSize;
	private static Integer maxActive;
	//在static块中初始化连接池参数
	static {
		//使用Properties对象读取配置文件
		Properties prop = new Properties();
		try {
			prop.load(DBUtils.class.getClassLoader().getResourceAsStream("db.properties"));
			//初始化参数
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			initialSize = Integer.valueOf(prop.getProperty("initialSize"));
			maxActive = Integer.valueOf(prop.getProperty("maxActive"));
			
			//初始化连接池对象
			ds = new BasicDataSource();
			ds.setDriverClassName(driver);//注册驱动
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(password);
			//设置连接池的管理策略参数
			ds.setInitialSize(initialSize);
			ds.setMaxActive(maxActive);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载db.properties失败！", e);
		}
		
	}
	
	
	//获取连接
	public static Connection getConnection() throws SQLException{
		//返回通过连接池对象获取重用的连接，如果连接满了则等待
		//如果有连接归还，则获取重用的连接
		//try {
			return ds.getConnection();
		//} catch (SQLException e) {
		//	e.printStackTrace();
		//	throw new RuntimeException("获取连接失败！",e);
		//}
	}
	
	/**
	 *   由连接池创建的连接，连接的close()方法
	 *   被连接池重写了，变为了归还连接的逻辑
	 *   即：连接池会将连接的状态设置为空闲，并
	 *   清空连接中所包含的任何数据。
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("归还连接失败！",e);
			}
		}
	}
	
	public static void close(Statement stmt) {
		if (stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Statement关闭失败！",e);
			}
		}
	}
	
	
	//回滚
	public static void rollback(Connection conn) {
		if(conn!=null){
			try {
				//取消在当前事务中进行的所有更改，
				//并释放此 Connection 对象当前持有的所有数据库锁。
				//此方法只应该在已禁用自动提交模式时使用。 				
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("回滚失败！",e);
			}
		}
	}
	
	
	
	
	
	
	
	
	
}
