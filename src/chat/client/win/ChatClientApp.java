package chat.client.win;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 8000;
	
	public static void main(String[] args) {
		String nickname = null;
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
		
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
			
			while( true ) {
				
				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				nickname = sc.nextLine();
				
				if (nickname.isEmpty() == false ) {
					break;
				}
				
				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}
			
			pw.println("join:"+nickname);
			
			ChatWindow cw = new ChatWindow(nickname, pw);
			
			new chat.client.win.ChatClientThread(br, cw).start();
			cw.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}