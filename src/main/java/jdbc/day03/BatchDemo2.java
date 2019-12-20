package jdbc.day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import jdbc.util.DBUtils;

/**
 * 批量参数处理，DML
 * @author hjh
 * https://www.jianshu.com/p/f73078789ea3
 *
 */
public class BatchDemo2 {
	/**
	 * 批量增加用户到user_jdbc表
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "INSERT INTO user_jdbc (id,name,pwd) "
					+"VALUES (?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			//取消自动提交事务
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			
			int[] arr = null;
			//批量增加108个用户
			for(int i=1;i<=108;i++) {
				//替换参数
				ps.setInt(1, i);
				ps.setString(2, "name"+i);
				ps.setString(3, "123"+i);
				
				//将参数添加到缓存区，将一组参数添加到此 PreparedStatement 对象的批处理命令中。
				ps.addBatch();
				
				//每50条语句批量提交一次
				if(i%50==0) {
					//将ps对象列表中的所有sql语句发送到数据库进行处理
					//将一批命令提交给数据库来执行，如果全部命令执行成功，则返回更新计数组成的数组。
					arr = ps.executeBatch();
					System.out.println(Arrays.toString(arr));
					//清空此 Statement 对象的当前 SQL 命令列表。 
					//清空当前ps中暂存的数据，便于下一批添加
					ps.clearBatch();
				}
				
			}
			
			//为避免有零头，批量执行再多提交到数据库一次
			arr = ps.executeBatch();
			//数组的元素值为-2该常量指示批量语句执行成功但不存在受影响的可用行数计数。 
			System.out.println(Arrays.toString(arr));
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
		
		
		
		
		
	}
}
