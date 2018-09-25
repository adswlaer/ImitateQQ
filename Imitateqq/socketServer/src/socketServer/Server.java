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

		//�������˿ں�
		private int port;
		//���������Socket,��ServerSocket
		private ServerSocket serverSocket;
		//��̬�̳߳�
		private ExecutorService executorService = Executors.newCachedThreadPool();
		//�������
		private static  ServerMainFrame smf;
		
		// ����ά����¼��Ϣ��Map <username,userinfo>
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
				//������������Socket,ָ���󶨵Ķ˿�,�������˶˿�
				serverSocket = new ServerSocket(port);
				Socket socket = null;
				//������Ϣ
				smf.updateLog(new Date().toString()+"\n"+"����������!");
				
				//ѭ�������ȴ��ͻ��˵�����
				while (flag) {
					//����accept()������ʼ�������ȴ��ͻ��˵�����
					socket = serverSocket.accept();
					
					//�������Ӧ��socket�̲߳������̳߳�
					executorService.execute(new ServerListener(socket,smf,clientsMap));
					
					
				}
				}catch(BindException e) {
					e.printStackTrace();
					System.out.println("�ö˿ڱ�ռ�ã��볢�Ա�Ķ˿ڣ�");
					ShowMessage.portBusy();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		
		public static void audio(String info) {
			Protocol sendProtocol = new Protocol(Protocol.AUDIO,Protocol.SERVER,Protocol.ALL,info);
			//�����ͻ���map
			for(UserInfo userInfo:clientsMap.values()) {
				try {
					//��õ�ǰ�ͻ��˵������
					PrintWriter pw = new PrintWriter(userInfo.getSocket().getOutputStream());
					//����㲥Э��
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
		//�������߿ͻ��˵��߳�
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
		

