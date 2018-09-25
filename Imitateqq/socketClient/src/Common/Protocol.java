package Common;

import java.text.SimpleDateFormat;
import java.util.Date;

//Э�飺ָ�Сд��+TIME+ʱ��+FROM+�û���/ip+TO+�û���/ip+INFO+��Ϣ����
public class Protocol {

	//����������
	public static final String SERVER = "server";
	//����������
	public static final String ALL = "all";
	
	//Э�����ͷ��
	public static final String TIME_HEAD = "TIME";	
	public static final String FROM_HEAD = "FROM";
	public static final String TO_HEAD = "TO";
	public static final String INFO_HEAD = "INFO";
	
	/*ע��ָ��
	 * �ͻ���->ע����Ϣ->������
	 * ������->������Ϣ->�ͻ���
	 */
	public static final String REGISTER = "register";	//ע��
	public static final String REGISTER_SUCCESS = "r00";	//ע��ɹ�
	public static final String REGISTER_FAIL = "r01";	//ע��ʧ��
	
	/*��½ָ��
	 * �ͻ���->�˺�������Ϣ->������
	 * ������->������Ϣ->�ͻ���
	 */
	public static final String LOGIN = "login";	//��½
	public static final String LOGIN_SUCCESS = "l00";	//��¼�ɹ�
	public static final String LOGIN_FAIL = "l01";	//��½ʧ�ܣ��û��������������
	public static final String LOGIN_REPEATED = "l02";	//�ظ���½
	/*
	 * ����ָ��
	*/
	public static final String MSG_PRIVATE = "private";
	public static final String MSG_GROUP = "group";
	/*
	 * ****����ָ��
	 */
	public static final String GET_FRIENDS = "getfriends";	//�õ�����
	public static final String QUIT = "quit";				//�����˳�
	public static final String ADD_FRIEND = "add_friend";	//��Ӻ���
	public static final String ADD_FIREND_CONFIRM = "af0";	//ȷ�����
	public static final String ADD_FIREND_RUFUSE = "af1";	//�ܾ����
	public static final String FILE = "file";
	public static final String FILE_CONFIRM = "f0";
	public static final String FILE_REFUSE = "f1";
	public static final String AUDIO = "audio";
	public static final String SEARCH_FRIEND = "search_friend";	//��ѯ����
	public static final String SEARCH_FRIEND_SUCCESS = "sf0";	//��ѯ�ɹ�
	public static final String SEARCH_FRIEND_FAIL = "sf1";	//��ѯ�ɹ�
	public static final String VOICE = "voice";
	public static final String VOICE_CONFIRM = "vo0";
	public static final String VOICE_REFUSE = "vo1";
	/*
	 *һЩ���� 
	 */
	
	public static final String SEPERATOR = "+";	//�ָ���
	
	//��������
	public String order = null;	//ָ��
	public String time = null;		//ʱ��
	public String from = null;		//��Դ
	public String to = null;		//ȥ��
	public String info = null;		//����
	public String protocolStr = null;
	
	public Protocol(String protocolStr)
	{
		this.protocolStr = protocolStr;
		String s = protocolStr;
		// ������Ϣָ��
		int index = s.indexOf(TIME_HEAD);
		order = s.substring(0, index);
		s = protocolStr.substring(order.length() + TIME_HEAD.length());
		// ������Ϣʱ��
		index = s.indexOf(FROM_HEAD);
		time = s.substring(0, index);
		s = s.substring(time.length() + FROM_HEAD.length());
		// ������Ϣ��Դȥ��
		index = s.indexOf(TO_HEAD);
		from = s.substring(0, index);
		s = s.substring(from.length() + TO_HEAD.length());
		index = s.indexOf(INFO_HEAD);
		to = s.substring(0, index);
		s = s.substring(to.length() + INFO_HEAD.length());
		// ������Ϣ����
		info = s;
	}
	
	public  Protocol(String order,String from,String to,String info)
	{
		this.order = order;
		this.time = new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(new Date());
		this.from = from;
		this.to = to;
		this.info = info;
		
		//��װЭ����Ϣ
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

