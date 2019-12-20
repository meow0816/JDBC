package jdbc.day02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.util.DBUtils;

/**
  *  演示SQL注入
 * @author hjh
 * CREATE TABLE user_jdbc(
 * 	id NUMBER(6),
 *  name VARCHAR2(50),
 *  pwd VARCHAR2(50)
 * );
 * INSERT INTO user_jdbc(id,name,pwd) VALUES (1,'Tom',123);
 * INSERT INTO user_jdbc(id,name,pwd) VALUES (2,'Jerry',123);
 * 
 */
public class PreparedStatementDemo4 {
	public static void main(String[] args) {
		//假设用户输入的登录条件是：
		String name = "hi";
		//String password = "a' OR 'b'='b";--字符串：a' OR 'b'='b
		String pwd = "a' OR 'b'='b";
		/*任意的用户名和密码都可以登录
		 * sql=SELECT COUNT(*) AS c FROM user_jdbc WHERE name='hi' AND pwd='a' OR 'b'='b' 
		 * 					                      先计算AND得到false ，再计算 false OR true 得到true
		 * 扭转了SQL语句的语义
		 */
		
		//检查登陆情况
		boolean flag = login(name,pwd);
		System.out.println(flag==true ? "欢迎你！"+name : "用户名或密码错误");
		
		flag = login1(name,pwd);
		System.out.println(flag==true ? "欢迎你！"+name : "用户名或密码错误");
	}
	
	/**
	 * 使用PreparedStatement能够避免SQL注入
	 * @param name
	 * @param pwd
	 * @return
	 */
	public static boolean login1(String name,String pwd) {
		String sql = "SELECT COUNT(*) AS c "
				+ "FROM user_jdbc "
				+ "WHERE name=? AND pwd=?";
		System.out.println("sql="+sql);
		
		Connection conn = null;
		//Statement stmt = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			//stmt = conn.createStatement();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pwd);
//			ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int n = rs.getInt("c");
				return n>=1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		
		return false;
	}
	
	
	//该方法校验用户名和密码是否能够登录
	public static boolean login(String name,String pwd) {
		String sql = "SELECT COUNT(*) AS c "
				+ "FROM user_jdbc "
				+ "WHERE name=\'"+name+"\' "
				+ "AND pwd=\'"+pwd+"\' ";
		System.out.println("sql="+sql);
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int n = rs.getInt("c");
				return n>=1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(stmt);
			DBUtils.close(conn);
		}
		
		return false;
	}
	
}
