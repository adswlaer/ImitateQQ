package Common;

import java.text.SimpleDateFormat;
import java.util.Date;

//协议：指令（小写）+TIME+时间+FROM+用户名/ip+TO+用户名/ip+INFO+信息内容
public class Protocol {

	//服务器名称
	public static final String SERVER = "server";
	//所有人名称
	public static final String ALL = "all";
	
	//协议各项头部
	public static final String TIME_HEAD = "TIME";	
	public static final String FROM_HEAD = "FROM";
	public static final String TO_HEAD = "TO";
	public static final String INFO_HEAD = "INFO";
	
	/*注册指令
	 * 客户端->注册信息->服务器
	 * 服务器->反馈信息->客户端
	 */
	public static final String REGISTER = "register";	//注册
	public static final String REGISTER_SUCCESS = "r00";	//注册成功
	public static final String REGISTER_FAIL = "r01";	//注册失败
	
	/*登陆指令
	 * 客户端->账号密码信息->服务器
	 * 服务器->回馈信息->客户端
	 */
	public static final String LOGIN = "login";	//登陆
	public static final String LOGIN_SUCCESS = "l00";	//登录成功
	public static final String LOGIN_FAIL = "l01";	//登陆失败，用户名或者密码错误
	public static final String LOGIN_REPEATED = "l02";	//重复登陆
	/*
	 * 聊天指令
	*/
	public static final String MSG_PRIVATE = "private";
	public static final String MSG_GROUP = "group";
	/*
	 * ****其他指令
	 */
	public static final String GET_FRIENDS = "getfriends";	//得到好友
	public static final String QUIT = "quit";				//好友退出
	public static final String ADD_FRIEND = "add_friend";	//添加好友
	public static final String ADD_FIREND_CONFIRM = "af0";	//确认添加
	public static final String ADD_FIREND_RUFUSE = "af1";	//拒绝添加
	public static final String FILE = "file";
	public static final String FILE_CONFIRM = "f0";
	public static final String FILE_REFUSE = "f1";
	public static final String AUDIO = "audio";
	public static final String SEARCH_FRIEND = "search_friend";	//查询好友
	public static final String SEARCH_FRIEND_SUCCESS = "sf0";	//查询成功
	public static final String SEARCH_FRIEND_FAIL = "sf1";	//查询成功
	public static final String VOICE = "voice";
	public static final String VOICE_CONFIRM = "vo0";
	public static final String VOICE_REFUSE = "vo1";
	/*
	 *一些符号 
	 */
	
	public static final String SEPERATOR = "+";	//分隔符
	
	//命令内容
	public String order = null;	//指令
	public String time = null;		//时间
	public String from = null;		//来源
	public String to = null;		//去处
	public String info = null;		//内容
	public String protocolStr = null;
	
	public Protocol(String protocolStr)
	{
		this.protocolStr = protocolStr;
		String s = protocolStr;
		// 解析消息指令
		int index = s.indexOf(TIME_HEAD);
		order = s.substring(0, index);
		s = protocolStr.substring(order.length() + TIME_HEAD.length());
		// 解析消息时间
		index = s.indexOf(FROM_HEAD);
		time = s.substring(0, index);
		s = s.substring(time.length() + FROM_HEAD.length());
		// 解析消息来源去处
		index = s.indexOf(TO_HEAD);
		from = s.substring(0, index);
		s = s.substring(from.length() + TO_HEAD.length());
		index = s.indexOf(INFO_HEAD);
		to = s.substring(0, index);
		s = s.substring(to.length() + INFO_HEAD.length());
		// 解析消息内容
		info = s;
	}
	
	public  Protocol(String order,String from,String to,String info)
	{
		this.order = order;
		this.time = new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(new Date());
		this.from = from;
		this.to = to;
		this.info = info;
		
		//组装协议信息
		StringBuilder sb = new StringBuilder();
		sb.append(order);
		sb.append(TIME_HEAD);sb.append(time);
		sb.append(FROM_HEAD);sb.append(from);
		sb.append(TO_HEAD);sb.append(to);
		sb.append(INFO_HEAD);sb.append(info);
		
		protocolStr = sb.toString();
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n---------------------------------\n");
		sb.append("Time: " + time + "\n");
		sb.append("Order: " + order + "\n");
		sb.append("From: " + from + "\n");
		sb.append("To: " + to + "\n");
		sb.append("Info:"+info);
		sb.append("\n----------------------------------\n");
		return sb.toString();
	}
	
	public String getProtocolStr()
	{
		return protocolStr;
	}
	
	public String getOrder()
	{
		return order;
	}
	
	public void setOrder(String order)
	{
		this.order = order;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public void setTime(String time)
	{
		this.time = time;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	public String getTo()
	{
		return to;
	}
	
	public void setTo(String to)
	{
		this.to = to;
	}
	
	public String getInfo()
	{
		return info;
	}
	
	public void setInfo(String info)
	{
		this.info = info;
	}
}

