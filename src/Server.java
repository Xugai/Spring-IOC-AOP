import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * ʹ��TCP��������������ͻ��˵�����
 */
public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			boolean flag1=true;
			while(flag1){
				Socket socket = serverSocket.accept();
				Server_Handler sh = new Server_Handler(socket);
				sh.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
