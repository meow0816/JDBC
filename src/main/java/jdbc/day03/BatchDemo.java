package jdbc.day03;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import jdbc.util.DBUtils;

/**
 * 批量更新
 * 
 * 批量执行DDL
 * 		(DDL不支持事务)
 * @author hjh
 *
 */
public class BatchDemo {
	public static void main(String[] args) {
		String[] sql = new String[12];
		//生成SQL语句
		for (int i = 0; i < sql.length; i++) {
			sql[i]="CREATE TABLE log_"+(i+1)+" (id NUMBER(8),"
					 + "msg VARCHAR2(100)"
					 + ")";
		}
		
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			//将给定的 SQL 命令添加到此 Statement 对象的当前命令列表中。
			//通过调用方法 executeBatch()可以批量执行此列表中的命令。
			stmt.addBatch(sql[0]);
			stmt.addBatch(sql[1]);
			stmt.addBatch(sql[2]);
			
			// 将一批命令提交给数据库来执行，如果全部命令执行成功，则返回更新计数组成的数组。
			//将ps对象列表中的所有sql语句发送到数据库进行处理
			int[] arr = stmt.executeBatch();
//			参考executeBatch()方法说明：
//			数组中元素的值大于等于 0 的数 - 指示成功处理了命令，是给出执行命令所影响数据库中行数的更新计数 
//			值为-3表示执行失败
			
			
			//参考API手册java.sql.*的常量字段值 
			System.out.println(Arrays.toString(arr));
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			System.out.println("ok");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
			DBUtils.close(conn);
		}
		
	}
	
	
}
