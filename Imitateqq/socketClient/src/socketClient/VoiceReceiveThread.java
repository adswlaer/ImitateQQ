package socketClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class VoiceReceiveThread implements Runnable{

	//�̶��Ľ��������Ķ˿ں�
	private final int port = 10010;
	//��ʽ
	private AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100.0f,16,1,2
			,44100.0f,false);
	//�ܵ�
	private SourceDataLine line;
	//����������
	private byte[] data;
	private boolean flag = true;
	
	public VoiceReceiveThread() {
		try {
			DataLine.Info  info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine)AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("���������̴߳�!");
		
		int length = (int)(format.getFrameSize()*format.getFrameRate()/2.0f);
		
		try {
			line.open(format);
			line.start();
			
			DatagramSocket dc = new DatagramSocket(port);
			
			while(flag) {
				data = new byte[length];
				DatagramPacket dp = new DatagramPacket(data,data.length);
				
				dc.receive(dp);
				
				line.write(data, 0, data.length);
				
				System.out.println("Receive success,size:"+data.length);
			}
			dc.close();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		line.close();
	}
	//ֹͣ�̵߳ķ���
	public void stopThread() {
		flag = false;
	}
}

