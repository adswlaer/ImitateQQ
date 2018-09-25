package Common;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Prompt {
	//消息提示音
	private final File msg = new File("voice/message.wav");
	//上线提示音
	private  File online = new File("voice/online.wav");
	private  AudioInputStream audioInputStream = null; 
	private  AudioFormat audioFormat = null; 
	private  SourceDataLine sourceDataLine = null; 
	private  DataLine.Info dataLine_info = null; 
	
	public synchronized void msgVoice() {
		//FileInputStream fis = new FileInputStream(msg); 
		try {
			audioInputStream = AudioSystem.getAudioInputStream(msg);
			audioFormat = audioInputStream.getFormat();
			dataLine_info = new DataLine.Info(SourceDataLine.class,audioFormat); 
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLine_info); 
			play();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void onlineVoice() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(online);
			audioFormat = audioInputStream.getFormat();
			dataLine_info = new DataLine.Info(SourceDataLine.class,audioFormat); 
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLine_info); 
			play();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void play() throws IOException, LineUnavailableException{ 
		byte[] b = new byte[1024]; 
		int len = 0; 
		sourceDataLine.open(audioFormat, 1024); 
		sourceDataLine.start(); 
		while ((len = audioInputStream.read(b)) > 0){ 
		sourceDataLine.write(b, 0, len); 
		} 
		audioInputStream.close(); 
		sourceDataLine.drain(); 
		sourceDataLine.close(); 
	} 
}
