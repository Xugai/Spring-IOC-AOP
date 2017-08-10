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
		//����Ԥ�������
		PreparedStatement ps = con.prepareStatement(sql);
		//��ȡ�ͻ��˴��ݹ�������Ϣ
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		while((userName = br.readLine())!=null){
			ps.setString(1, userName);		
			
			userKey = br.readLine();
			key = Integer.parseInt(userKey);
			ps.setInt(2, key);
		}
		socket.shutdownInput();  //�ر�������
		//ִ��Ԥ���������ͻ��˷���ȷ����Ϣ
		ps.execute();
		//��ͻ��˷���ȷ����Ϣ
		flag1=true;
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(flag1);
		pw.flush();
	}
	
	public void login(){
		//�ѿͻ��˵���Ϣ���ݸ����ݿ�
		try {
			Connection con = MySQL.getConnection();
			String sql1 = "select * from tb_user";
			//ִ�в�ѯ���
			ResultSet rs = con.createStatement().executeQuery(sql1);
			//������������ȡ�ӿͻ��˴��������˺�����
			in = socket.getInputStream();
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);
			if((userName = br.readLine()) != null){
				userKey = br.readLine();
			}
			socket.shutdownInput();  //�ر�������

			while(rs.next()){
				if(rs.getString(1).equals(userName)&&rs.getString(2).equals(userKey)){
					flag2=true;
					break;
				}
			}
			//�����������ͻ��˷���ȷ����Ϣ
			if(flag2){
				out = socket.getOutputStream();
				pw = new PrintWriter(out);
				flag1=true;
				pw.println(flag1);
				pw.flush();
				socket.shutdownOutput();   //�ر������
			}else{
				out = socket.getOutputStream();
				pw = new PrintWriter(out);
				pw.println(flag1);
				pw.flush();
				socket.shutdownOutput();   //�ر������
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
		
		//�������������տͻ��˷��͹������ļ��Ķ���������
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		String file = null;
		
		if((file = br.readLine()) != null){
			//��ô����ļ����ļ���
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
		socket.shutdownInput();  //�ر�������
		ps.setBytes(2, bbbuf);

		if(!ps.execute()){	
			//�����������ͻ��˷���ȷ����Ϣ
			out = socket.getOutputStream();
			pw = new PrintWriter(out);
			flag3=true;
			pw.println(flag3);
			pw.flush();
			
		//	socket.shutdownOutput();  //�ر������
		}
	}
	
	public void run(){
		try {
			//�������������տͻ��˴�����ָ������ѡ��
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
