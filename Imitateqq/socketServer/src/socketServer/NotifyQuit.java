package socketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import Common.Protocol;
import Common.UserInfo;

public class NotifyQuit {
public NotifyQuit(String account,ArrayList<String> friendsList, Map<String, UserInfo> clientsMap) {
		
		for(int i=0;i<friendsList.size();i++) {
			String fAccount = friendsList.get(i);
			
			if(clientsMap.containsKey(fAccount)) {
				Socket fsocket = clientsMap.get(fAccount).getSocket();
				
				try {
					OutputStream fos = fsocket.getOutputStream();
					PrintWriter fpw = new PrintWriter(fos);
					//协议形式为:quit+account
					Protocol sendProtocol = new Protocol(Protocol.QUIT,Protocol.SERVER,fAccount,account);
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
