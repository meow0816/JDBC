package jdbc.day01;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC(二)
 * 
 * 通过读取properties文件来配置加载驱动和建立连接等步骤
 * 
 * 1.使用java.util.Properties类
 * 2.它本质上就Map
 * 3.它专门用来读取properties文件
 * @author hjh
 */
public class JDBCDemo2 {
	public static void main(String[] args){
		//1.创建Properties对象
		Properties prop = new Properties();
		//2.获得类加载器
		ClassLoader classLoader = JDBCDemo2.class.getClassLoader();
		//3.获取指定资源的字节流，该方法返回读取指定资源的输入流。
		InputStream is = classLoader.getResourceAsStream("db.properties");
		
		Connection conn = null;
		try {
			//4.从输入流中读取属性列表（键和元素对）
			prop.load(is);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			//5.注册驱动
			Class.forName(driver);
			//6.创建连接
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
			//7.创建Statement对象来执行SQL语句
			Statement stmt = conn.createStatement();
			System.out.println(stmt);
			//....
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
