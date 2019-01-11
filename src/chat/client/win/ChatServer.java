package chat.client.win;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();
		
		try {
			//1. create server socket
			serverSocket = new ServerSocket();

//			//1-1. set option SO_REUSEADDR
//			// (종료 후 빨리 바인딩을 하기 위해서)
//			serverSocket.setReuseAddress(true);
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localhostAddress, PORT);
			serverSocket.bind(inetSocketAddress);
			log("binding "+localhostAddress+":"+PORT);
			//bind
			
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String s) {
		System.out.println("[server] "+s);
	}
}