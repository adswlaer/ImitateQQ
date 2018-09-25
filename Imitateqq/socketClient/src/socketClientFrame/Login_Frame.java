package socketClientFrame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Common.Protocol;
import socketClient.Client;

import javax.swing.JPasswordField;

public class Login_Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//设置面板
	private Set_Frame set_Frame = new Set_Frame();
	//图片--面板
	private JPanel img_Pal,input_Pal,btn_Pal;
	private BackGroundPanel bgjpl = new BackGroundPanel("image/login_bg.png");
	private JLabel userName_Lab,password_Lab,img_Lab;
	public static JTextField userName_Txt;
	private JButton register_Btn,login_Btn,set_Btn;;
	private JPasswordField password_Pwd;
	//private ImageIcon img = new ImageIcon("image/login_bg.png");
	private Client clientSer=null;
	private static Login_Frame lf = null;
	
	public Login_Frame() {
		init();
		lf = this;
	}
	
	private void init()
	{
		this.setTitle("QQ");
		bgjpl.setLayout(new BorderLayout());
		
		//链接服务器--面板
		img_Pal = new JPanel();
		img_Pal.setPreferredSize(new Dimension(300,100));
		//img_Pal.setLayout(new BorderLayout());
		
		//img.setImage(img.getImage().getScaledInstance(310, 110,Image.SCALE_DEFAULT)); 
		//img_Lab = new JLabel(img);
		//img_Lab.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		//img_Pal.setBorder(BorderFactory.createEtchedBorder());
		//img_Pal.add(img_Lab);
		
		//登陆信息--面板
		input_Pal = new JPanel(new GridLayout(2,2));
		input_Pal.setPreferredSize(new Dimension(250,70));
		//input_Pal.setBackground(Color.WHITE);
		input_Pal.setBorder(BorderFactory.createTitledBorder("登录QQ"));
		
		userName_Lab = new JLabel("用户名",JLabel.CENTER);
		userName_Txt = new JTextField(12);
		
		password_Lab = new JLabel("密码",JLabel.CENTER);
		password_Pwd = new JPasswordField(12);
		
		input_Pal.add(userName_Lab);
		input_Pal.add(userName_Txt);
		input_Pal.add(password_Lab);
		input_Pal.add(password_Pwd);
		
		//注册，登陆--面板
		btn_Pal = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btn_Pal.setPreferredSize(new Dimension(250,50));

		register_Btn = new JButton("注册");
		login_Btn = new JButton("登录");
		set_Btn = new JButton("设置");
		
		btn_Pal.add(set_Btn);
		btn_Pal.add(register_Btn);
		btn_Pal.add(login_Btn);
		
		
		buttonListener btn_listener = new buttonListener();
		set_Btn.addActionListener(btn_listener);
		register_Btn.addActionListener(btn_listener);
		login_Btn.addActionListener(btn_listener);
		
		
		bgjpl.setPreferredSize(new Dimension(300,210));
		
		img_Pal.setOpaque(false);
		input_Pal.setOpaque(false);
		btn_Pal.setOpaque(false);
		
		bgjpl.add(img_Pal,BorderLayout.NORTH);
		bgjpl.add(input_Pal,BorderLayout.CENTER);
		bgjpl.add(btn_Pal,BorderLayout.SOUTH);
		
		this.getContentPane().add(bgjpl);
		
		this.setLocation(550, 250);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(getPreferredSize());
		this.setResizable(false);
		
	}

	public class buttonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == set_Btn) {
				set_Frame.setVisible(true);
			}else if(e.getSource() == login_Btn){
				if (Set_Frame.getIP().isEmpty() || Set_Frame.getPort() == -1) {
					JOptionPane.showMessageDialog(null, "服务器设置为空，请先设置服务器！");
				}else {
					//userName_Txt.setEditable(false);
					if (userName_Txt.getText().isEmpty() || password_Pwd.getPassword().length == 0) {
						JOptionPane.showMessageDialog(null, "用户名  / 密码不能为空！");
						userName_Txt.setEditable(true);
					}else {
						//登陆操作
						clientSer = new Client(Set_Frame.getIP(),Set_Frame.getPort());
						//clientSer.start();
						Protocol sendProtocol = new Protocol(Protocol.LOGIN,null,Protocol.SERVER,
								getUserName() + Protocol.SEPERATOR +  getPassword());
						if(clientSer != null)
							clientSer.sendToServer(sendProtocol.getProtocolStr());
					}
				}
				
			}else{
				new Register_Frame();
			}
		}
	}
	
	public static String getUserName() {
		return userName_Txt.getText();
	}
	
	public String getPassword() {
		return new String(password_Pwd.getPassword());
	}
	
	public static void disposeFrame() {
		lf.dispose();
	}
}
