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
	
	//ע��õ����˺�
	private int account=0;  
	
	//����Connection����
	public  Connection con;
	//����������
	private final static String driver = "com.mysql.jdbc.Driver";
	//URLָ��Ҫ���ʵ����ݿ���
	private final static String url = "jdbc:mysql://localhost:3306/qq?characterEncoding=utf8";
	//Mysql���õ��û���
	private final static String user = "root";
	//Mysql���õ�����
	private final static String psw = null;
	private  Statement statement;
	//�õ���socket		
	private Socket socket;
	//�õ���ע����Ϣ
	private String info;
	//������Э����Ϣ
	private Protocol sendProtocol;

	
	public  ServerRegister(Socket socket,String info){  
		
		this.socket = socket;
		this.info = info;
		
		openDataBase();
		
		String str = info;
		//��������
		int index = str.indexOf(Protocol.SEPERATOR);
		String password = str.substring(0,index);
		str = str.substring(password.length()+Protocol.SEPERATOR.length());
		//�����ǳ�
		String nick = str;
		
		//ע�ᵽ���ݿ�,����һ��ID
        String sql="INSERT INTO user(password,nick,status) VALUES('"+password+"','"+nick+"',0)";  
        try{  
        //ʹ��JDBC 3.0 getGeneratedKeys   
        statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);  
        //��õ�����Idֵ  
        ResultSet rs=statement.getGeneratedKeys();  
        if(rs.next()){  
            account=rs.getInt(1);  
        }  
        }catch(Exception e){  
        	e.printStackTrace();
        } 
        closeDataBase();
        
        //����ע����Ϣ���ͻ���
        try {
        	//��ȡ�ͻ��˵�������
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
			//������������
			Class.forName(driver);
			//getConnection()����,����Mysql���ݿ�
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
