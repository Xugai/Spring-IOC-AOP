import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Server_Handler extends Thread {
	private Socket socket = null;
	private InputStream in = null;
	private InputStreamReader isr = null;
	private OutputStream out = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private String userName = null;
	private String userKey = null;
	private int key;
	boolean flag1 = false;
	boolean flag2 = false;
	boolean flag3 = false;
	
	public Server_Handler(Socket socket){
		this.socket=socket;
	}
	
	public void register() throws IOException, SQLException{
		Connection con = MySQL.getConnection();
		String sql = "insert into tb_user values(?,?);";
		//编译预处理语句
		PreparedStatement ps = con.prepareStatement(sql);
		//读取客户端传递过来的信息
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		while((userName = br.readLine())!=null){
			ps.setString(1, userName);		
			
			userKey = br.readLine();
			key = Integer.parseInt(userKey);
			ps.setInt(2, key);
		}
		socket.shutdownInput();  //关闭输入流
		//执行预处理命令，向客户端返回确认信息
		ps.execute();
		//向客户端发送确认信息
		flag1=true;
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(flag1);
		pw.flush();
	}
	
	public void login(){
		//把客户端的信息传递给数据库
		try {
			Connection con = MySQL.getConnection();
			String sql1 = "select * from tb_user";
			//执行查询语句
			ResultSet rs = con.createStatement().executeQuery(sql1);
			//打开输入流，获取从客户端传过来的账号密码
			in = socket.getInputStream();
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);
			if((userName = br.readLine()) != null){
				userKey = br.readLine();
			}
			socket.shutdownInput();  //关闭输入流

			while(rs.next()){
				if(rs.getString(1).equals(userName)&&rs.getString(2).equals(userKey)){
					flag2=true;
					break;
				}
			}
			//打开输出流，向客户端返回确认信息
			if(flag2){
				out = socket.getOutputStream();
				pw = new PrintWriter(out);
				flag1=true;
				pw.println(flag1);
				pw.flush();
				socket.shutdownOutput();   //关闭输出流
			}else{
				out = socket.getOutputStream();
				pw = new PrintWriter(out);
				pw.println(flag1);
				pw.flush();
				socket.shutdownOutput();   //关闭输出流
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void fileUpload() throws IOException,SQLException{
		Connection con = MySQL.getConnection();
		String sql = "insert into tb_file(fileName,fileContent) values(?,?);";
		PreparedStatement ps = con.prepareStatement(sql);
		
		//打开输入流，接收客户端发送过来的文件的二进制内容
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		String file = null;
		
		if((file = br.readLine()) != null){
			//获得传入文件的文件名
			String [] strs = file.split("\\\\");
			ps.setString(1, strs[strs.length-1]);
		}
		BufferedInputStream bis = new BufferedInputStream(in);
		byte [] bbuf = new byte[100*1024];
		int i = 0;
		byte [] buf = new byte[bis.available()];
		while(bis.read(buf) != -1){
			for (byte b : buf) {
				bbuf[i++] = b;
			}
		}
		byte [] bbbuf = new byte[i];
		for(int j=0;j<i;j++){
			bbbuf[j] = bbuf[j];
		}
		socket.shutdownInput();  //关闭输入流
		ps.setBytes(2, bbbuf);

		if(!ps.execute()){	
			//打开输出流，向客户端发送确认信息
			out = socket.getOutputStream();
			pw = new PrintWriter(out);
			flag3=true;
			pw.println(flag3);
			pw.flush();
			
		//	socket.shutdownOutput();  //关闭输出流
		}
	}
	
	public void run(){
		try {
			//打开输入流，接收客户端传来的指定功能选项
			in = socket.getInputStream();
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);
			int no;
			no=Integer.parseInt(br.readLine());
			switch(no){
			case 1:
				register();
				break;
			case 2:
				login();
				break;
			case 3:
				fileUpload();
				break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
