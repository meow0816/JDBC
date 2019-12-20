package jdbc.day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.DBUtils;

/**
 * 返回自动主键
 * @author hjh
  *  主表：keywords_jdbc
 *  	字段id  主键
 *  	字段word
 *  	序列：seq_keywords_id
  *   从表： post_jdbc
 *   	字段id  主键
 *   	字段content
 *   	字段 k_id 外键   
 *   	序列：seq_keywords_id
 *   注意：序列发生后不可回退，不会参与到事务
   *   要点：增加从表后keywords_jdbc如何获得生成的部门ID
   *   (一个事务，一个连接)
 */
public class GeneratedKeysDemo1 {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			
			//给主表添加一条记录
			String sql = "INSERT INTO keywords_jdbc(id,word) "
						+ "VALUES(seq_keywords_id.NEXTVAL,?)";
			//创建一个能返回由给定数组指定的自动生成键的默认 PreparedStatement 对象。
			//此数组包含目标表中列(字段)的名称，而目标表包含应该返回的自动生成键。
			//参数2是一个数组，用来告诉PS，需要它返回哪些字段，即自动生产序号的列名
			String[] columnNames = {"id"};
			ps = conn.prepareStatement(sql,columnNames);
			ps.setString(1, "#雾霾#");
			int num = ps.executeUpdate();
			System.out.println("表keywords_jdbc受影响的记录数："+num);
			if (num!=1) {
				throw new Exception("话题添加失败！");
			}

			int pk = -1;
			//获取表keywords_jdbc自动生成的主键值
			//该方法获取由于执行此 Statement 对象而创建的所有自动生成的键。
			//如果此 Statement 对象没有生成任何键，则返回空的 ResultSet 对象。 
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()) {
				//这种场景下的结果集，只能通过字段的序号获取值
				pk = rs.getInt(1);
			}
			DBUtils.close(ps);//关闭statement
			
			//给从表增加一条记录，第二个参数的值是从主表中获取的自动生成的主键值
			sql = "INSERT INTO post_jdbc(id,content,k_id) "
				+ "VALUES(seq_post_kid.NEXTVAL,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "今天天气很烂！");
			ps.setInt(2, pk);//参数是获取到的自动生成的主键值
			num = ps.executeUpdate();
			System.out.println("表post_jdbc受影响的记录数："+num);
			if (num!=1) {
				throw new Exception("动态发布！");
			}
			conn.commit();
			System.out.println("OK!");
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		}catch (Exception e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
		
		
	}
}
