package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class UDPTimeClient {
	public static final String SERVER_IP = "192.168.56.1";
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			socket = new DatagramSocket();
			
			//메세지 전송
			byte[] data = " ".getBytes();
			DatagramPacket sendPacket = 
					new DatagramPacket(data, data.length, new InetSocketAddress(SERVER_IP, UDPTimeServer.PORT));
			socket.send(sendPacket);
				
			//메세지 수신
			DatagramPacket receivePacket =
					new DatagramPacket(new byte[UDPTimeServer.BUFFER_SIZE], UDPTimeServer.BUFFER_SIZE);
			socket.receive(receivePacket);
			
			String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
