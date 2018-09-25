package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Common.Protocol;
import socketClient.Client;
import socketClient.clientListener;

public class Search_Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel input_Pal;

	private static JPanel message_Pal;

	private JPanel btn_Pal;
	private JButton search_Btn, cancel_Btn;
	private JTextField userName_Txt;
	private static JLabel info_Lab;
	private Protocol sendProtocol;
	private static JButton add_Btn;
	private JPanel jplMsg;
	private JLabel jlbAccount,jlbNick,jlbStatus;
	private JPanel jplBtn;
	private BackGroundPanel sfBg_Pal;
	public Search_Frame() {
		init();
	}
	
	private void init() {
		this.setTitle("查找好友");
		this.setLayout(new BorderLayout());
		sfBg_Pal = new BackGroundPanel("image/Register_bg.jpg");
		sfBg_Pal.setLayout(new BorderLayout());
		
		
		input_Pal = new JPanel();
		input_Pal.setPreferredSize(new Dimension(250,60));
		input_Pal.setBorder(BorderFactory.createTitledBorder("输入要查找的用户名："));
		input_Pal.setLayout(new FlowLayout());
		input_Pal.setOpaque(false);
		
		userName_Txt = new JTextField(10);
		search_Btn = new JButton("查找");
		search_Btn.addActionListener(new ButtonListener());
		input_Pal.add(userName_Txt);
		input_Pal.add(search_Btn);
		
		message_Pal = new JPanel();
		message_Pal.setPreferredSize(new Dimension(250,120));
		message_Pal.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		message_Pal.setOpaque(false);
		
		btn_Pal = new JPanel();
		btn_Pal.setPreferredSize(new Dimension(250,40));
		btn_Pal.setOpaque(false);
		
		cancel_Btn = new JButton("关闭");
		cancel_Btn.addActionListener(new ButtonListener());
		
		btn_Pal.add(cancel_Btn);
		
		sfBg_Pal.add(input_Pal,BorderLayout.NORTH);
		sfBg_Pal.add(message_Pal,BorderLayout.CENTER);
		sfBg_Pal.add(btn_Pal,BorderLayout.SOUTH);
		
		this.add(sfBg_Pal);
		this.setLocation(575,300);
		this.setSize(250,240);
		this.setResizable(false);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
    		public void windowsClosing(WindowEvent e) {
    			closeFrame();
    		}
    	});
	}
	
	
	private void closeFrame() {
		this.dispose();
	}
	
	public void updateUI(String info) {
		this.remove(message_Pal);
		
		message_Pal = new JPanel();
		message_Pal.setPreferredSize(new Dimension(250,120));
		message_Pal.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		message_Pal.setLayout(new BorderLayout());
		
		jplMsg = new JPanel();
		jplMsg.setPreferredSize(new Dimension(120,110));
		jplMsg.setLayout(new BoxLayout(jplMsg,BoxLayout.Y_AXIS));
		
		String[] str = info.split("\\"+Protocol.SEPERATOR);
		
		jlbAccount = new JLabel("用户名:"+str[0]);
		jlbNick = new JLabel("昵称:"+str[1]);
		
		JLabel jlbStatus;
		if(str[2].equals("1"))
			jlbStatus = new JLabel("状态:在线");
		else
			jlbStatus = new JLabel("状态:离线");
		
		jplMsg.add(jlbAccount);
		jplMsg.add(jlbNick);
		jplMsg.add(jlbStatus);
		//jplMsg.setOpaque(false);
		
		message_Pal.add(jplMsg,BorderLayout.CENTER);
		message_Pal.setOpaque(false);
		jplBtn = new JPanel();
		jplBtn.setPreferredSize(new Dimension(120,110));
		//jplBtn.setOpaque(false);
		if (Integer.parseInt(str[2]) == 1) {
			add_Btn = new JButton("添加好友");
			add_Btn.setSize(35,35);
			//message_Pal.setLayout(new FlowLayout());
			add_Btn.addActionListener(new ButtonListener());
			jplBtn.add(add_Btn);
			message_Pal.add(jplBtn,BorderLayout.EAST);
		}
		
		sfBg_Pal.add(message_Pal,BorderLayout.CENTER);
		//this.add(message_Pal,BorderLayout.CENTER);
		
		this.validate();
	}
	
	private class ButtonListener implements ActionListener{
		private String search_Name = null;
		private String order = null;
		private String from = null;
		private String to = null;
		private String info = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			search_Name = userName_Txt.getText();
			if (e.getSource() == cancel_Btn) {
				closeFrame();
			}
			else if (e.getSource() == search_Btn) {
				info = search_Name;
				
				order = Protocol.SEARCH_FRIEND;
				from = clientListener.getAccount();
				to = Protocol.SERVER;
				sendProtocol = new Protocol(order, from, to, info);
				Client.sendToServer(sendProtocol.protocolStr);
			
			}
			else if (e.getSource() == add_Btn) {
				from = clientListener.getAccount();
				to = search_Name;
				
				if(from.equals(to)) {
					JOptionPane.showMessageDialog(null, "不能添加自己为好友!");
					return;
				}
				
				if(clientListener.friendsMap.containsKey(to)) {
					JOptionPane.showMessageDialog(null, "该用户已是你的好友！");
				}
				else {
					order = Protocol.ADD_FRIEND;
					info = from + Protocol.SEPERATOR +clientListener.getNick();
					sendProtocol = new Protocol(order, from, to, info);
					Client.sendToServer(sendProtocol.protocolStr);
				}
			}
		}
		
	}
	
}
