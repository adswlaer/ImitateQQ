package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import socketClient.VoiceReceiveThread;
import socketClient.VoiceSendThread;

public class VoiceFrame extends JFrame{
	    private JPanel topPal,cenPal,butPal;
	    private JLabel jlbNick,jlbTime;
	    private JButton btnCancel;
	    private String ip,nick;
	    private VoiceSendThread sendThread;
	    private VoiceReceiveThread recThread;
	    private ExecutorService executorVoice = Executors.newCachedThreadPool();
	    private boolean flag = true;
	    
	    public VoiceFrame(String ip,String nick) {
	    	this.ip = ip;
	    	this.nick = nick;
	    	
	    	JOptionPane.showMessageDialog(null, "本功能建议使用耳机,外放存在杂音！");
	    	
	    	init();
	    	
	    	sendThread = new VoiceSendThread(ip);
	    	recThread = new VoiceReceiveThread();
	    	
	    	executorVoice.execute(sendThread);
	    	executorVoice.execute(recThread);
	    	new timeThread().start();
	    	
	    }
	    
	    private void init() {
	    	topPal = new JPanel();
	    	topPal.setPreferredSize(new Dimension(200,50));
	    	topPal.setLayout(new FlowLayout(FlowLayout.LEFT));
	    	
	    	jlbNick = new JLabel("你正和"+nick+"进行语音聊天");
	    	
	    	topPal.add(jlbNick);
	    	
	    	cenPal = new JPanel();
	    	cenPal.setPreferredSize(new Dimension(200,60));
	    	cenPal.setBorder(BorderFactory.createEtchedBorder());
	    	jlbTime = new JLabel("时长:");
	    	
	    	cenPal.add(jlbTime);
	    	
	    	butPal = new JPanel();
	    	butPal.setPreferredSize(new Dimension(200,50));
	    	
	    	btnCancel = new JButton(" 取消");
	    	ButtonListener btnListener = new ButtonListener();
	    	btnCancel.addActionListener(btnListener);
	    	
	    	butPal.add(btnCancel);
	    	
	    	this.setLayout(new BorderLayout());
	    	this.add(topPal, BorderLayout.NORTH);
	    	this.add(cenPal, BorderLayout.CENTER);
	    	this.add(butPal, BorderLayout.SOUTH);
	    	
	    	this.setTitle("语音聊天");
	    	this.setSize(200,180);
	    	this.setVisible(true);
	    	this.setResizable(false);
	    	
	    	this.addWindowListener(new WindowAdapter() {
	    		public void windowsClosing(WindowEvent e) {
	    			Exit();
	    		}
	    	});
	    }
	    
	    private class timeThread extends Thread{
	    	int total = 0;
	    	public void run() {
	    		while(flag) {
	    			int hour = 0;
	    	    	int min = 0;
	    	    	int second = 0;
	    			total += 1;
	    			if(total>3600) {
	    				hour = total/3600;
	    				min = (total-hour*3600)/60;
	    				second = total-3600*hour-60*min;
	    			}
	    			else if(total<60) {
	    				second = total;
	    			}
	    			else {
	    				min = total/60;
	    				second = total-60*min;
	    			}
	    			jlbTime.setText("时长："+hour+":"+min+":"+second);
	    			try {
						this.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    	}
	    }
	    
	    public void Exit() {
	    	sendThread.stopThread();
			recThread.stopThread();
			flag = false;
			executorVoice.shutdown();
			this.dispose();
	    }
	    
	    //按钮监听器
	    private class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				Exit();
			}
		}
}
