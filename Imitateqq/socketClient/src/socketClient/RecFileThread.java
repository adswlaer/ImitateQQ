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

	//�̶��Ĵ����ļ��Ķ˿�
	private final int port = 10086;
	//�����ļ���socket
	private Socket socket;
	//Ŀ�ĵ�ip
	private String ip;
	//�Է��������ļ���
	private String fileName;
	//�Է��������ļ�����
	private long fileSize;
	//�ܹ��յ����ļ�����
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
			//�����ô��ļ���׼�����ٴ�socket
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
			
			int size = 0;	//ÿ�δ������ļ�����
			
			byte[] buffer = new byte[4096*5];
			while(count<fileSize&&size!=-1&&flag) {
				 size = is.read(buffer);
				 
				 fos.write(buffer, 0, size);
				 fos.flush();
				 
				 count+=size;

				 System.out.println("�������ݰ�����СΪ��"+size);
				 
				 fileFrame.jlbPercent.setText("�ѽ���:"+(((count+0.0)/fileSize)*100)+"%");
				 
				 fileLog.setBreakPoint(count);
				 if(size==0)
					 size=-1;
			 }
			
			System.out.println("д����ɣ�");
			
			fos.close();
		    
			
			if(count>=fileSize)
				fileFrame.jlbPercent.setText("�����ļ�����!");
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
