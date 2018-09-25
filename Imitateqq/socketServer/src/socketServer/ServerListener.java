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
//����������������Ϣ������߳�
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
	
	//�߳�ִ�еĲ�������Ӧ�ͻ��˵�����
	public void run()
	{
		try {
			//��ȡ������������ȡ�ͻ�����Ϣ
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String msg = null;
			//��ȡ�����
			//OutputStream os = socket.getOutputStream();
			//PrintWriter pw = new PrintWriter(os);
			
			while ((msg = br.readLine())!= null) {
				String protocolStr = msg;
				Protocol protocol = new Protocol(protocolStr);	//��ȡЭ��
				//������־
				smf.updateLog(protocol.toString());
				
				String order = protocol.getOrder();		//��ȡ����
				OutputStream os = null;
				PrintWriter pw = null;
				Protocol sendProtocol;
				//��������
				switch(order) 
				{
					//ע��ָ��
					case Protocol.REGISTER:	
						ServerRegister sr = new ServerRegister(socket,protocol.getInfo());
						smf.updateLog(sr.getSendProtocol().toString());
						break;
					//��½ָ��
					case Protocol.LOGIN:
						ServerLogin sl = new ServerLogin(socket,clientsMap,protocol.getInfo());
						account = sl.getAccount();
						smf.updateLog(sl.getLoginProtocol().toString());
						//�����û����ݿ�
						new UpdateUser("login",socket.getInetAddress().getHostAddress(),account);	
						//���ͺ����б�
						if(!account.isEmpty()) {
							SendFriendsList sendFriendsList = new SendFriendsList(account,clientsMap,socket);
							friendsList = sendFriendsList.getFriendsList();
							smf.updateLog("\n�û�"+account+"�����ߣ�");	//��־��ʾ�û�����
						
							//�������ߵĺ��ѣ���socket����
							new NotifyOnline(account,friendsList,clientsMap);
							//���������û�����
							smf.updateOnlineCount(clientsMap.size());
							//�����û������б�
							smf.updateTable(clientsMap);
						}
						//����������Ϣ
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
					//Ⱥ��
					case Protocol.MSG_GROUP:
						for(UserInfo userInfo:clientsMap.values()) {
							os = userInfo.getSocket().getOutputStream();
							pw = new PrintWriter(os);
							
							pw.println(protocolStr);
							pw.flush();
						}
						break;
					//ת����Ϣ	
					case Protocol.FILE:
					case Protocol.FILE_CONFIRM:
					case Protocol.FILE_REFUSE:
					case Protocol.VOICE:
					case Protocol.VOICE_CONFIRM:
					case Protocol.VOICE_REFUSE:
					case Protocol.MSG_PRIVATE:
						//������Ͷ������߽���Ϣ�������ݿ�
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
							//���ͺ����б�
							if(!account.isEmpty()) {
								SendFriendsList sendFriendsList = new SendFriendsList(account,clientsMap,socket);
								friendsList = sendFriendsList.getFriendsList();
							}
							//�������ߵĺ��ѣ���socket����
							new NotifyOnline(account,friendsList,clientsMap);
						}
						break;
					case Protocol.SEARCH_FRIEND:
						SearchFriend searchFriend = new SearchFriend(protocol.getInfo());
						String fnick,fstatus;
						fnick = searchFriend.getNick();
						if(fnick != null) {
							fstatus = searchFriend.getStatus();
							//Э���ʽ��order+��account/��order+account+nick+status
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
					//�������
					clientsMap.remove(account);
					new UpdateUser("quit",socket.getInetAddress().getHostAddress(),account);
					socket.close();
					smf.updateOnlineCount(clientsMap.size());
					smf.updateTable(clientsMap);
					new NotifyQuit(account,friendsList,clientsMap);
					smf.updateLog("\n�û�"+account+"�����ˣ�");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}


