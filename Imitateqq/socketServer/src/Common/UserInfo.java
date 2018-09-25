package Common;

import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

//用户信息类
public class UserInfo {

	private String account;
	private String nick;
	private String ip;
	private int port;
	private Socket socket=null;
	
	public UserInfo(String account,String nick,Socket socket)
	{
		this.account = account;
		this.nick = nick;
		this.socket = socket;
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	public String getNick()
	{
		return nick;
	}
	
	public void setNick(String nick)
	{
		this.nick = nick;
	}
	
	public String getIp()
	{
		return socket.getInetAddress().getHostAddress();
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public int getPort()
	{
		return socket.getPort();
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}
}
