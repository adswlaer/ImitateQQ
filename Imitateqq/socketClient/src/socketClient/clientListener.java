package socketClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import Common.FileChooser;
import Common.FileLog;
import Common.FileOperation;
import Common.FriendsInfo;
import Common.Prompt;
import Common.Protocol;
import socketClientFrame.FileFrame;
import socketClientFrame.FriendsCell;
import socketClientFrame.Login_Frame;
import socketClientFrame.MainPanel_Frame;
import socketClientFrame.PersonalChat_Frame;
import socketClientFrame.Search_Frame;
import socketClientFrame.VoiceFrame;
import socketClientFrame.groupchat_Frame;

public class clientListener implements Runnable{

	private Socket socket;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	private boolean flag = true;
	private MainPanel_Frame mpf = null;
	public  static Map<String, FriendsInfo> friendsMap = new ConcurrentHashMap<>();
	private static String account;
	private Protocol sendProtocol;
	private static File file;
	private static String fileSize;
	private FriendsInfo fInfo;
	private Search_Frame sf;
	private static String nick;
	private Prompt prompt = new Prompt();
	public static ArrayList<FileLog> fileRecord = new ArrayList<FileLog>();	//文件记录
	
	public clientListener(Socket socket) {
		this.socket = socket;
		
		try {
			is = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
	}
	
	public void run() {
		try {
			String protocolStr;
			while((protocolStr = br.readLine())!=null&&flag) {
				
				System.out.println(protocolStr);
				
				Protocol protocol = new Protocol(protocolStr);
				String order = protocol.getOrder();	
				
				switch(order) {
					case Protocol.LOGIN_SUCCESS:
						account = Login_Frame.getUserName();
						nick = protocol.getInfo();
						Login_Frame.disposeFrame();
						mpf = new MainPanel_Frame(account,nick,friendsMap);
						FileOperation.createDir("chatRecord\\"+account);
						break;
					case Protocol.LOGIN_FAIL:
						//do something
						JOptionPane.showMessageDialog(null,"用户名或密码不正确，请重新登陆！");
						Login_Frame.userName_Txt.setEditable(true);
						break;
					case Protocol.LOGIN_REPEATED:
						//do something
						JOptionPane.showMessageDialog(null, "此用户已登陆！");
						Login_Frame.userName_Txt.setEditable(true);
						break;
					case Protocol.GET_FRIENDS:
						String InfoStr = protocol.getInfo();
						//将 Info 拆分，用于构造 FriendsCell 
						String[] str = InfoStr.split("\\"+Protocol.SEPERATOR);
						String faccount = str[0];
						int fstatus = Integer.parseInt(str[4]);
						String fnick = str[1];
						String fip = null;
						if(fstatus!=0) {
						
						fip = str[2];
						}
					
						if(!friendsMap.containsKey(faccount)) {
							PersonalChat_Frame chatFrame = new PersonalChat_Frame(faccount,fnick,fstatus);
							chatFrame.setVisible(false);
							FriendsCell friendsCell = new FriendsCell(faccount, fnick, fstatus,chatFrame);
							FriendsInfo friendsInfo = new FriendsInfo(faccount,fnick,fip,fstatus,friendsCell);
							friendsMap.put(faccount, friendsInfo);
						}else {
							fInfo = friendsMap.get(faccount);
							fInfo.setNick(fnick);
							fInfo.setIp(fip);
							fInfo.setStatus(fstatus);
							fInfo.getCell().setStatus(fstatus);
							fInfo.getCell().setNick(fnick);
						}
						
						if(mpf != null)
							mpf.updateUI();
						if(fstatus != 0)
							prompt.onlineVoice();
						break;
					case Protocol.MSG_PRIVATE:
						fInfo = friendsMap.get(protocol.getFrom());
						fInfo.getCell().getChatFrame().appendTxt(fInfo.getNick(), protocol.getInfo());
						fInfo.getCell().HeadFlashing();
						prompt.msgVoice();
						break;
					case Protocol.MSG_GROUP:
						if(mpf.getGCF()==null)
							mpf.setGCF(new groupchat_Frame(nick));
						if (!account.equals(protocol.getFrom())) {
							String[] str_gc = protocol.getInfo().split("\\" + Protocol.SEPERATOR);
							mpf.getGCF().appendTxt(str_gc[0], str_gc[1]);
						}
						prompt.msgVoice();
						break;
					case Protocol.FILE:
						int n1 = JOptionPane.showConfirmDialog(null, protocol.getFrom()+"请求发文件给你，是否接受？", "文件传输", JOptionPane.YES_NO_OPTION); 
				        if (n1 == JOptionPane.YES_OPTION) { 
				        	String[] str1 = protocol.getInfo().split("\\+");
							String fileName = str1[0];
							long fileSize = Long.parseLong(str1[1]);
							long breakPoint=0;
							int exsit = 0;
							FileLog fl,fileLog = null;
							for(int i=0;i<fileRecord.size();i++) {
				        		fl = fileRecord.get(i);
				        		if(fl.getFileName().equals(fileName)&&fl.getSize()==fileSize);{
				        			exsit = 1;	//找到记录
				        			fileLog = fl;
				        			fileRecord.remove(i);
				        			breakPoint = fileLog.getBreakPoint();
				        			break;
				        		}
				        	}
							
				        	sendProtocol = new Protocol(Protocol.FILE_CONFIRM,protocol.getTo(),
				        			protocol.getFrom(),Long.toString(breakPoint));
				        	
				        	new FileFrame(str1[0],str1[1],friendsMap.get(protocol.getFrom()).getIp(),fileLog);
				        } else if (n1 == JOptionPane.NO_OPTION) { 
				        	sendProtocol = new Protocol(Protocol.FILE_REFUSE,protocol.getTo(),
				        			protocol.getFrom(),null);
				        } 
				        Client.sendToServer(sendProtocol.getProtocolStr());
				        break;
					case Protocol.FILE_CONFIRM:

					if(file!=null)
							new FileFrame(file.getName(),fileSize,file,Long.parseLong(protocol.getInfo()));
						file = null;
						break;
					case Protocol.FILE_REFUSE:
						JOptionPane.showMessageDialog(null, "对方拒绝接收文件!");
						break;
					case Protocol.VOICE:
						int n2 = JOptionPane.showConfirmDialog(null, protocol.getFrom()+"请求语音聊天是否接受？", "语音聊天", JOptionPane.YES_NO_OPTION); 
				        if (n2 == JOptionPane.YES_OPTION) { 
				        	sendProtocol = new Protocol(Protocol.VOICE_CONFIRM,protocol.getTo(),
				        			protocol.getFrom(),null);
				        	new VoiceFrame(friendsMap.get(protocol.getFrom()).getIp(),friendsMap.get(protocol.getFrom()).getNick());
				        } else if (n2 == JOptionPane.NO_OPTION) { 
				        	sendProtocol = new Protocol(Protocol.VOICE_REFUSE,protocol.getTo(),
				        			protocol.getFrom(),null);
				        } 
				        Client.sendToServer(sendProtocol.getProtocolStr());
				        break;
					case Protocol.VOICE_CONFIRM:
						new VoiceFrame(friendsMap.get(protocol.getFrom()).getIp(),friendsMap.get(protocol.getFrom()).getNick());
						break;
					case Protocol.VOICE_REFUSE:
						JOptionPane.showMessageDialog(null, "对方拒绝与你进行语音聊天");
						break;
					case Protocol.SEARCH_FRIEND_SUCCESS:
						String search_Info = protocol.getInfo();
						mpf.getSF().updateUI(search_Info);
						break;
					case Protocol.SEARCH_FRIEND_FAIL:
						JOptionPane.showMessageDialog(null, "该用户不存在！");
						break;
					case Protocol.ADD_FRIEND:
						int n3 = JOptionPane.showConfirmDialog(null, 
								protocol.getFrom()+"请求加你为好友,是否接受？","好友请求",JOptionPane.YES_NO_OPTION);
						if (n3 == JOptionPane.YES_OPTION) {
							sendProtocol = new Protocol(Protocol.ADD_FIREND_CONFIRM,protocol.getTo(),
									protocol.getFrom(),null); 
						}else if(n3 == JOptionPane.NO_OPTION) {
							sendProtocol = new Protocol(Protocol.ADD_FIREND_RUFUSE,protocol.getTo(),
									protocol.getFrom(),null);
						}
						Client.sendToServer(sendProtocol.getProtocolStr());
						break;
					case Protocol.ADD_FIREND_CONFIRM:
						JOptionPane.showMessageDialog(null, protocol.getFrom() + "接受了你的好友请求，现在可以开始聊天了");
						break;
						//更新好友列表
					case Protocol.ADD_FIREND_RUFUSE:
						JOptionPane.showMessageDialog(null, "对方拒绝了你的好友请求。");
						break;
					case Protocol.QUIT:
						if(friendsMap.containsKey(protocol.getInfo())){
							FriendsInfo fInfo = friendsMap.get(protocol.getInfo());
							fInfo.setStatus(0);
							fInfo.getCell().setStatus(0);
							mpf.updateUI();	
						}
						prompt.onlineVoice();
						break;
					case Protocol.AUDIO:
						JOptionPane.showMessageDialog(null, "系统通知:"+"\n"+protocol.getInfo());
						break;
					default:
						break;
			}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
				JOptionPane.showMessageDialog(null, "你已掉线，请重新登陆");
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	public static String getNick() {
		return nick;
	}
	
	public static String getAccount() {
		return account;
	}
	
	public static void setFile(File file1) {
		file = file1;
	}
	
	public static void setFileSize(String fileSize1) {
		fileSize = fileSize1;
	}
	
	
}
