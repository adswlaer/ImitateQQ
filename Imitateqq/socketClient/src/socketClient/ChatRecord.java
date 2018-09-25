package socketClient;

import java.io.File;

import Common.FileOperation;

public class ChatRecord {
	
	public String getChatRecord(String faccount) {
		File record = new File("chatRecord\\"+clientListener.getAccount()+"\\"+faccount+".txt");
		String result = "";
		try {
			result = FileOperation.readTxtFile(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;	
	}
	
	public void addChatRecord(String faccount,String message) {
		String[] str = message.split("\n");
		int i=0;
		File gc = new File("chatRecord\\"+clientListener.getAccount()+"\\"+faccount+".txt");
		while(str[i]!=null) {
				try {
					if(i==0)
						FileOperation.writeTxtFile(str[i], gc);
					i++;
					if(i>=str.length)
						break;
					FileOperation.contentToTxt("chatRecord\\"+clientListener.getAccount()+"\\"+faccount+".txt", str[i]);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		}
	}
	
	

}
