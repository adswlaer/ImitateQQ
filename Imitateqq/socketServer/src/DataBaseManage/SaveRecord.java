package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveRecord {
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

	public SaveRecord(String from,String to,String time,String info) {
		openDataBase();
		
		String mysqlComm = "insert into record values('"+from+"','"+to+"','"+time+"','"+info+"')";
		
		if(con == null)
			System.out.println("δ�����ݿ⣡");
		else {
			try {
				 statement.executeUpdate(mysqlComm);
				 System.out.println("������Ϣ����ɹ���");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("������Ϣ����ʧ��");
				
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
	
}
