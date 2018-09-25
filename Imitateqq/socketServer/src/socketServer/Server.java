package socketServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.DataLine;

import Common.Protocol;
import Common.ShowMessage;
import Common.UserInfo;

import socketServerFrame.ServerMainFrame;


public class Server implements Runnable{

		//服务器端口号
		private int port;
		//创建服务端Socket,即ServerSocket
		private ServerSocket serverSocket;
		//动态线程池
		private ExecutorService executorService = Executors.newCachedThreadPool();
		//管理界面
		private static  ServerMainFrame smf;
		
		// 用于维护登录信息的Map <username,userinfo>
		private static Map<String, UserInfo> clientsMap = new ConcurrentHashMap<>();
		private boolean flag = true;
		
		public Server(int port,ServerMainFrame smf)
		{
			this.port = port;
			this.smf = smf;
			
			//executorService.execute(new updateClient());
		}		
		
		public void run()
		{
			try {
				//创建服务器端Socket,指定绑定的端口,并监听此端口
				serverSocket = new ServerSocket(port);
				Socket socket = null;
				//启动信息
				smf.updateLog(new Date().toString()+"\n"+"服务器启动!");
				
				//循环监听等待客户端的连接
				while (flag) {
					//调用accept()方法开始监听，等待客户端的连接
					socket = serverSocket.accept();
					
					//创建相对应的socket线程并加入线程池
					executorService.execute(new ServerListener(socket,smf,clientsMap));
					
					
				}
				}catch(BindException e) {
					e.printStackTrace();
					System.out.println("该端口被占用，请尝试别的端口！");
					ShowMessage.portBusy();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		
		public static void audio(String info) {
			Protocol sendProtocol = new Protocol(Protocol.AUDIO,Protocol.SERVER,Protocol.ALL,info);
			//遍历客户端map
			for(UserInfo userInfo:clientsMap.values()) {
				try {
					//获得当前客户端的输出流
					PrintWriter pw = new PrintWriter(userInfo.getSocket().getOutputStream());
					//输出广播协议
					pw.println(sendProtocol.getProtocolStr());
					pw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			smf.updateLog(sendProtocol.toString());
		}
		
		public void openService(){
			executorService.execute(this);
		}
		
		public void stopService() {
			flag = false;
			executorService.shutdown();
		}
		/*
		//更新在线客户端的线程
		class updateClient implements Runnable{
			public void run() {
				while(true) {
				smf.updateTable(clientsMap);
				smf.updateOnlineCount(clientsMap.size());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
		*/
}
		

