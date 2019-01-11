package udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPEchoClient {
	public static final String SERVER_IP = "192.168.56.1";
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		Scanner sc = null;
		DatagramSocket socket = null;
		
		try {
			//1. 키보드 연결
			sc = new Scanner(System.in);
			
			//2. socket 생성
		
			socket = new DatagramSocket();
			
			while(true) {
				//3. 사용자 입력 받음
				System.out.print(">> ");
				String message = sc.nextLine();
				
				if("quit".equals(message)) {
					break;
				}
				
				//4. 메세지 전송
				byte[] data = message.getBytes("UTF-8");
				DatagramPacket sendPacket = 
						new DatagramPacket(data, data.length, new InetSocketAddress(SERVER_IP, PORT));
				socket.send(sendPacket);
				
				//5. 메세지 수신
				DatagramPacket receivePacket = 
						new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(), 0, receivePacket.getLength(),"UTF-8");
				System.out.println("<< " + message);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//자원정리
			if(sc != null) {
				sc.close();
			}
			if(socket != null && socket.isClosed()==false) {
				socket.close();
			}
		}
	}
}
