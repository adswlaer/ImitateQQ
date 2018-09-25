package socketServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JScrollPane;

import Common.Protocol;
import Common.ShowMessage;
import Common.UserInfo;
import DataBaseManage.mysqlManage;
import DataBaseManage.AddFriend;
import DataBaseManage.GetFriends;
import DataBaseManage.ReadRecord;
import DataBaseManage.SaveRecord;
import DataBaseManage.SearchFriend;
import DataBaseManage.UpdateUser;
import socketServerFrame.ServerMainFrame;
//监听服务器有无信息到达的线程
public class ServerListener implements Runnable{

	private Socket socket = null;
	private ServerMainFrame smf;
	private String account=null;
	private Map<String, UserInfo> clientsMap;
	private ArrayList<String> friendsList;
	
	public ServerListener(Socket socket,ServerMainFrame smf,Map<String, UserInfo> clientsMap)
	{
		this.socket = socket;
		this.smf = smf;
		this.clientsMap = clientsMap;
	}
	
	//线程执行的操作，响应客户端的请求
	public void run()
	{
		try {
			//获取输入流，并读取客户端信息
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String msg = null;
			//获取输出流
			//OutputStream os = socket.getOutputStream();
			//PrintWriter pw = new PrintWriter(os);
			
			while ((msg = br.readLine())!= null) {
				String protocolStr = msg;
				Protocol protocol = new Protocol(protocolStr);	//获取协议
				//更新日志
				smf.updateLog(protocol.toString());
				
				String order = protocol.getOrder();		//获取命令
				OutputStream os = null;
				PrintWriter pw = null;
				Protocol sendProtocol;
				//解析命令
				switch(order) 
				{
					//注册指令
					case Protocol.REGISTER:	
						ServerRegister sr = new ServerRegister(socket,protocol.getInfo());
						smf.updateLog(sr.getSendProtocol().toString());
						break;
					//登陆指令
					case Protocol.LOGIN:
						ServerLogin sl = new ServerLogin(socket,clientsMap,protocol.getInfo());
						account = sl.getAccount();
						smf.updateLog(sl.getLoginProtocol().toString());
						//更新用户数据库
						new UpdateUser("login",socket.getInetAddress().getHostAddress(),account);	
						//发送好友列表
						if(!account.isEmpty()) {
							SendFriendsList sendFriendsList = new SendFriendsList(account,clientsMap,socket);
							friendsList = sendFriendsList.getFriendsList();
							smf.updateLog("\n用户"+account+"已上线！");	//日志提示用户上线
						
							//提醒在线的好友，本socket上线
							new NotifyOnline(account,friendsList,clientsMap);
							//更新在线用户人数
							smf.updateOnlineCount(clientsMap.size());
							//更新用户在线列表
							smf.updateTable(clientsMap);
						}
						//发送离线信息
						ReadRecord rr = new ReadRecord(account);
						ArrayList<Protocol> record = rr.getRecord();
						
						os = socket.getOutputStream();
						pw = new PrintWriter(os);
						for(int i=0;i<record.size();i++) {
							System.out.println(record.get(i).getProtocolStr());
							pw.println(record.get(i).getProtocolStr());
							pw.flush();
						}
						break;
					//群聊
					case Protocol.MSG_GROUP:
						for(UserInfo userInfo:clientsMap.values()) {
							os = userInfo.getSocket().getOutputStream();
							pw = new PrintWriter(os);
							
							pw.println(protocolStr);
							pw.flush();
						}
						break;
					//转发消息	
					case Protocol.FILE:
					case Protocol.FILE_CONFIRM:
					case Protocol.FILE_REFUSE:
					case Protocol.VOICE:
					case Protocol.VOICE_CONFIRM:
					case Protocol.VOICE_REFUSE:
					case Protocol.MSG_PRIVATE:
						//如果发送对象不在线将信息存入数据库
						if(protocol.getOrder().equals(Protocol.MSG_PRIVATE)&&!clientsMap.containsKey(protocol.getTo()))
							new SaveRecord(protocol.getFrom(),protocol.getTo(),protocol.getTime(),protocol.getInfo());
					case Protocol.ADD_FIREND_RUFUSE:
					case Protocol.ADD_FRIEND:
					case Protocol.ADD_FIREND_CONFIRM:
						
						if(clientsMap.containsKey(protocol.getTo())) {
							os = clientsMap.get(protocol.getTo()).getSocket().getOutputStream();
							pw = new PrintWriter(os);
						
							pw.println(protocolStr);
							pw.flush();
						}
						if(order.equals(Protocol.ADD_FIREND_CONFIRM)) {
							new AddFriend(protocol.getFrom(),protocol.getTo());
							//发送好友列表
							if(!account.isEmpty()) {
								SendFriendsList sendFriendsList = new SendFriendsList(account,clientsMap,socket);
								friendsList = sendFriendsList.getFriendsList();
							}
							//提醒在线的好友，本socket上线
							new NotifyOnline(account,friendsList,clientsMap);
						}
						break;
					case Protocol.SEARCH_FRIEND:
						SearchFriend searchFriend = new SearchFriend(protocol.getInfo());
						String fnick,fstatus;
						fnick = searchFriend.getNick();
						if(fnick != null) {
							fstatus = searchFriend.getStatus();
							//协议格式：order+发account/回order+account+nick+status
							sendProtocol = new Protocol(Protocol.SEARCH_FRIEND_SUCCESS,Protocol.SERVER,protocol.getFrom(),
									protocol.getInfo()+Protocol.SEPERATOR+fnick+Protocol.SEPERATOR+fstatus);
						}
						else
							sendProtocol = new Protocol(Protocol.SEARCH_FRIEND_FAIL,Protocol.SERVER,protocol.getFrom(),
									null);
						os = clientsMap.get(protocol.getFrom()).getSocket().getOutputStream();
						pw = new PrintWriter(os);
						pw.println(sendProtocol.getProtocolStr());
						pw.flush();
						break;
					default:
						break;
			
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//ShowMessage.failOpenServerListener();
		}finally {
			if(socket!=null&&account!=null) {
				try {
					//清除连接
					clientsMap.remove(account);
					new UpdateUser("quit",socket.getInetAddress().getHostAddress(),account);
					socket.close();
					smf.updateOnlineCount(clientsMap.size());
					smf.updateTable(clientsMap);
					new NotifyQuit(account,friendsList,clientsMap);
					smf.updateLog("\n用户"+account+"下线了！");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}


