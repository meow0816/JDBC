package jdbc.day03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import jdbc.util.DBUtils;

/**
 * ResultSetMetadata：数据结果集的元数据
 *  和查询出来的结果集相关，从结果集(ResultSet)中获取
 * @author hjh
 *
 */
public class ResultSetMetadataDemo1 {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			String dql = "SELECT * FROM emp ORDER BY empno";
			ResultSet rs = stmt.executeQuery(dql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mrg = rs.getInt("mgr");
				java.sql.Date hiredate = rs.getDate("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				
				System.out.println(empno+"-"+ename+"-"+job+"-"+mrg+
						"-"+sdf.format(new Date(hiredate.getTime()))+
						"-"+sal+"-"+comm+"-"+deptno);
			
			}
			System.out.println("-----------------------------------------");
			//获取结果集元数据,元数据中封装了结果集的描述信息
			ResultSetMetaData metaData = rs.getMetaData();
			//返回此 ResultSet 对象中的列数。 
			int columnCount = metaData.getColumnCount();
			System.out.println("结果集中的列数："+columnCount);
			
			//返回结果集中指定序号的列名
			String colName1 = metaData.getColumnLabel(1);//获取第1列的列名
			String colName2 = metaData.getColumnLabel(2);//获取第2列的列名
			System.out.println("结果集中第1列的列名："+colName1);
			System.out.println("结果集中第2列的列名："+colName2);
			
			//获取指定列的数据库特定的类型名称(大写)。 
			String columnTypeName = metaData.getColumnTypeName(2);
			System.out.println("结果集中指定列的数据类型："+columnTypeName);
			
			System.out.println("-----------------------------------------");
			
			
			for(int i=1;i<=columnCount;i++) {
				System.out.println(metaData.getColumnLabel(i)+"\t"+
								   metaData.getColumnTypeName(i));
			}	
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
			DBUtils.close(conn);
		}
		
	}
}
