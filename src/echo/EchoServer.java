package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			// 2. Binding
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localHostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress, PORT);
			serverSocket.bind(inetSocketAddress);
			log("binding"+localHostAddress+":"+PORT);
			
			// 3. Accept
			Socket socket = serverSocket.accept();
			
			InetSocketAddress inetRemoteSocketAddress =
					(InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();
			log("connected by client["+
							remoteHostAddress+":"+remoteHostPort+"]");
			try {
				// 4. IOStream 받아오기
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
				
				while(true) {
					// 5. 데이터 읽기
					String data = br.readLine();
					System.out.println(data);
					if(data == null) {
						log(" closed by client");
						break;
					}
					log("received : "+ data);
					
					// 6. 데이터 쓰기
					pw.println(data);
				}
			} catch (SocketException e) {
				log("abnormal closed by client");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// 7. 자원 정리
					if(socket != null && socket.isClosed() == false) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String s) {
		System.out.println("[server] "+s);
	}
}
