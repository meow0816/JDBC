package jdbc.c3p0;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * C3P0连接池
 * https://blog.csdn.net/qq_42035966/article/details/81332343
 * @author hjh
 *
 */
public class C3P0Demo2 {
	public static void main(String[] args) {
		//1.创建数据库连接池对象，构造方法不传参的话使用默认配置<default-config>
		DataSource ds = new ComboPooledDataSource();
		
		
		try {
			
			//获取连接池的初始化连接池个数
			for (int i = 1; i <=10; i++) {
				Connection conn = ds.getConnection();
				System.out.println(i+":"+conn);
				
				if (i==5) {
					//归还获得的第5个连接
					conn.close();
				}
			}
			System.out.println("--------------------------------------");
			testNamedConfig();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void testNamedConfig() throws SQLException {
		//使用配置文件中指定命名空间的配置
		DataSource ds = new ComboPooledDataSource("otherC3P0");
		//获取连接池的初始化连接池个数
		for (int i = 1; i <=8; i++) {
			Connection conn = ds.getConnection();
			System.out.println(i+":"+conn);
			
			if (i==5) {
				//归还获得的第5个连接
				conn.close();
			}
		}
	}
	
	
	
}
