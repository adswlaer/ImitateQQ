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

	private String ip;	//服务器ip
	private int port;	//服务器端口
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
			
			System.out.println("连接服务器成功!");
			
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			
			//为客户端添加监听数据来的线程
			Thread clientThread = new Thread(new clientListener(socket));
			clientThread.start();
					
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("服务器设置错误！");
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

