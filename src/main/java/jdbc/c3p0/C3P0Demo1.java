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
public class C3P0Demo1 {
	public static void main(String[] args) {
		//1.创建数据库连接池对象
		DataSource ds = new ComboPooledDataSource();
		try {
			//2.获取连接
			Connection conn = ds.getConnection();
			
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取连接失败！",e);
		}
		
	}
}
