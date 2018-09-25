package socketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private String ip;	//������ip
	private int port;	//�������˿�
	private Socket socket;
	private OutputStream os;
	private static PrintWriter pw;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	
	public Client(String ip,int port) {
		this.ip = ip;
		this.port = port;
		
		try {			
			socket = new Socket(ip,port);
			
			System.out.println("���ӷ������ɹ�!");
			
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			
			//Ϊ�ͻ�����Ӽ������������߳�
			Thread clientThread = new Thread(new clientListener(socket));
			clientThread.start();
					
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("���������ô���");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendToServer(String sendProtocol) {
			pw.println(sendProtocol);
			pw.flush();
	}
	
	
}

