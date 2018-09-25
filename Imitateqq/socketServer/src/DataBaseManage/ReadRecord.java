package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Common.Protocol;

public class ReadRecord {
	//声明Connection对象
	public static Connection con;
	//驱动程序名
	private final static String driver = "com.mysql.jdbc.Driver";
	//URL指向要访问的数据库名
	private final static String url = "jdbc:mysql://localhost:3306/qq?characterEncoding=utf8";
	//Mysql配置的用户名
	private final static String user = "root";
	//Mysql配置的密码
	private final static String psw = null;
	private static Statement statement;	
	private ResultSet rs;
	private ArrayList<Protocol> record = new ArrayList<Protocol>();
	
	public ReadRecord(String account) {
		openDataBase();
		
		if(con == null)
			System.out.println("未打开数据库！");
		else {
			try {
				 String selectComm = "select * from record where faccount='"+account+"'";
				 rs = statement.executeQuery(selectComm);
				 
				 if(rs!=null) {
					 while(rs.next()) {
						 Protocol sendProtocol = new Protocol(Protocol.MSG_PRIVATE,rs.getString(3),rs.getString(1),
									rs.getString(2),rs.getString(4));
						 record.add(sendProtocol);
					 }
				 }
				 System.out.println("读取离线记录成功！");
				 String deleteComm = "delete from record where faccount='"+account+"'";
				 statement.executeUpdate(deleteComm);
				 System.out.println("删除离线信息成功！");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("失败");
				
			}
		}
		
		closeDataBase();
	}
	
	public ArrayList<Protocol> getRecord() {
		return record;
	}
	
	//打开数据库
	public  boolean openDataBase()
	{
		try {
			//加载驱动程序
			Class.forName(driver);
			//getConnection()方法,连接Mysql数据库
			con = DriverManager.getConnection(url,user,psw);
			if(!con.isClosed())
			{
				System.out.println("Succeeded connecting to the DataBase!");
				//ShowMessage.successConnectDataBase();
				statement = con.createStatement();
			}
			return true;
		}catch(ClassNotFoundException cnfex) {
			System.out.println("Failed to load the Driver!");
			//ShowMessage.failLoadDriver();
			cnfex.printStackTrace();
			return false;
		}catch(SQLException sqlex) {
			System.err.println("Failed to connect the DataBase!");
			//ShowMessage.failConnectDataBase();
			sqlex.printStackTrace();
			return false;
		}
	}
			
	//关闭数据库
	public void closeDataBase()
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
				//ShowMessage.unOpenDataBase();
			}
		}catch(Exception e) {
			e.printStackTrace();
			//ShowMessage.failCloseDataBase();
		}
	}	
	
}
