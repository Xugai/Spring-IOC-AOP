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
		System.out.println("请输入用户名：");
		userName = input.next();
		System.out.println("请输入密码：");
		userKey = input.next();
		System.out.println("请再次确认您输入的密码：");
		repeatKey = input.next();
		while(!userKey.equals(repeatKey)){
			System.out.println("密码确认错误！请重新输入：");
			System.out.println("请输入用户名：");
			userName = input.next();
			System.out.println("请输入密码：");
			userKey = input.next();
			System.out.println("请再次确认您输入的密码：");
			repeatKey = input.next();
		}		
		//向服务器端传入信息
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(userName);
		pw.flush();	 //将缓冲输出
		pw.println(userKey);
		pw.flush();  //将缓冲输出
		socket.shutdownOutput();  //关闭输出流
		
		//读取服务器端返回过来的确认信息
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);								
		if((flag1 = Boolean.parseBoolean(br.readLine()))){
			System.out.println("注册成功！");
		}else{
			System.out.println("注册失败！");
		}
	}

	public static void login(Socket socket) throws IOException{
		System.out.println("请输入用户名：");
		Scanner input = new Scanner(System.in);
		userName = input.next();
		System.out.println("请输入密码：");
		userKey = input.next();
		//打开输出流
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(userName);
		pw.flush();
		pw.println(userKey);
		pw.flush();
		socket.shutdownOutput();  //关闭输出流

		//打开输入流，获取服务器端返回的确认信息
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		if((flag1 = Boolean.parseBoolean(br.readLine()))){
			socket.shutdownInput();   //关闭输入流
			System.out.println("登录成功！");
		}else{
			System.out.println("登录失败！");
		}
	}
	
	public static void fileUpload(Socket socket) throws IOException{
		Scanner input = new Scanner(System.in);
		System.out.println("请输入文件的绝对路径：");
		String file = null;
		file = input.next();
		//读取文件，把文件转化为字节内容，放入字节数组中
		FileInputStream fis = new FileInputStream(file);
		byte [] buf = new byte[fis.available()];
		
		// 再打开输出流，把字节数组发送给服务器端
		out = socket.getOutputStream();
		pw = new PrintWriter(out);
		pw.println(file);
		pw.flush();

		BufferedOutputStream bos = new BufferedOutputStream(out);
		while(fis.read(buf) != -1){
			bos.write(buf);
			bos.flush();
		}
		socket.shutdownOutput(); // 关闭输出流
		
		//打开输入流，接收服务器端返回的确认信息
		in = socket.getInputStream();
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		if(Boolean.parseBoolean(br.readLine())){
			System.out.println("文件上传成功！");
			socket.shutdownInput();   //关闭输入流
		}
	}
	
	
	
	public static void main(String[] args) {	
		try {
			address = InetAddress.getByName("localhost");
			Socket socket = new Socket(address, 1234);
			Scanner input = new Scanner(System.in);
			//打开输出流，向服务器传递指定功能选项
			out = socket.getOutputStream();
			pw = new PrintWriter(out);
			System.out.println("**********请选择执行的功能：************");
			System.out.println("1、注册用户");
			System.out.println("2、用户登录");
			System.out.println("3、文件上传");
			int no;
			no = input.nextInt();
			switch(no){
			case 1:
				pw.println(1);
				pw.flush();
	//			socket.shutdownOutput();   //关闭输出流
				register(socket);
				break;
			case 2:
				pw.println(2);
				pw.flush();
	//			socket.shutdownOutput();   //关闭输出流
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
