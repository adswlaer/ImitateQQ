package Common;

import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

import socketClientFrame.FriendsCell;
import socketClientFrame.PersonalChat_Frame;

//用户信息类
public class FriendsInfo {

	private String account;
	private String nick;
	private String ip;
	private int port=0;
	private int status;
	private FriendsCell cell;
	
	public FriendsInfo(String account,String nick,String ip,int status,FriendsCell cell)
	{
		this.account = account;	//用户名
		this.nick = nick;	//昵称
		this.ip = ip;	//ip	
		this.status = status;	//状态
		this.cell = cell;	//列表元胞
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

