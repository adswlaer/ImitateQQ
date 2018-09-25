package socketServerFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//��ʾ��Ϣ
import Common.ShowMessage;
import Common.UserInfo;
//���ݿ�
import DataBaseManage.mysqlManage;
//��������
import socketServer.Server;

public class ServerMainFrame extends JFrame{
		//��ť������
		ButtonListener btnListener = new ButtonListener();
		//�������Ĳ���Ԫ��
		private JPanel north_Pal;	//�����
		private JLabel ipTxt_Lab,ip_Lab,portTxt_Lab;	//ip��ǩ�Ͷ˿ڱ�ǩ
		private JTextField port_Txt;	//�˿��ı���
		private JButton serverSwitch_btn;	//���������ذ�ť
		//�в����Ĳ���Ԫ��
		private JPanel center_Pal;	//�����
		private JLabel online_Lab;	//����������ǩ
		private int onlineNum=0;	//��������ͳ��
		private JTable table;
		private JScrollPane scrollPane;
		private Vector rows=null;
		//�������Ĳ���Ԫ��
		private JPanel east_Pal,log_Pal,radio_Pal;	//����壬��־��壬�㲥���
		private static JTextArea log_Txt;	//��־�ı���͹㲥�ı���
		private static JTextArea radio_Txt;
		private JLabel log_Lab,radio_Lab;	//��־��ǩ�͹㲥��ǩ
		private JButton sendRadio_btn;	//���͹㲥��ť
		//�ϲ����Ĳ���Ԫ��
		private JPanel south_Pal;
		private JLabel date_Lab;
		private Server server = null;
		private ServerMainFrame smf;
		
		public ServerMainFrame()
		{
			init();
			smf = this;
			
		}
		
		public void init()
		{
			initNorth();
			initCenter();
			initEast();
			initSouth();
			
			//Container c = new Container();
			this.setLayout(new BorderLayout());
			
			this.add(north_Pal, BorderLayout.NORTH);
			this.add(center_Pal, BorderLayout.CENTER);
			this.add(east_Pal, BorderLayout.EAST);
			this.add(south_Pal, BorderLayout.SOUTH);
			
			this.setTitle("QQ������");
			this.setSize(getPreferredSize());
			this.setResizable(false);
			this.setVisible(true);
			
			this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		}
		//��ʼ���������
		public void initNorth()
		{
			north_Pal = new JPanel();
			north_Pal.setPreferredSize(new Dimension(800,50));
			north_Pal.setBorder(BorderFactory.createEtchedBorder());
			
			ipTxt_Lab = new JLabel("����IP��");
			ip_Lab = new JLabel();
			try {
				String hostIp = InetAddress.getLocalHost().getHostAddress().toString();
				ip_Lab.setText(hostIp);
				ip_Lab.setForeground(Color.magenta);
			}catch (Exception e){
				e.printStackTrace();
				ip_Lab.setText("��ȡʧ��");
				ip_Lab.setForeground(Color.red);
			}
			
			portTxt_Lab = new JLabel("�˿ں�");
			port_Txt = new JTextField(5);
			port_Txt.setText("11111");
			
			serverSwitch_btn = new JButton("����");
			serverSwitch_btn.addActionListener(btnListener);
			serverSwitch_btn.setBackground(Color.GREEN);
			
			north_Pal.add(ipTxt_Lab);
			north_Pal.add(ip_Lab);
			north_Pal.add(portTxt_Lab);
			north_Pal.add(port_Txt);
			north_Pal.add(serverSwitch_btn);

		}
		//��ʼ���в����
		private void initCenter()
		{
			center_Pal = new JPanel();
			center_Pal.setPreferredSize(new Dimension(400,600));
			center_Pal.setLayout(new BoxLayout(center_Pal,BoxLayout.Y_AXIS));
			center_Pal.setBorder(BorderFactory.createEtchedBorder());
			
			online_Lab = new JLabel();
			online_Lab.setText("��������:"+Integer.toString(onlineNum)+"��");
			
			table = new JTable(rows,tableHead());
			scrollPane = new JScrollPane(table);
			
			center_Pal.add(online_Lab);
			center_Pal.add(scrollPane);
		}
		//��ʼ���������
		private void initEast()
		{
			east_Pal = new JPanel();
			east_Pal.setPreferredSize(new Dimension(390,600));
			east_Pal.setBorder(BorderFactory.createEtchedBorder());
			
			//��־���
			log_Pal = new JPanel();
			log_Pal.setPreferredSize(new Dimension(380,290));
			//log_Pal.setLayout(new BoxLayout(log_Pal,BoxLayout.Y_AXIS));
			log_Pal.setBorder(BorderFactory.createEtchedBorder());
			
			log_Lab = new JLabel("��Ϣ��־");
			
			log_Txt = new JTextArea(12,30);
			log_Txt.setLineWrap(true);
			log_Txt.setEditable(false);
			JScrollPane log_scroll = new JScrollPane(log_Txt);
			
			log_Pal.add(log_Lab);
			log_Pal.add(log_scroll);
			
			//�㲥���
			radio_Pal = new JPanel();
			radio_Pal.setPreferredSize(new Dimension(380,250));
			radio_Pal.setBorder(BorderFactory.createEtchedBorder());
			
			radio_Lab = new JLabel("��ȫ���û�������Ϣ�㲥");
			
			radio_Txt = new JTextArea(10,25);
			radio_Txt.setLineWrap(true);
			JScrollPane radio_scroll = new JScrollPane(radio_Txt);
			
			sendRadio_btn = new JButton("����");
			sendRadio_btn.addActionListener(btnListener);
			
			radio_Pal.add(radio_Lab);
			radio_Pal.add(radio_scroll);
			radio_Pal.add(sendRadio_btn);
			
			east_Pal.add(log_Pal);
			east_Pal.add(radio_Pal);
		}
		
		//��ʼ�������
		private void initSouth()
		{
			south_Pal = new JPanel();
			south_Pal.setPreferredSize(new Dimension(800,30));
			south_Pal.setBorder(BorderFactory.createEtchedBorder());
			
			date_Lab = new JLabel();
			
			south_Pal.add(date_Lab);
			
			//�����߳�
			new Thread() {
				public void run() {
				try {
				while (true) {
				date_Lab.setText(new Date().toString());//��ʾ��ǰʱ��
				Thread.sleep(1000);//��ͣһ��
				}
				} catch (Exception e) {
				}
				}
				}.start();
		}
		
		//�õ��˿ں�
		public int getPort()
		{
			return Integer.parseInt(port_Txt.getText());
		}
		
		public  void updateLog(String logTxt)
		{
			log_Txt.append(logTxt);
		}
		
		public synchronized void updateOnlineCount(int count)
		{
			onlineNum = count;
			online_Lab.setText("��������:"+Integer.toString(onlineNum)+"��");
		}
		
		private Vector tableHead()
		{
			Vector head = new Vector();
			head.addElement("�˺�");
			head.addElement("�ǳ�");
			head.addElement("IP");
			head.addElement("ʱ��");
		 
			return head;
		}
		
		public synchronized void updateTable(Map<String,UserInfo> clientsMap)
		{
			rows = new Vector();
			
			for(UserInfo userInfo:clientsMap.values())
			{
				Vector currentRow = new Vector();
				currentRow.addElement(userInfo.getAccount());
				System.out.println("map:"+userInfo.getAccount());
				currentRow.addElement(userInfo.getNick());
				currentRow.addElement(userInfo.getSocket().getInetAddress().getHostAddress());
				String time = new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(new Date());
				currentRow.addElement(time);
				
				rows.addElement(currentRow);
				
			}
			
			center_Pal.remove(scrollPane);
			table = new JTable(rows,tableHead());
			scrollPane = new JScrollPane(table);
			center_Pal.add(scrollPane);
			
			center_Pal.validate();
		}
		
		
		
		//��ť������
		private class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				if(event.getSource() == serverSwitch_btn) 
				{
					if(serverSwitch_btn.getText().equals("����")) {
						server = new Server(getPort(),smf);
						server.openService();
						serverSwitch_btn.setText("�رշ�����");
						serverSwitch_btn.setBackground(Color.RED);
						port_Txt.setEditable(false);
					}
					else {
						server.stopService();
						System.exit(0);
					}
						
				}
				else if(event.getSource() == sendRadio_btn) {
					if(server!=null) {
						if(radio_Txt.getText()!=null) {
							Server.audio(radio_Txt.getText());
							radio_Txt.setText(null);
						}else {
							JOptionPane.showMessageDialog(null, "�㲥��Ϣ����Ϊ��!");
						}
					}else {
						JOptionPane.showMessageDialog(null, "���ȴ򿪷�������");
					}
				}
				
			}
		}
}
