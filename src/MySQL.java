import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class MySQL {
	//Ԥ�����������֤������ύ��ع�
	public static void Trans_Test() throws SQLException{
		Date date=new Date();
		Connection con=getConnection();
		try{
			//���ﲻҪ����Զ��رգ������޸ĵĽ���޷������ݿ��ύ!!!�����Ǻ����ֶ����commit��䣡
			con.setAutoCommit(false);
			String sql="update consume set csmoney = ? where mname = ?;";
			//����Ԥ�������
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 20);
			ps.setString(2, "��ҩˮ");
			ps.execute();

			ps.setInt(1, 35);
			ps.setString(2, "ţ�ƽⶾƬ");
			ps.execute();
			
			con.commit();
			ps.close();
			con.close();
		}catch(SQLException ex){
			System.out.println("Ԥ�������쳣��");
			ex.printStackTrace();
			con.rollback();
		}	
	}
		
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://127.0.0.1:3306/db_Filer";
			Connection con=DriverManager.getConnection(url, "root", "6665226");
			System.out.println("����mysql���ݿ�ɹ���");
			return con;
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("�޷���ȡ���ݿ�������");
			return null;
		}catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("���ݿ����ӳ����쳣��");
			return null;
		}
	}
}
