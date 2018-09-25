package socketClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class VoiceSendThread implements Runnable{
	private final int port = 10010;	//固定的语音端口
	private InetAddress toIp;	//目的ip
	private TargetDataLine line;	//管道
	private byte[] data;	//存放每次从麦克风获得的数据
	private boolean flag=true;
	// 格式  
	/** 
	* 脉冲编码调制：pcm、mu-law编码和a-law编码 
	* 信道数：单声道有一个信道，立体声有两个信道 
	* 样本速率：测量每信道、每秒钟采用的声压快照数，不管声道数是多少，速率都一样。 
	* 样本大小：指示用于存储每个快照的位数，典型值是8和16，对于16位样本，字节顺序很重要 
	* 每个样本中的字节或者以little-endian或者以big-endian样式排列。 
	* 对于PCM编码，帧是由在给定时间点上所有声道的样本集合组成，因此帧的大小总是等于样本大小成一声道数。 
	*  
	* 声道编码，每秒播放或者录制的样本数，声音样本中的位数，音频信道数，每秒播放或者录制的帧数 ， 
	* 以 big-endian 顺序还是 little-endian 顺序存储音频数据 
	* */  
	
	private AudioFormat format = new AudioFormat(  
		    AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);  
	
	public VoiceSendThread(String ip) {
		try {
			this.toIp = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		System.out.println("语音发送线程打开，占用端口号:10010");
		
		try {
			line.open(format,line.getBufferSize());
			line.start();
			
			int length = (int)(format.getFrameSize()*format.getFrameRate()/2.0f);
			
			while(flag) {
				data = new byte[length];
				line.read(data, 0, data.length);
				sendData();
			}
			line.close();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//使用UDP协议传输声音
	private void sendData() {
		try {
			DatagramPacket dp = new DatagramPacket(data,data.length,toIp,port);
			DatagramSocket ds = new DatagramSocket();
			
			ds.send(dp);
			ds.close();
			
			System.out.println("Succeed Sending,Size:"+data.length);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//停止线程的方法
	public void stopThread() {
		flag = false;
	}
}
