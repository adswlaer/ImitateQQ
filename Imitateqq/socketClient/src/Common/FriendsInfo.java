package Common;

import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

import socketClientFrame.FriendsCell;
import socketClientFrame.PersonalChat_Frame;

//�û���Ϣ��
public class FriendsInfo {

	private String account;
	private String nick;
	private String ip;
	private int port=0;
	private int status;
	private FriendsCell cell;
	
	public FriendsInfo(String account,String nick,String ip,int status,FriendsCell cell)
	{
		this.account = account;	//�û���
		this.nick = nick;	//�ǳ�
		this.ip = ip;	//ip	
		this.status = status;	//״̬
		this.cell = cell;	//�б�Ԫ��
	}
	
	//get set Account
	public String getAccount()
	{
		return account;
	}
	
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	//get set Nick
	public String getNick()
	{
		return nick;
	}
	
	public void setNick(String nick)
	{
		this.nick = nick;
	}
	
	//get set IP
	public String getIp()
	{
		return ip;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	//get set Port
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	//get set status
	public int getStatus() 
	{
		return status;
	}
	
	public void setStatus(int status) 
	{
		this.status = status;
	}
	
	public FriendsCell getCell() {
		return cell;
	}
	
	public void setCell(FriendsCell cell) {
		this.cell = cell;
	}
	
}

