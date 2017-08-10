import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class MySQL {
	//预处理操作，验证事物的提交与回滚
	public static void Trans_Test() throws SQLException{
		Date date=new Date();
		Connection con=getConnection();
		try{
			//事物不要随便自动关闭，否则修改的结果无法向数据库提交!!!，除非后面手动添加commit语句！
			con.setAutoCommit(false);
			String sql="update consume set csmoney = ? where mname = ?;";
			//编译预处理语句
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 20);
			ps.setString(2, "眼药水");
			ps.execute();

			ps.setInt(1, 35);
			ps.setString(2, "牛黄解毒片");
			ps.execute();
			
			con.commit();
			ps.close();
			con.close();
		}catch(SQLException ex){
			System.out.println("预处理发生异常！");
			ex.printStackTrace();
			con.rollback();
		}	
	}
		
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://127.0.0.1:3306/db_Filer";
			Connection con=DriverManager.getConnection(url, "root", "6665226");
			System.out.println("连接mysql数据库成功！");
			return con;
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("无法获取数据库驱动！");
			return null;
		}catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("数据库连接出现异常！");
			return null;
		}
	}
}
