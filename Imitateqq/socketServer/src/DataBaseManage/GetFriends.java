package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import Common.ShowMessage;
import Common.UserInfo;

public class GetFriends {
	private String account;
	private ArrayList<String> friendsList = new ArrayList<String>();
	
	//����Connection����
	public static Connection con;
	//����������
	private final static String driver = "com.mysql.jdbc.Driver";
	//URLָ��Ҫ���ʵ����ݿ���
	private final static String url = "jdbc:mysql://localhost:3306/qq?characterEncoding=utf8";
	//Mysql���õ��û���
	private final static String user = "root";
	//Mysql���õ�����
	private final static String psw = null;
	private static Statement statement;	
	
	public GetFriends(String account)
	{
		this.account = account;
		
		openDataBase();
		//��ѯ�������ݿ�
		String mysqlComm = "select * from friends where ID1='"+account+"'or ID2='"+account+"'";
		
		if(con == null)
			ShowMessage.unOpenDataBase();
		else {
			try {
				 ResultSet rs = statement.executeQuery(mysqlComm);
				 while(rs.next()) {
					 if(account.equals(rs.getString(1)))
						 friendsList.add(rs.getString(2));
					 else
						 friendsList.add(rs.getString(1));
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		closeDataBase();
	}
	
	//�����ݿ�
	public  boolean openDataBase()
	{
		try {
			//������������
			Class.forName(driver);
			//getConnection()����,����Mysql���ݿ�
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
			
	//�ر����ݿ�
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
	
	public ArrayList<String> getFriendsList()
	{
		return friendsList;
	}
}
