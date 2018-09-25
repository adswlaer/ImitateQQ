package Common;

import javax.swing.JOptionPane;

public class ShowMessage {

	//登陆失败
	public static void loginError()
	{
		JOptionPane.showMessageDialog(null, "账号或密码错误,请检查!");
	}
	//打开服务器失败
	public static void failOpenServer()
	{
		JOptionPane.showMessageDialog(null, "打开服务器线程失败!");
	}
	//服务器监听器打开失败
	public static void failOpenServerListener()
	{
		JOptionPane.showMessageDialog(null, "服务器监听器打开失败!");
	}
	//端口被占用
	public static void portBusy()
	{
		JOptionPane.showMessageDialog(null, "该端口被占用，换个端口试试!");
	}
	//等待连接
	public static void waitConnect()
	{
		JOptionPane.showMessageDialog(null, "正在等待连接!");
	}
	/*数据库的提示语句
	 * 
	 */
	//成功连接数据库
	public static void successConnectDataBase()
	{
		JOptionPane.showMessageDialog(null, "成功连接数据库!");
	}
	
	//连接数据库失败
	public static void failConnectDataBase()
	{
		JOptionPane.showMessageDialog(null, "连接数据库失败!");
	}
	//加载驱动失败
	public static void failLoadDriver()
	{
		JOptionPane.showMessageDialog(null, "加载驱动失败!");
	}
	//数据库未打开
	public static void unOpenDataBase()
	{
		JOptionPane.showMessageDialog(null, "数据库未打开!");
	}
	//执行失败
	public static void failExecute()
	{
		JOptionPane.showMessageDialog(null, "SQL语句执行失败!");
	}
	//成功关闭数据库
	public static void successCloseDataBase()
	{
		JOptionPane.showMessageDialog(null, "成功关闭数据库!");
	}
	//关闭数据库失败
	public static void failCloseDataBase()
	{
		JOptionPane.showMessageDialog(null, "关闭数据库失败!");
	}
	
}
