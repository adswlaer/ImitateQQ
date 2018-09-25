package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Common.ShowMessage;


public class SearchFriend {
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
	private String nick=null,status;
	public SearchFriend(String account) {
		openDataBase();
		//查询朋友数据库
		String mysqlComm = "select * from user where account='"+account+"'";
				
		if(con == null)
			ShowMessage.unOpenDataBase();
		else {
			try {
				 ResultSet rs = statement.executeQuery(mysqlComm);
				 while(rs.next()) {
					 nick = rs.getString(3);
					 status = rs.getString(5);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		closeDataBase();		
		
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
		
		public String getNick() {
			return nick;
		}
		
		public String getStatus() {
			return status;
		}
		
}
