package DataBaseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFriend {
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
		
		public AddFriend(String id1,String id2) {
			openDataBase();
			
			String time = new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(new Date());
			
			String mysqlComm = "insert into friends values('"+id1+"','"+id2+"','"+time+"')";
			
			if(con == null)
				System.out.println("δ�����ݿ⣡");
			else {
				try {
					 statement.executeUpdate(mysqlComm);
					 System.out.println("�û���¼��Ϣ���³ɹ���");
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("�û���¼��Ϣ����ʧ�ܣ�");
					
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
