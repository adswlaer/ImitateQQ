package socketClientFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;  
import javax.swing.JScrollPane;
import Common.FriendsInfo;  
  
public class MainPanel_Frame extends JFrame{  
	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
	private String account = null, nick = null;
	private JLabel myInfo_Lab;
	private BackGroundPanel topPanel,listPanel,btnPal;
	private JScrollPane listScrollpane;
	private JButton search_Btn,groupchat_Btn;
	private Map<String, FriendsInfo> friendsMap = new ConcurrentHashMap<>();
	private static Search_Frame sf = null;
	private groupchat_Frame gcf = null;
	private ImageIcon top_bg = new ImageIcon("image/friendsList_top_bg.jpg");
	
	public MainPanel_Frame(String account,String nick,Map<String, FriendsInfo> friendsMap) {
		this.account = account;
		this.nick = nick;
		this.friendsMap = friendsMap;

		init();
	}
	
	private void init() {
		
		initTopPanel();
		
		listPanel = new BackGroundPanel("image/friendsList_cen_bg.jpg");
		listPanel.setSize(210,450);
		listScrollpane = new JScrollPane(listPanel);
		
		initButtomPanel();
		
		this.setLayout(new BorderLayout());
		this.add(topPanel,BorderLayout.NORTH);
		this.add(listScrollpane,BorderLayout.CENTER);
		this.add(btnPal,BorderLayout.SOUTH);
		
		this.setResizable(false);
		this.setSize(260,600);
		this.setVisible(true);
		this.setTitle("QQ");
		this.setLocation(1050, 70);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void initTopPanel() {
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(260,100));
		
		//背景面板
		JPanel jplBg = new JPanel();
		jplBg.setPreferredSize(new Dimension(260,100));
		
		//top_bg.setImage(top_bg.getImage().getScaledInstance(270, 110,Image.SCALE_DEFAULT)); 
		//JLabel jlbImg = new JLabel(top_bg);
		
		//jplBg.add(jlbImg);

		topPanel = new BackGroundPanel("image/friendsList_top_bg.jpg");
		topPanel.setPreferredSize(new Dimension(260,100));
		topPanel.setBackground(Color.CYAN);

		JPanel jpl1 = new JPanel();
		jpl1.setPreferredSize(new Dimension(80,100));
		//jpl1.setBackground(new Color().getTransparency(#BBDAEE));
		
		ImageIcon headIcon = new ImageIcon("image/head.png");
		headIcon.setImage(headIcon.getImage().getScaledInstance(65, 65,Image.SCALE_DEFAULT)); 
		
		JLabel jlb1 = new JLabel();
		jlb1.setIcon(headIcon);
		
		jpl1.add(jlb1);
		
		topPanel.add(jpl1);
		
		JPanel jpl2 = new JPanel();
		jpl2.setPreferredSize(new Dimension(150,90));
		jpl2.setLayout(new BoxLayout(jpl2,BoxLayout.Y_AXIS));
		
		//jpl2.setBackground(Color.CYAN);
		
		JLabel jlbAccount = new JLabel(account);
		JLabel jlbNick = new JLabel(nick);
		JLabel jlbStatus = new JLabel("在线");
		
		jpl2.add(jlbAccount);
		jpl2.add(jlbNick);
		jpl2.add(jlbStatus);
		
		topPanel.add(jpl2);
		
		jpl1.setOpaque(false);
		jpl2.setOpaque(false);
		topPanel.setOpaque(false);
		
		//layeredPane.add(jplBg, new Integer(0));
		//layeredPane.add(topPanel, new Integer(1));
	}
	
	private void initButtomPanel() {
		btnPal = new BackGroundPanel("image/friendsList_but_bg.jpg");
		btnPal.setPreferredSize(new Dimension(260,50));
		
		groupchat_Btn = new JButton("群聊");
		search_Btn = new JButton("查找");
		ButtonListener bl = new ButtonListener();
		groupchat_Btn.addActionListener(bl);
		search_Btn.addActionListener(bl);
		
		btnPal.add(groupchat_Btn);
		btnPal.add(search_Btn);
		//btnPal.setBackground(Color.CYAN);
	}
	
	public Search_Frame getSF() {
		return sf;
	}
	
	public void setGCF(groupchat_Frame gcf) {
		this.gcf = gcf;
	}
	
	public groupchat_Frame getGCF() {
		return gcf;
	}
	
	public void updateUI() {
		this.remove(listScrollpane);
		
		listPanel = new BackGroundPanel("image/friendsList_cen_bg.jpg");
		listPanel.setPreferredSize(new Dimension(210,450));
		//listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
		
		for(String key:friendsMap.keySet()) {
			if(friendsMap.get(key).getStatus()!=0)
				listPanel.add(friendsMap.get(key).getCell());
		}
		
		for(String key:friendsMap.keySet()) {
			if(friendsMap.get(key).getStatus()==0)
				listPanel.add(friendsMap.get(key).getCell());
		}
		
		//listPanel.setSize(250,450);
		listScrollpane = new JScrollPane(listPanel);
		
		this.add(listScrollpane,BorderLayout.CENTER);
		this.validate();
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == search_Btn) {
				if(sf==null)
					sf = new Search_Frame();
				else
					sf.setVisible(true);
			}else if (e.getSource() == groupchat_Btn) {
				if (gcf==null)
					gcf = new groupchat_Frame(nick);
				else 
					gcf.setVisible(true);
			}
		
		}
	}
}