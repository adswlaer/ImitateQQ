package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Common.Protocol;
import socketClient.Register;

public class Register_Frame extends JFrame{
	/**注册界面**/
	private JPanel info_Pal,button_Pal,place_Pal,logo_Pal;	//信息面板和按钮面板
	private JLabel password_Lab,rePassword_Lab,nick_Lab,logo_Lab;	//用户名、密码和昵称标签
	private JTextField nick_Txt;	//用户名和昵称文本框
	private JPasswordField password_Txt,rePassword_Txt;
	private JButton register_btn,cancel_btn;	//注册按钮和取消按钮
	private Register_Frame register;
	private BackGroundPanel bg_Pal;
	
	public Register_Frame()
	{
		init();
		register = this;
	}
	
	private void init()
	{
		
		this.setLayout(new BorderLayout());
		
		//按钮监听器
		ButtonListener btnListener = new ButtonListener();
		bg_Pal = new BackGroundPanel("image/Register_bg.jpg");
		bg_Pal.setLayout(new BorderLayout());
		
		JPanel pal = new JPanel();
		pal.setPreferredSize(new Dimension(200,250));
		//信息面板
		info_Pal = new JPanel();
		info_Pal.setPreferredSize(new Dimension(180,220));
		info_Pal.setOpaque(false);
		
		logo_Pal = new JPanel();
		logo_Pal.setPreferredSize(new Dimension(180,50));
		logo_Pal.setBorder(BorderFactory.createEtchedBorder());
		logo_Pal.setOpaque(false);
		
		logo_Lab = new JLabel("赶紧注册吧，体验LOW版QQ！");
		
		logo_Pal.add(logo_Lab);

		password_Lab = new JLabel("密码");
		password_Txt = new JPasswordField(15);
		
		rePassword_Lab = new JLabel("确认密码");
		rePassword_Txt = new JPasswordField(15);
		
		nick_Lab = new JLabel("昵称");
		nick_Txt = new JTextField(15);
		
		info_Pal.add(logo_Pal);

		info_Pal.add(nick_Lab);
		info_Pal.add(nick_Txt);
		info_Pal.add(password_Lab);
		info_Pal.add(password_Txt);
		info_Pal.add(rePassword_Lab);
		info_Pal.add(rePassword_Txt);
		
		pal.add(info_Pal);
		pal.setOpaque(false);
		
		//按钮面板
		button_Pal = new JPanel();
		//button_Pal.setLayout(new GridLayout(1,2));
		button_Pal.setBorder(BorderFactory.createEtchedBorder());
		button_Pal.setPreferredSize(new Dimension(250,40));
		
		register_btn = new JButton("注册");
		register_btn.addActionListener(btnListener);
		
		cancel_btn = new JButton("取消");
		cancel_btn.addActionListener(btnListener);
		
		button_Pal.add(register_btn);
		button_Pal.add(cancel_btn);
		button_Pal.setOpaque(false);
		
		place_Pal = new JPanel();
		place_Pal.setPreferredSize(new Dimension(250,100));
		place_Pal.setBorder(BorderFactory.createEtchedBorder());
		place_Pal.setOpaque(false);
		
		//设置Frame
		bg_Pal.add(pal,BorderLayout.NORTH);
		bg_Pal.add(place_Pal, BorderLayout.CENTER);
		bg_Pal.add(button_Pal,BorderLayout.SOUTH);
		this.add(bg_Pal);
		this.setTitle("注册");
		this.setSize(getPreferredSize());
		this.setVisible(true);
		this.setResizable(false);	//设置Frame大小不可改变
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2
				,(Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);
	}
	
	//设置Frame不可见
	public void setUnvisible()
	{
		this.dispose();
	}
	
	//监听器
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == cancel_btn)
				setUnvisible();
			else if(event.getSource() == register_btn)
			{
				int flag=1;
				String nk = register.getNick();
				String passwd = register.getPassword();
				String rePasswd = register.getRePassword();
				if(flag==1&&nk.isEmpty()||passwd.isEmpty()||rePasswd.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "信息不能为空 ！");
					flag=0;
				}
				if(flag==1&&!passwd.equals(rePasswd))
				{
					JOptionPane.showMessageDialog(null, "两次输入密码不一致，请重新输入！");
					flag=0;
				}
				if(flag==1&&passwd.length()<6||passwd.length()>15) {
					JOptionPane.showMessageDialog(null, "密码长度为6~15位，请重新设置");
					flag = 0;
				}
				if(flag==1)
				{
					new Thread(new Register(nk, passwd)).start();
					setUnvisible();
					System.out.println("正在注册!");
				}
				
			
			}
			
		}
	}
	
	public String getPassword()
	{	
		return new String(password_Txt.getPassword());
	}
	
	public String getRePassword()
	{	
		return new String(rePassword_Txt.getPassword());
	}
	
	public String getNick()
	{
		return nick_Txt.getText();
	}
}
