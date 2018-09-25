package socketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Common.Protocol;
import socketClientFrame.Set_Frame;

public class Register implements Runnable{
	Socket socket;
	String nk,pwd;
	public Register(String nk, String pwd) {
			this.nk = nk;
			this.pwd = pwd;
			
		}
	public void run() {
		try {
			//1.�����ͻ���Socket��ָ����������ַ�Ͷ˿�
			socket = new Socket(Set_Frame.getIP(),Set_Frame.getPort());
			OutputStream os = socket.getOutputStream();//�ֽ������
			PrintWriter pw = new PrintWriter(os);//���������װΪ��ӡ��
			
			
			//д��Э����ʽ����Ϣ
			StringBuilder register_Info = new StringBuilder();
			register_Info.append(pwd);
			register_Info.append(Protocol.SEPERATOR);
			register_Info.append(nk);
			Protocol protocol = new Protocol(Protocol.REGISTER, "δע���û�", 
					Protocol.SERVER, register_Info.toString());
			
			//2.��ȡ���������������˷�����Ϣ
			pw.println(protocol.getProtocolStr());
			pw.flush();
			
			
			//��ȡ����������ȡ�������˵���Ӧ��Ϣ
		
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String protocolStr;
			
			while((protocolStr = br.readLine())!=null) {
				Protocol reProtocol = new Protocol(protocolStr);
				JOptionPane.showMessageDialog(null, "ע��ɹ��������û���Ϊ��" +"\n"
							+ reProtocol.getInfo() + "�����μǣ�");
			}
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.shutdownOutput();
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}	}
	
}
