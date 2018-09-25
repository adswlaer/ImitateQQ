package socketClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Common.FileChooser;
import Common.FileLog;
import socketClientFrame.FileFrame;

public class RecFileThread implements Runnable{

	//固定的传输文件的端口
	private final int port = 10086;
	//传输文件的socket
	private Socket socket;
	//目的地ip
	private String ip;
	//对方传来的文件名
	private String fileName;
	//对方传来的文件长度
	private long fileSize;
	//总共收到的文件长度
	private long count = 0;	
	private boolean flag = true;
	private InputStream is;
	private FileFrame fileFrame;
	private FileLog fileLog;
	public RecFileThread(String ip,String fileName,String fileSize,FileFrame fileFrame,FileLog fileLog) {
		this.ip = ip;
		this.fileName = fileName;
		this.fileSize = Long.parseLong(fileSize);
		this.fileFrame = fileFrame;
		this.fileLog = fileLog;
	}
	
	public void run() {
		try {
			//先做好打开文件的准备，再打开socket
			File file; 
			if(fileLog!=null) {
				file = new File(fileLog.getPath());
				count = fileLog.getBreakPoint();
			}
			else {
				file = FileChooser.saveFile(fileName);
				fileLog = new FileLog(file.getName(),fileSize,file.getAbsolutePath(),0);
			}
			FileOutputStream fos = new FileOutputStream(file,true);
			
			fileFrame.jlbName.setText(file.getName());
			
			socket = new Socket(ip,port);
			is = socket.getInputStream();
			
			int size = 0;	//每次传来的文件长度
			
			byte[] buffer = new byte[4096*5];
			while(count<fileSize&&size!=-1&&flag) {
				 size = is.read(buffer);
				 
				 fos.write(buffer, 0, size);
				 fos.flush();
				 
				 count+=size;

				 System.out.println("接受数据包，大小为："+size);
				 
				 fileFrame.jlbPercent.setText("已接收:"+(((count+0.0)/fileSize)*100)+"%");
				 
				 fileLog.setBreakPoint(count);
				 if(size==0)
					 size=-1;
			 }
			
			System.out.println("写入完成！");
			
			fos.close();
		    
			
			if(count>=fileSize)
				fileFrame.jlbPercent.setText("接受文件结束!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				is.close();
				socket.close();
				clientListener.fileRecord.add(fileLog);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void stopThread() {
		flag = false;
		try {
			is.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
