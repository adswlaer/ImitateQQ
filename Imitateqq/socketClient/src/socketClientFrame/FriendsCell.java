package socketClientFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FriendsCell extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private String account,nick;
	private int status;
	private JPanel panel1,panel2;
	private JLabel jlbHeadIcon;
	private JLabel jlbAccount,jlbNick;
	private boolean flag=true;
	public PersonalChat_Frame chatFrame;
	private ImageIcon headIcon = new ImageIcon("image/head.png");
	private ImageIcon whiteIcon = new ImageIcon("image/white.jpg");
	private ImageIcon quitIcon = new ImageIcon("image/quit.png");
	private int flashStatus = 0;
	private FlashListener flashListener = new FlashListener();
    private Timer timer = new Timer(200,flashListener);
	
    private MouseAdapter mouseListener = new MouseAdapter() {
    	//离开面板
		public void mouseExited(MouseEvent e) {
					exchangeExited();
		}
		//进入面板
		public void mouseEntered(MouseEvent e) {
					exchangeEnter();
		}
		//双击事件
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				stopFlashing();
				setStatus(status);
				if(chatFrame==null) 
					chatFrame = new PersonalChat_Frame();
				
				else
					chatFrame.setVisible(true);
				chatFrame.setLocation(500,130);
							
			}
		}
    };
    
	public FriendsCell(String account,String nick,int status,PersonalChat_Frame chatFrame) {
		this.account = account;
		this.nick = nick;
		this.status = status;
		this.chatFrame = chatFrame;
		init();
		
	}
	
	private void init() {
		
		this.setPreferredSize(new Dimension(210,65));
		headIcon.setImage(headIcon.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT)); 
		whiteIcon.setImage(whiteIcon.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT));
		quitIcon.setImage(quitIcon.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT));
		
		panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(55,60));
		
		jlbHeadIcon = new JLabel();
		jlbHeadIcon.setSize(50,50);
		setStatus(status);
		
		panel1.add(jlbHeadIcon);
		//鼠标动作，移进鼠标
		panel1.addMouseListener(mouseListener);
		
		
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(120,60));
		panel2.setLayout(new GridLayout(2,1));
		
		jlbAccount = new JLabel(account);
		jlbNick = new JLabel(nick);
		
		panel2.add(jlbAccount);
		panel2.add(jlbNick);
		
		//鼠标动作，移开鼠标，移进鼠标
		panel2.addMouseListener(mouseListener);
		
		panel1.setBackground(null);
		panel2.setBackground(null);
		this.setBackground(null);
		
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		this.setOpaque(false);
		//this.setSize(200,60);
		this.add(panel1);
		this.add(panel2);
		
		//鼠标动作，移开鼠标，移进鼠标
		this.addMouseListener(mouseListener);
	}
	
	private void exchangeEnter() {
		 this.setBackground(new Color(192,224,248));
		 panel1.setBackground(new Color(192,224,248));
		 panel2.setBackground(new Color(192,224,248));
		 
		 panel1.setOpaque(true);
		 panel2.setOpaque(true);
		 this.setOpaque(true);
	}

	private void exchangeExited() {
		 this.setBackground(null);
		 panel1.setBackground(null);
		 panel2.setBackground(null);
		 
		 panel1.setOpaque(false);
		 panel2.setOpaque(false);
		 this.setOpaque(false);
	}
	
	public void setNick(String nick) {
		jlbNick.setText(nick);
	}
	 
	public void setStatus(int status) {
		this.status = status;
		if(status==0)
			jlbHeadIcon.setIcon(quitIcon);
		else
			jlbHeadIcon.setIcon(headIcon);
	}
	
	public void HeadFlashing() {
		 flag = true;
		 timer.start();
	}
	
	public void stopFlashing() {
		flag = false;
		timer.stop();
	}
	
	public PersonalChat_Frame getChatFrame() {
		return chatFrame;
	}
	
	//头像闪动动作
	 private class FlashListener implements ActionListener {
	    	public void actionPerformed(ActionEvent e){
	    		if(flag) {
	    			if(flashStatus==0) {
	    				jlbHeadIcon.setIcon(whiteIcon);
	    				flashStatus = 1;
	    			}
	    			else {
	    				setStatus(status);
	    				flashStatus = 0;
	    			}
	    		}
	    	}
	 }
    
    
}
