import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	static String userName = null;
	static String userKey = null;
	static String repeatKey = null;
	static boolean flag1 = false;
	
	static InetAddress address;
	static InputStream in = null;
	static InputStreamReader isr = null;
	static OutputStream out = null;
	static BufferedReader br = null;
	static PrintWriter pw = null;
	static String str = null;
	
	public static void register(Socket socket) throws IOException{
		Scanner input = new Scanner(System.in);
		System.out.println("�������û�����");
		userName = input.next();
		System.out.println("���������룺");
		userKey = input.next();
		System.out.println("���ٴ�ȷ������������룺");
		repeatKey = input.next();
		while(!userKey.equals(repeatKey)){
			System.out.println("����ȷ�ϴ������������룺");
			System.out.println("�������û�����");
			userName = input.next();
			System.out.println("���������룺");
			userKey = input.next();
			System.out.println("���ٴ�ȷ������������룺");
			repeatKey = input.next();
		}		
		//��������˴�����Ϣ
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(userName);
		pw.flush();	 //���������
		pw.println(userKey);
		pw.flush();  //���������
		socket.shutdownOutput();  //�ر������
		
		//��ȡ�������˷��ع�����ȷ����Ϣ
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);								
		if((flag1 = Boolean.parseBoolean(br.readLine()))){
			System.out.println("ע��ɹ���");
		}else{
			System.out.println("ע��ʧ�ܣ�");
		}
	}

	public static void login(Socket socket) throws IOException{
		System.out.println("�������û�����");
		Scanner input = new Scanner(System.in);
		userName = input.next();
		System.out.println("���������룺");
		userKey = input.next();
		//�������
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(userName);
		pw.flush();
		pw.println(userKey);
		pw.flush();
		socket.shutdownOutput();  //�ر������

		//������������ȡ�������˷��ص�ȷ����Ϣ
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		if((flag1 = Boolean.parseBoolean(br.readLine()))){
			socket.shutdownInput();   //�ر�������
			System.out.println("��¼�ɹ���");
		}else{
			System.out.println("��¼ʧ�ܣ�");
		}
	}
	
	public static void fileUpload(Socket socket) throws IOException{
		Scanner input = new Scanner(System.in);
		System.out.println("�������ļ��ľ���·����");
		String file = null;
		file = input.next();
		//��ȡ�ļ������ļ�ת��Ϊ�ֽ����ݣ������ֽ�������
		FileInputStream fis = new FileInputStream(file);
		byte [] buf = new byte[fis.available()];
		
		// �ٴ�����������ֽ����鷢�͸���������
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(file);
		pw.flush();

		BufferedOutputStream bos = new BufferedOutputStream(out);
		while(fis.read(buf) != -1){
			bos.write(buf);
			bos.flush();
		}
		socket.shutdownOutput(); // �ر������
		
		//�������������շ������˷��ص�ȷ����Ϣ
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		if(Boolean.parseBoolean(br.readLine())){
			System.out.println("�ļ��ϴ��ɹ���");
			socket.shutdownInput();   //�ر�������
		}
	}
	
	
	
	public static void main(String[] args) {	
		try {
			address = InetAddress.getByName("localhost");
			Socket socket = new Socket(address, 1234);
			Scanner input = new Scanner(System.in);
			//��������������������ָ������ѡ��
			out = socket.getOutputStream();
			pw = new PrintWriter(out);
			System.out.println("**********��ѡ��ִ�еĹ��ܣ�************");
			System.out.println("1��ע���û�");
			System.out.println("2���û���¼");
			System.out.println("3���ļ��ϴ�");
			int no;
			no = input.nextInt();
			switch(no){
			case 1:
				pw.println(1);
				pw.flush();
	//			socket.shutdownOutput();   //�ر������
				register(socket);
				break;
			case 2:
				pw.println(2);
				pw.flush();
	//			socket.shutdownOutput();   //�ر������
				login(socket);
				break;
			case 3:
				pw.println(3);
				pw.flush();
				fileUpload(socket);
				break;
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
