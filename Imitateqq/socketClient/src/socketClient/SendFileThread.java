package socketClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import socketClientFrame.FileFrame;


public class SendFileThread implements Runnable{
	
	//固定的传输文件的端口
	private final int port = 10086;
	//创建服务端Socket,即ServerSocket
	private ServerSocket serverSocket;
	//传输的文件
	private File file;
	//传输的文件目前发送的总大小
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
			//跳过position位置后开始发送
			fis.skip(position);
			count+=position;
			byte[] buffer = new byte[4096*5];
			while((size = fis.read(buffer)) != -1&&flag) {
				os.write(buffer, 0, size);
				os.flush();
				System.out.println("发送数据包，大小为："+size);
				count += size;
				
				fileFrame.jlbPercent.setText("已发送:"+(((count+0.0)/fileSize)*100)+"%");
			}
			
			fis.close();
			
			fileFrame.jlbPercent.setText("发送文件结束!");
			
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
