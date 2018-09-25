   package socketServerFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
//������Ϣ��ʾ��
import Common.ShowMessage;

public class ServerLoginFrame extends JFrame{
	
	//�������
	private JPanel topPal;
	private JLabel serverManage_Lab;
	//�в����
	private JPanel centerPal;
	private JLabel account_Lab,password_Lab;
	private JTextField account_Txt;
	private JPasswordField password_Txt;
	//�ײ����
	private JPanel buttonPal;
	private JButton login_btn,cancel_btn;
	//��ť������
	ButtonListener btnListener = new ButtonListener();
	//�˺�������Ϣ
	private final String account = "admin";
	private final String password = "admin";
	
	public ServerLoginFrame()
	{
		init();
	}
	
	private void init()
	{
		//��
		topPal = new JPanel();
		topPal.setPreferredSize(new Dimension(250,50));
		topPal.setBorder(BorderFactory.createEtchedBorder());
		topPal.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		serverManage_Lab = new JLabel("������������");
		serverManage_Lab.setFont(new java.awt.Font("����", 1, 20));
		
		topPal.add(serverManage_Lab);
		
		//��
		centerPal = new JPanel();
		centerPal.setPreferredSize(new Dimension(250,100));
		JPanel cPal = new JPanel();
		cPal.setPreferredSize(new Dimension(150,100));
		//centerPal.setLayout(new GridLayout(2,2));
		
		account_Lab = new JLabel("�˺�");
		account_Txt = new JTextField(8);
		
		password_Lab = new JLabel("����");
		password_Txt = new JPasswordField(8);
		
		cPal.add(account_Lab);
		cPal.add(account_Txt);
		cPal.add(password_Lab);
		cPal.add(password_Txt);
		
		centerPal.add(cPal);
		//��
		buttonPal = new JPanel();
		buttonPal.setPreferredSize(new Dimension(250,50));
		buttonPal.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPal.setBorder(BorderFactory.createEtchedBorder());
		
		login_btn = new JButton("��½");
		login_btn.addActionListener(btnListener);
		
		cancel_btn = new JButton("ȡ��");
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
