package socketClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import socketClientFrame.FileFrame;


public class SendFileThread implements Runnable{
	
	//�̶��Ĵ����ļ��Ķ˿�
	private final int port = 10086;
	//���������Socket,��ServerSocket
	private ServerSocket serverSocket;
	//������ļ�
	private File file;
	//������ļ�Ŀǰ���͵��ܴ�С
	private long count=0;
	private FileFrame fileFrame;
	private boolean flag = true;
	private int per = 0;
	private long fileSize = 0;
	private OutputStream os;
	private long position;
	public SendFileThread(File file,FileFrame fileFrame,long position) {
		this.file = file;
		this.fileFrame = fileFrame;
		this.position = position;
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			Socket socket = null;
			
			
			socket = serverSocket.accept();
				
			os = socket.getOutputStream();
				
			FileInputStream fis = new FileInputStream(file);
				
			int size = 0;
			fileSize = fis.available();
			//����positionλ�ú�ʼ����
			fis.skip(position);
			count+=position;
			byte[] buffer = new byte[4096*5];
			while((size = fis.read(buffer)) != -1&&flag) {
				os.write(buffer, 0, size);
				os.flush();
				System.out.println("�������ݰ�����СΪ��"+size);
				count += size;
				
				fileFrame.jlbPercent.setText("�ѷ���:"+(((count+0.0)/fileSize)*100)+"%");
			}
			
			fis.close();
			
			fileFrame.jlbPercent.setText("�����ļ�����!");
			
			flag = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				os.close();
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void stopThread() {
		flag = false;
		try {
			os.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
