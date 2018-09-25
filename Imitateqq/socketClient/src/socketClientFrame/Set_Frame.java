package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Set_Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel info_Pal,button_Pal;			//��Ϣ���Ͱ�ť���
	private JLabel serverIP_Lab,serverPort_Lab;	//IP���˿ںű�ǩ
	private static JTextField serverIP_Txt;	//IP���˿ں��ı���
	private static JTextField serverPort_Txt;
	private JButton sure_btn,cancel_btn;		//ȷ�ϣ�ȡ����ť
	private Set_Frame set_Frame;
	private BackGroundPanel setBg_Pal;
	
	public Set_Frame() {
		init();
		set_Frame = this;
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		
		setBg_Pal = new BackGroundPanel("image/friendsList_cen_bg.jpg");
		setBg_Pal.setLayout(new BorderLayout());
		
		info_Pal = new JPanel();
		info_Pal.setLayout(new GridLayout(5,1));
		info_Pal.setPreferredSize(new Dimension(200,150));
		info_Pal.setBorder(BorderFactory.createTitledBorder("����������"));
		info_Pal.setOpaque(false);
		
		serverIP_Lab = new JLabel("������IP");
		serverIP_Txt = new JTextField(15);
		
		serverPort_Lab = new JLabel("�������˿�");
		serverPort_Txt = new JTextField(5);
		
		info_Pal.add(serverIP_Lab);
		info_Pal.add(serverIP_Txt);
		info_Pal.add(serverPort_Lab);
		info_Pal.add(serverPort_Txt);
		
		button_Pal = new JPanel();
		button_Pal.setLayout(new FlowLayout(FlowLayout.CENTER));
		button_Pal.setPreferredSize(new Dimension(200,75));
		button_Pal.setOpaque(false);
		
		sure_btn = new JButton("ȷ��");
		cancel_btn = new JButton("ȡ��");
		
		button_Pal.add(sure_btn);
		button_Pal.add(cancel_btn);
		
		ButtonListener btn_listener = new ButtonListener();
		sure_btn.addActionListener(btn_listener);
		cancel_btn.addActionListener(btn_listener);
		
		setBg_Pal.add(info_Pal,BorderLayout.NORTH);
		setBg_Pal.add(button_Pal,BorderLayout.CENTER);
		
		this.add(setBg_Pal);
		this.setTitle("");
		this.setSize(getPreferredSize());
		this.setLocation(600, 225);
		this.setVisible(false);
		this.setResizable(false);	//����Frame��С���ɸı�
		
	}
	
	public static String getIP() {
		return serverIP_Txt.getText();
	}
	
	public static int getPort() {
		if (serverPort_Txt.getText().isEmpty())
			return -1;
		return Integer.parseInt(serverPort_Txt.getText());
	}
	
	public class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == sure_btn) {
				if(serverIP_Txt.getText().isEmpty() ||serverPort_Txt.getText().isEmpty() ) {
					JOptionPane.showMessageDialog(null,"������IP / �˿ں� Ϊ�գ�");
				}else {
					set_Frame.setVisible(false);
				}
			}else {
				set_Frame.setVisible(false);
			}
		}	
	}
}
