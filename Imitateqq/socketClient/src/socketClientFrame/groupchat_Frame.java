package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Common.Protocol;
import socketClient.ChatRecord;
import socketClient.Client;
import socketClient.clientListener;

public class groupchat_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel message_Pal, tool_Pal,input_Pal;
	private JButton send_Btn;
	private JTextArea input_Txt, message_Txt;
	private JScrollPane message_Scroll, input_Scroll;
	private String nick = null;
	private Protocol sendProtocol;
	private ButtonListener btnListener = new ButtonListener();
	private BackGroundPanel chatBgpl;
	ChatRecord cr = new ChatRecord();
	
	public groupchat_Frame(String nick) {
		this.nick = nick;
		init();
	}
	
	public void init() {
		this.setTitle("ÈºÁÄ");
		this.setLayout(new BorderLayout());
		
		chatBgpl = new BackGroundPanel("image/friendsList_cen_bg.jpg");
		chatBgpl.setLayout(new BorderLayout());
		
		initMessage_Pal();
		initTool_Pal();
		initInput_Pal();
		
		chatBgpl.add(message_Pal, BorderLayout.NORTH);
		chatBgpl.add(tool_Pal, BorderLayout.CENTER);
		chatBgpl.add(input_Pal, BorderLayout.SOUTH);
		this.add(chatBgpl);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				groupchat_Frame.this.setVisible(false);
				System.out.println("JTxtArea");
				System.out.println(message_Txt.getText());
				System.out.println("File");
				cr.addChatRecord("group",message_Txt.getText());
			}
		});
		this.setSize(getPreferredSize());
		this.setLocation(500,150);
		this.setVisible(true);
		this.setResizable(false);
	}

	private void initMessage_Pal() {
		// TODO Auto-generated method stub
		message_Pal = new JPanel();
		message_Pal.setPreferredSize(new Dimension(450,250));
		message_Txt = new JTextArea(13,38);
		message_Txt.setText(cr.getChatRecord("group"));
		message_Txt.setEditable(false);
		message_Txt.setLineWrap(true);
						
		message_Scroll = new JScrollPane(message_Txt);
						
		message_Pal.add(message_Scroll);
		message_Pal.setOpaque(false);
	}



	private void initTool_Pal() {
		// TODO Auto-generated method stub
		tool_Pal = new JPanel();
		tool_Pal.setPreferredSize(new Dimension(450,80));
		tool_Pal.setBorder(BorderFactory.createEtchedBorder());
		tool_Pal.setOpaque(false);
	}
	
	private void initInput_Pal() {
		// TODO Auto-generated method stub
		input_Pal = new JPanel();
		input_Pal.setPreferredSize(new Dimension(450,100));
				
		input_Txt = new JTextArea(5,30);
		input_Txt.setLineWrap(true);		
		input_Scroll = new JScrollPane(input_Txt);
				
		send_Btn = new JButton("·¢ËÍ");
		send_Btn.setPreferredSize(new Dimension(75,90));
		
		send_Btn.addActionListener(btnListener);		
		input_Pal.add(input_Scroll);
		input_Pal.add(send_Btn);
		input_Pal.setOpaque(false);
	}
	
	public void appendTxt(String nick, String info) {
		// TODO Auto-generated method stub
		message_Txt.append(nick+"  ");
		message_Txt.append(new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(new Date()));
		message_Txt.append("\n");
		message_Txt.append(info+"\n");
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == send_Btn) {
				String order = Protocol.MSG_GROUP;
				String from = clientListener.getAccount();
				String to = Protocol.ALL;
				String info = nick + Protocol.SEPERATOR +input_Txt.getText();
				sendProtocol = new Protocol(order,from,to,info);
				
				Client.sendToServer(sendProtocol.getProtocolStr());
				appendTxt("ÎÒ", input_Txt.getText());
				input_Txt.setText(null);
				
			}
		}
	}
	
}
