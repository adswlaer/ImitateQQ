package socketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import Common.Protocol;
import Common.UserInfo;

public class NotifyOnline {

	public NotifyOnline(String account,ArrayList<String> friendsList, Map<String, UserInfo> clientsMap) {
		
		UserInfo info = clientsMap.get(account);
		
		for(int i=0;i<friendsList.size();i++) {
			String fAccount = friendsList.get(i);
			
			if(clientsMap.containsKey(fAccount)) {
				Socket fsocket = clientsMap.get(fAccount).getSocket();
				
				try { 
					//获取好友输出流
					OutputStream fos = fsocket.getOutputStream();
					PrintWriter fpw = new PrintWriter(fos);
					//协议形式为:fAccount+fNick+fIp+fPort+fStatus
					Protocol sendProtocol = new Protocol(Protocol.GET_FRIENDS,Protocol.SERVER,fAccount,
							account+Protocol.SEPERATOR+info.getNick()+Protocol.SEPERATOR+info.getIp()+
							Protocol.SEPERATOR+info.getPort()+Protocol.SEPERATOR+"1");
					//发送协议
					fpw.println(sendProtocol.getProtocolStr());
					fpw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
