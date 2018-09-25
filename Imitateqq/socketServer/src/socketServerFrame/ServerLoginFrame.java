   package socketServerFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
//错误信息提示包
import Common.ShowMessage;

public class ServerLoginFrame extends JFrame{
	
	//顶端面板
	private JPanel topPal;
	private JLabel serverManage_Lab;
	//中部面板
	private JPanel centerPal;
	private JLabel account_Lab,password_Lab;
	private JTextField account_Txt;
	private JPasswordField password_Txt;
	//底部面板
	private JPanel buttonPal;
	private JButton login_btn,cancel_btn;
	//按钮监听器
	ButtonListener btnListener = new ButtonListener();
	//账号密码信息
	private final String account = "admin";
	private final String password = "admin";
	
	public ServerLoginFrame()
	{
		init();
	}
	
	private void init()
	{
		//顶
		topPal = new JPanel();
		topPal.setPreferredSize(new Dimension(250,50));
		topPal.setBorder(BorderFactory.createEtchedBorder());
		topPal.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		serverManage_Lab = new JLabel("服务器管理工具");
		serverManage_Lab.setFont(new java.awt.Font("隶书", 1, 20));
		
		topPal.add(serverManage_Lab);
		
		//中
		centerPal = new JPanel();
		centerPal.setPreferredSize(new Dimension(250,100));
		JPanel cPal = new JPanel();
		cPal.setPreferredSize(new Dimension(150,100));
		//centerPal.setLayout(new GridLayout(2,2));
		
		account_Lab = new JLabel("账号");
		account_Txt = new JTextField(8);
		
		password_Lab = new JLabel("密码");
		password_Txt = new JPasswordField(8);
		
		cPal.add(account_Lab);
		cPal.add(account_Txt);
		cPal.add(password_Lab);
		cPal.add(password_Txt);
		
		centerPal.add(cPal);
		//底
		buttonPal = new JPanel();
		buttonPal.setPreferredSize(new Dimension(250,50));
		buttonPal.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPal.setBorder(BorderFactory.createEtchedBorder());
		
		login_btn = new JButton("登陆");
		login_btn.addActionListener(btnListener);
		
		cancel_btn = new JButton("取消");
		cancel_btn.addActionListener(btnListener);
		
		buttonPal.add(login_btn);
		buttonPal.add(cancel_btn);
		
		//Frame
		
		this.setLayout(new BorderLayout());
		this.add(topPal,BorderLayout.NORTH);
		this.add(centerPal,BorderLayout.CENTER);
		this.add(buttonPal,BorderLayout.SOUTH);
		this.setSize(getPreferredSize());
		
		this.setVisible(true);
		this.setResizable(false);
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2
				,(Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);
		
		this.addWindowListener(new WindowAdapter() {
    		public void windowsClosing(WindowEvent e) {
    			System.exit(0);
    		}
    	});
	}
	
	public String getAccount()
	{
		return account_Txt.getText();
	}
	
	public String getPassword()
	{	
		return new String(password_Txt.getPassword());
	}
	
	public void closeFrame()
	{
		this.dispose();
	}
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == cancel_btn)
				//setUnvisible();
				System.exit(0);
			else {
				if(account.equals(getAccount())&&password.equals(getPassword()))
				{
					closeFrame();
					new ServerMainFrame();
				}
				else
					ShowMessage.loginError();
			}
				
			
		}
	}
}
