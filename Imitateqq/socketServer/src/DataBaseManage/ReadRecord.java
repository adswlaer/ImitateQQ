package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Common.Protocol;

public class ReadRecord {
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
	private ResultSet rs;
	private ArrayList<Protocol> record = new ArrayList<Protocol>();
	
	public ReadRecord(String account) {
		openDataBase();
		
		if(con == null)
			System.out.println("δ�����ݿ⣡");
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
				 System.out.println("��ȡ���߼�¼�ɹ���");
				 String deleteComm = "delete from record where faccount='"+account+"'";
				 statement.executeUpdate(deleteComm);
				 System.out.println("ɾ��������Ϣ�ɹ���");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ʧ��");
				
			}
		}
		
		closeDataBase();
	}
	
	public ArrayList<Protocol> getRecord() {
		return record;
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
