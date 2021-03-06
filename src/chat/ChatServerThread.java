package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerThread extends Thread{
	private Socket socket;
	private String nickname;
	private List<Writer> listWriters;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = 
				(InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remoteHostPort = inetRemoteSocketAddress.getPort();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
			
			while(true) {
				String protocol = br.readLine();
				String[] tokens = protocol.split(":");
				if(tokens[0].equals("join")) {
					doJoin(tokens[1], pw);
				}else if(tokens[0].equals("message")) {
					if(tokens[1] != null) {
						doMessage(tokens[1], pw);
					}
				}else if(tokens[0].equals("quit")) {
					doQuit(pw);
				}else {
					pw.println("알수없는 요청입니다.".getBytes("UTF-8"));
				}
			}
			
		} catch (SocketException e) {
			log(nickname+"님의 연결 끊김");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed()==false) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void doQuit(PrintWriter pw) {
		removeWriter(pw);
		boardcast(nickname+"님이 퇴장하셨습니다.", pw);
		
		//서버
		System.out.println(nickname+"님이 퇴장하셨습니다.");
	}

	private void doMessage(String message, PrintWriter pw) {
		boardcast(nickname+" > "+message, pw);
		
		//서버
		System.out.println(nickname+" > "+message);
	}

	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		boardcast(nickname+"님이 입장하셨습니다.", (PrintWriter)writer);
		addWriter(writer);
		((PrintWriter)writer).println("즐거운 채팅되세요.");
		
		//서버
		System.out.println(nickname+"님이 입장하셨습니다.");
	}
	
	private void boardcast(String data, PrintWriter pw) {
		synchronized (listWriters) {
			for(Writer writer : listWriters) {
				if(writer != pw) {
					PrintWriter printWirter = (PrintWriter)writer;
					printWirter.println(data);
				}
			}
		}
	}
	
	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}
	
	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}
	
	private void log(String s) {
		System.out.println("[server] "+s);
	}
}
