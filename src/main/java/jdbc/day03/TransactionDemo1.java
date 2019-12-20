package jdbc.day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.DBUtils;

/**
 * 事务
 * 模拟转账业务
 * 
 * 前提：
 * 	假设用户已经登录网银，并且
 * 	输入了收款方的帐号，以及
 * 	要转账的金额。
 * 
 * 步骤：
 * 1.查询付款帐号，看余额够不够
 * 2.查询收款帐号，看帐号对不对
 * 3.修改付款余额，金额-N
 * 4.修改收款余额，金额+N
 * 
 * 注意：转账是一个完整的业务，要
 * 使用一个连接，保障只有一个事务。
 */
public class TransactionDemo1 {
	public static void main(String[] args) {
		//1002向1000转账1000
		pay(1000, 1002, 1000);
		System.out.println("ok");
	}
	/**
	 * 保证汇款的操作要么发生，要么不发生，是不可分割的，
	 * 这体现了事务的特性。原子性，一致性
	 * @param from
	 * @param to
	 * @param money
	 */
	//转账方法
	public static void pay(int from,int to,double money) {
		String sql1 = "UPDATE account_jdbc SET balance=balance+? "
						+ "WHERE id=?";
		String sql2 = "SELECT balance FROM account_jdbc "
						+ "WHERE id=?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);//取消自动提交
			ps = conn.prepareStatement(sql1);
			//----------------原子操作-------------------------
			//从转账方账户减钱
			ps.setDouble(1, -money);
			ps.setInt(2, from);
			int num = ps.executeUpdate();
			if (num!=1) {
				throw new Exception("扣错啦");
			}
			
			//给收款账户加钱
			ps.setDouble(1, money);
			ps.setInt(2, to);
			num = ps.executeUpdate();
			if (num!=1) {
				throw new Exception("收款方不存在");
			}
			//立即释放此 Statement 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。
			DBUtils.close(ps);
			//检查转账方的余额
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, from);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				double bal = rs.getDouble(1);
				if (bal<0) {
					throw new Exception("转账方账户透支");
				}
			}
			
			
			
			conn.commit();//手动提交
		} catch (Exception e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally {
			//DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
		
		
		
	}
	
	
	
	
}
