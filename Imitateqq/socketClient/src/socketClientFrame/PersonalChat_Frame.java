package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Common.FileChooser;
import Common.FileOperation;
import Common.Protocol;
import socketClient.ChatRecord;
import socketClient.Client;
import socketClient.SendFileThread;
import socketClient.clientListener;

public class PersonalChat_Frame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel message_Pal, tool_Pal, input_Pal;
	private JButton send_Btn,file_Btn,voice_Btn;
	private JTextArea input_Txt, message_Txt;
	private JScrollPane message_Scroll, input_Scroll;
	private String from_Nick,to_Nick=null;
	private String account,nick;
	private int status;
	private Protocol sendProtocol;
	private File file;
	private FileInputStream fis;
	private ButtonListener btnListener = new ButtonListener();
	private ChatRecord cr = new ChatRecord();
	private BackGroundPanel chatBgpl;
	
	
	public PersonalChat_Frame(String account,String nick,int status){
		this.account = account;
		this.nick = nick;
		this.status = status;
		init();
	}
	
	public PersonalChat_Frame() {
		init();
	}
	
	private void init(){
		this.setTitle("与"+ this.nick +"的会话");
		
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
				PersonalChat_Frame.this.setVisible(false);
				System.out.println("JTxtArea");
				System.out.println(message_Txt.getText());
				System.out.println("File");
				cr.addChatRecord(account,message_Txt.getText());
			}
		});
		this.setSize(getPreferredSize());
		this.setVisible(true);
		this.setResizable(false);   
		this.setLocation(520,170);
	}

	private void initMessage_Pal() {
		// TODO Auto-generated method stub
		//聊天消息框
		message_Pal = new JPanel();
		message_Pal.setPreferredSize(new Dimension(450,250));
		message_Pal.setLayout(new FlowLayout(FlowLayout.LEFT));
		message_Txt = new JTextArea(13,31);
		message_Txt.setText(cr.getChatRecord(account));
		message_Txt.setEditable(false);
		message_Txt.setLineWrap(true);
						
		message_Scroll = new JScrollPane(message_Txt);
						
		message_Pal.add(message_Scroll);
		message_Pal.setOpaque(false);
		
	}
	
	private void initTool_Pal() {
		// TODO Auto-generated method stub
		//工具栏
		tool_Pal = new JPanel();
		tool_Pal.setPreferredSize(new Dimension(450,80));
		tool_Pal.setBorder(BorderFactory.createEtchedBorder());
		
		file_Btn = new JButton("发送文件");
		voice_Btn = new JButton("语音聊天");
		
		file_Btn.addActionListener(btnListener);
		voice_Btn.addActionListener(btnListener);
		
		tool_Pal.add(file_Btn);
		tool_Pal.add(voice_Btn);
		tool_Pal.setOpaque(false);
		
	}

	private void initInput_Pal() {
		// TODO Auto-generated method stub
		//聊天输入框
		input_Pal = new JPanel();
		input_Pal.setPreferredSize(new Dimension(450,100));
				
		input_Txt = new JTextArea(5,30);
		input_Txt.setLineWrap(true);
				
		input_Scroll = new JScrollPane(input_Txt);
				
		send_Btn = new JButton("发送");
		send_Btn.setPreferredSize(new Dimension(75,90));
		send_Btn.addActionListener(btnListener);		
		input_Pal.add(input_Scroll);
		input_Pal.add(send_Btn);
		input_Pal.setOpaque(false);
	}

	public void appendTxt(String nick,String info) {
		message_Txt.append(nick+"  ");
		message_Txt.append(new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(new Date()));
		message_Txt.append("\n");
		message_Txt.append(info+"\n");
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == send_Btn) {
				String order = Protocol.MSG_PRIVATE;
				String from = clientListener.getAccount();
				String to = account;
				String info = input_Txt.getText();
				
				sendProtocol = new Protocol(order,from,to,info);
				Client.sendToServer(sendProtocol.getProtocolStr());
				input_Txt.setText(null);
				
				if (info!=null) {
					appendTxt("我", info);
				}
			} 
			else if(e.getSource() == file_Btn) {
				
				try {
					file = FileChooser.openFile();
					fis = new FileInputStream(file);
					sendProtocol = new Protocol(Protocol.FILE,clientListener.getAccount(),account,
							file.getName()+Protocol.SEPERATOR+fis.available());
					Client.sendToServer(sendProtocol.getProtocolStr());
					clientListener.setFileSize(Long.toString(fis.available()));
					clientListener.setFile(file);
					fis.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else {
				sendProtocol = new Protocol(Protocol.VOICE,clientListener.getAccount(),
						account,null);
				Client.sendToServer(sendProtocol.getProtocolStr());
			}
		}
		
	}
	
}
