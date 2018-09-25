package socketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import Common.Protocol;
import Common.UserInfo;
import DataBaseManage.GetFriends;
import DataBaseManage.SearchFriend;
public class SendFriendsList{

	private Socket socket;
	private String account;
	private Map<String, UserInfo> clientsMap;
	private String nick;
	private ArrayList<String> friendsList;
	private String fNick = null;
	private String fIp = null;
	private String fPort = null;
	private String fStatus = "0";
	
	public SendFriendsList(String account, Map<String, UserInfo> clientsMap,Socket socket) {
		this.account = account;
		this.clientsMap = clientsMap;
		this.socket = socket;
		this.nick = clientsMap.get(account).getNick();
		
		run();
	}
	
	public void run() {
		//���ͱ�socket�ĺ�����Ϣ�������ͱ�socket���ߵ���Ϣ������
		//���ݲ������������Һ���
		GetFriends getFriends = new GetFriends(account);	
		friendsList = getFriends.getFriendsList();
		for(int i=0;i<friendsList.size();i++) {
			sendFriendsList(friendsList.get(i));	//���ͺ�����Ϣ���û�
		}
		
	}
	
	public void sendFriendsList(String fAccount) 
	{
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);

			if(!fAccount.isEmpty())
			{

				if(clientsMap.containsKey(fAccount)) {
					UserInfo friendsInfo = clientsMap.get(fAccount);
					fNick = friendsInfo.getNick();
					fIp = friendsInfo.getIp();
					fPort = Integer.toString(friendsInfo.getPort());
					fStatus = "1";	//����
				}
				else {
					fIp = null;
					fPort = null;
					fStatus = "0";	//����
					fNick = new SearchFriend(fAccount).getNick();	//��ѯ���ݿ�
				}
				//Э����ʽΪ:fAccount+fNick+fIp+fPort+fStatus
				Protocol sendProtocol = new Protocol(Protocol.GET_FRIENDS,Protocol.SERVER,account
						,fAccount+Protocol.SEPERATOR+fNick+Protocol.SEPERATOR+fIp+
						Protocol.SEPERATOR+fPort+Protocol.SEPERATOR+fStatus);
				
				sendToClient(sendProtocol.getProtocolStr());
				
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendToClient(String protocolStr)
	{
		
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			pw.println(protocolStr);
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getFriendsList()
	{
		return friendsList;
		
	}
}
