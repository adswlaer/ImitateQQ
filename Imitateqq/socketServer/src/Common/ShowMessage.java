package Common;

import javax.swing.JOptionPane;

public class ShowMessage {

	//��½ʧ��
	public static void loginError()
	{
		JOptionPane.showMessageDialog(null, "�˺Ż��������,����!");
	}
	//�򿪷�����ʧ��
	public static void failOpenServer()
	{
		JOptionPane.showMessageDialog(null, "�򿪷������߳�ʧ��!");
	}
	//��������������ʧ��
	public static void failOpenServerListener()
	{
		JOptionPane.showMessageDialog(null, "��������������ʧ��!");
	}
	//�˿ڱ�ռ��
	public static void portBusy()
	{
		JOptionPane.showMessageDialog(null, "�ö˿ڱ�ռ�ã������˿�����!");
	}
	//�ȴ�����
	public static void waitConnect()
	{
		JOptionPane.showMessageDialog(null, "���ڵȴ�����!");
	}
	/*���ݿ����ʾ���
	 * 
	 */
	//�ɹ��������ݿ�
	public static void successConnectDataBase()
	{
		JOptionPane.showMessageDialog(null, "�ɹ��������ݿ�!");
	}
	
	//�������ݿ�ʧ��
	public static void failConnectDataBase()
	{
		JOptionPane.showMessageDialog(null, "�������ݿ�ʧ��!");
	}
	//��������ʧ��
	public static void failLoadDriver()
	{
		JOptionPane.showMessageDialog(null, "��������ʧ��!");
	}
	//���ݿ�δ��
	public static void unOpenDataBase()
	{
		JOptionPane.showMessageDialog(null, "���ݿ�δ��!");
	}
	//ִ��ʧ��
	public static void failExecute()
	{
		JOptionPane.showMessageDialog(null, "SQL���ִ��ʧ��!");
	}
	//�ɹ��ر����ݿ�
	public static void successCloseDataBase()
	{
		JOptionPane.showMessageDialog(null, "�ɹ��ر����ݿ�!");
	}
	//�ر����ݿ�ʧ��
	public static void failCloseDataBase()
	{
		JOptionPane.showMessageDialog(null, "�ر����ݿ�ʧ��!");
	}
	
}
