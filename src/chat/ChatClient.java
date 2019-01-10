package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 8088;
	
	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		
		try {
			sc = new Scanner(System.in);
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
			
			System.out.print("nickname > ");
			String nickname = sc.nextLine();
			pw.println("join:"+nickname);
			
			new ChatClientThread(br).start();
			
			while(true) {
				//System.out.print("> ");
				String protocol = sc.nextLine();
				
				if(protocol.equals("quit")) {
					pw.println(protocol);
					break;
				}else {
					pw.println("message:"+protocol);
				}
			}
			
		} catch (SocketException e) {
			System.out.println("서버 종료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed()) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
