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
	private final int port = 10010;	//�̶��������˿�
	private InetAddress toIp;	//Ŀ��ip
	private TargetDataLine line;	//�ܵ�
	private byte[] data;	//���ÿ�δ���˷��õ�����
	private boolean flag=true;
	// ��ʽ  
	/** 
	* ���������ƣ�pcm��mu-law�����a-law���� 
	* �ŵ�������������һ���ŵ����������������ŵ� 
	* �������ʣ�����ÿ�ŵ���ÿ���Ӳ��õ���ѹ�������������������Ƕ��٣����ʶ�һ���� 
	* ������С��ָʾ���ڴ洢ÿ�����յ�λ��������ֵ��8��16������16λ�������ֽ�˳�����Ҫ 
	* ÿ�������е��ֽڻ�����little-endian������big-endian��ʽ���С� 
	* ����PCM���룬֡�����ڸ���ʱ�������������������������ɣ����֡�Ĵ�С���ǵ���������С��һ�������� 
	*  
	* �������룬ÿ�벥�Ż���¼�Ƶ������������������е�λ������Ƶ�ŵ�����ÿ�벥�Ż���¼�Ƶ�֡�� �� 
	* �� big-endian ˳���� little-endian ˳��洢��Ƶ���� 
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
		System.out.println("���������̴߳򿪣�ռ�ö˿ں�:10010");
		
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
	
	//ʹ��UDPЭ�鴫������
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
	//ֹͣ�̵߳ķ���
	public void stopThread() {
		flag = false;
	}
}
