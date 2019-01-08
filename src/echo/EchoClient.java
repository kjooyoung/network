package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 5000;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
		try {
			// 1. 소켓 생성
			socket = new Socket();
			
			// 2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connected");
			
			//3. IOStream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			while(true) {
				
				//4. 데이터 쓰기
				System.out.print(">> ");
				String data = sc.nextLine();
				if(data.equals("exit")) {
					break;
				}
				pw.println(data);
				
				//5. 데이터 읽기
				String data2 = br.readLine();
				if(data2 == null) {
					log("closed by server");
				}
				System.out.println("<< "+data2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//6. 자원 정리
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
				if(sc != null) {
					sc.close();
				}
			} catch (IOException e) {
				log("error: "+e);
			}
		}
	}
	public static void log(String s) {
		System.out.println("[client] "+s);
	}
}
