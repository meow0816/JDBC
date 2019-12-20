package jdbc.druid;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * druid连接池
 * @author hjh
 *
 */
public class DruidDemo1 {
	public static void main(String[] args) throws Exception {
		//DruidDataSourceFactory.createDataSource(Map properties)
		Properties prop = new Properties();
		prop.load(DruidDemo1.class.getClassLoader()
				.getResourceAsStream("druidConf.properties"));
		DataSource ds = DruidDataSourceFactory.createDataSource(prop);
		Connection conn = ds.getConnection();
		System.out.println(conn);
		
		
		
		
		
	}
}
