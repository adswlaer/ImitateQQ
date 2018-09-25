package socketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Common.Protocol;
import Common.ShowMessage;
import socketServerFrame.ServerMainFrame;

public class ServerRegister {
	
	//注册得到的账号
	private int account=0;  
	
	//声明Connection对象
	public  Connection con;
	//驱动程序名
	private final static String driver = "com.mysql.jdbc.Driver";
	//URL指向要访问的数据库名
	private final static String url = "jdbc:mysql://localhost:3306/qq?characterEncoding=utf8";
	//Mysql配置的用户名
	private final static String user = "root";
	//Mysql配置的密码
	private final static String psw = null;
	private  Statement statement;
	//得到本socket		
	private Socket socket;
	//得到的注册信息
	private String info;
	//发出的协议信息
	private Protocol sendProtocol;

	
	public  ServerRegister(Socket socket,String info){  
		
		this.socket = socket;
		this.info = info;
		
		openDataBase();
		
		String str = info;
		//解析密码
		int index = str.indexOf(Protocol.SEPERATOR);
		String password = str.substring(0,index);
		str = str.substring(password.length()+Protocol.SEPERATOR.length());
		//解析昵称
		String nick = str;
		
		//注册到数据库,返回一个ID
        String sql="INSERT INTO user(password,nick,status) VALUES('"+password+"','"+nick+"',0)";  
        try{  
        //使用JDBC 3.0 getGeneratedKeys   
        statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);  
        //获得到主键Id值  
        ResultSet rs=statement.getGeneratedKeys();  
        if(rs.next()){  
            account=rs.getInt(1);  
        }  
        }catch(Exception e){  
        	e.printStackTrace();
        } 
        closeDataBase();
        
        //返回注册信息给客户端
        try {
        	//获取客户端的输入流
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			String acct = Integer.toString(account);
			System.out.println(acct);
		
			sendProtocol = new Protocol(Protocol.REGISTER_SUCCESS,Protocol.SERVER
				,socket.getInetAddress().getHostAddress(),acct);
		
			pw.println(sendProtocol.getProtocolStr());
			pw.flush();
		
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }  
	
	
	private  void closeDataBase()
	{
		try {
			if(con != null)
			{
				con.close();
				System.out.println("Succeeded closing the DataBase!");
				//ShowMessage.successCloseDataBase();
			}
			else
			{
				System.out.println("DataBase have not been opened!");
				ShowMessage.unOpenDataBase();
			}
		}catch(Exception e) {
			e.printStackTrace();
			ShowMessage.failCloseDataBase();
		}
	}
	
	private void openDataBase()
	{
		try {
			//加载驱动程序
			Class.forName(driver);
			//getConnection()方法,连接Mysql数据库
			con = DriverManager.getConnection(url,user,psw);
			if(!con.isClosed())
			{
				System.out.println("Succeeded connecting to the DataBase!");
				statement = con.createStatement();
			}
		}catch(ClassNotFoundException cnfex) {
			System.out.println("Failed to load the Driver!");
			ShowMessage.failLoadDriver();
			cnfex.printStackTrace();
		}catch(SQLException sqlex) {
			System.err.println("Failed to connect the DataBase!");
			ShowMessage.failConnectDataBase();
			sqlex.printStackTrace();
		}
	}
	
	public Protocol getSendProtocol()
	{
		return sendProtocol;
	}
}
