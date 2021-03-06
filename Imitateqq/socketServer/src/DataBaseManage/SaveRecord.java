package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveRecord {
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

	public SaveRecord(String from,String to,String time,String info) {
		openDataBase();
		
		String mysqlComm = "insert into record values('"+from+"','"+to+"','"+time+"','"+info+"')";
		
		if(con == null)
			System.out.println("未打开数据库！");
		else {
			try {
				 statement.executeUpdate(mysqlComm);
				 System.out.println("离线信息存入成功！");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("离线信息存入失败");
				
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
	
}
