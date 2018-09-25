package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Common.FileLog;
import socketClient.RecFileThread;
import socketClient.SendFileThread;


public class FileFrame extends JFrame{
	
	private JPanel jplName,jplSize,jplPercent;
	public JLabel jlbName,jlbSize,jlbPercent;
	private String fileName,fileSize,ip;
	private File file;
	private SendFileThread sendThread;
	private RecFileThread recThread;
	private ExecutorService executorFile = Executors.newCachedThreadPool();
	private FileFrame fileFrame;
	//文件的大小
	private long size;
	private long position;
	//发送端
	public FileFrame(String fileName,String fileSize,File file,long position) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.file = file;
		fileFrame = this;
		size = Long.parseLong(fileSize);
		this.position = position;
		
		init();
		
		this.addWindowListener(new WindowAdapter() {
    		public void windowsClosing(WindowEvent e) {
    			sendThread.stopThread();
    			executorFile.shutdownNow();
    			fileFrame.dispose();
    			//System.exit(0);
    		}
    	});
		
		sendThread = new SendFileThread(file,this,position);
		//打开发送文件的线程
		executorFile.execute(sendThread);
	}
	
	//接收端
	public FileFrame(String fileName,String fileSize,String ip,FileLog fileLog) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.ip = ip;
		fileFrame = this;
		init();
		
		this.addWindowListener(new WindowAdapter() {
    		public void windowsClosing(WindowEvent e) {
    			recThread.stopThread();
    			executorFile.shutdown();
    			fileFrame.dispose();
    			
    		}
    	});
		
		recThread = new RecFileThread(ip,fileName,fileSize,this,fileLog);
		//打开发送文件的线程
		executorFile.execute(recThread);
	}
	
	//初始化界面
	public void init() {
		jplName = new JPanel();
		jplName.setLayout(new FlowLayout(FlowLayout.LEFT));
		jplName.setSize(new Dimension(250,100));
		
		jlbName = new JLabel("FileName:"+fileName);
		
		jplName.add(jlbName);
		
		jplSize = new JPanel();
		jplSize.setLayout(new FlowLayout(FlowLayout.LEFT));
		jplSize.setSize(new Dimension(250,30));
		jplSize.setBorder(BorderFactory.createEtchedBorder());
		
		jlbSize = new JLabel("Size:"+fileSize);
		
		jplSize.add(jlbSize);
		
		jplPercent = new JPanel();
		jplPercent.setSize(new Dimension(250,30));
		
		jlbPercent = new JLabel("Percent:"+0+"%");
		
		jplPercent.add(jlbPercent);
		
		this.setTitle("传输文件");
		this.setSize(250, 160);
		this.setLayout(new GridLayout(3,1));
		this.add(jplName);
		this.add(jplSize);
		this.add(jplPercent);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
}
