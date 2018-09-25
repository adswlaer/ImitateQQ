package socketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import Common.Protocol;
import Common.ShowMessage;
import Common.UserInfo;
import DataBaseManage.GetFriends;
import DataBaseManage.mysqlManage;

public class ServerLogin {

	private String info;
	private String order;
	private String account;
	private String password;
	private String nick;
	private int port;
	private Socket socket;
	private Map<String, UserInfo> clientsMap;
	private Protocol loginProtocol;
	
	
	public ServerLogin(Socket socket, Map<String, UserInfo> clientsMap,String info)
	{
		this.socket = socket;
		this.info = info;
		this.clientsMap = clientsMap;
		
		String dbAccount = null;
		String dbPassword = null;
		
		mysqlManage db = new mysqlManage();
		db.openDataBase();
		
		String str = info;
		//解析账号
		int index = str.indexOf(Protocol.SEPERATOR);
		account = str.substring(0,index);
		str = str.substring(account.length()+Protocol.SEPERATOR.length());
		//解析密码
		password = str;
		
		//test,打印输入进来的账号和密码
		System.out.println("account:"+account+"\n"+"password:"+password);
		
		//查找数据库有无该用户名,若有则判断密码是否一致
		String comm = "SELECT * FROM user where account="+account;
		ResultSet rs = db.query(comm);
		
		try {
			while(rs.next())
			{
				dbAccount = rs.getString("account");
				dbPassword = rs.getString("password");
				nick = rs.getString("nick");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//test,打印数据库查询到的账号和密码
		System.out.println("dbAccount:"+dbAccount);
		System.out.println("dbPassword:"+dbPassword);
		
		//关闭数据库
		db.closeDataBase();
		
		//判断账号密码是否正确
		if(dbAccount == null)
			 order = Protocol.LOGIN_FAIL;
		else {
			if(password.equals(dbPassword))
			 order = Protocol.LOGIN_SUCCESS;
			else
			 order = Protocol.LOGIN_FAIL;
		}
		
		//判断有无重复登陆
		if(order.equals(Protocol.LOGIN_SUCCESS)&&clientsMap.containsKey(account))
			order = Protocol.LOGIN_REPEATED;
		
		loginProtocol = new Protocol(order,Protocol.SERVER,socket.getInetAddress().toString(),nick);
		//回复信息给客户端
		sendToClient(loginProtocol.getProtocolStr());	
		
		//若登陆成功，将用户信息加入到clientsMap中
		if(order.equals(Protocol.LOGIN_SUCCESS)&&!account.isEmpty()) {
			UserInfo userInfo = new UserInfo(account,nick,socket);
			clientsMap.put(account, userInfo);
			
			
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
	
	
	
	public String getNick()
	{
		return nick;
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public String getOrder()
	{
		return order;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public Protocol getLoginProtocol()
	{
		return loginProtocol;
	}
}
