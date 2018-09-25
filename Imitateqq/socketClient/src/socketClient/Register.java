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
			//1.创建客户端Socket，指定服务器地址和端口
			socket = new Socket(Set_Frame.getIP(),Set_Frame.getPort());
			OutputStream os = socket.getOutputStream();//字节输出流
			PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
			
			
			//写成协议形式的消息
			StringBuilder register_Info = new StringBuilder();
			register_Info.append(pwd);
			register_Info.append(Protocol.SEPERATOR);
			register_Info.append(nk);
			Protocol protocol = new Protocol(Protocol.REGISTER, "未注册用户", 
					Protocol.SERVER, register_Info.toString());
			
			//2.获取输出流，向服务器端发送信息
			pw.println(protocol.getProtocolStr());
			pw.flush();
			
			
			//获取输入流，读取服务器端的响应信息
		
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String protocolStr;
			
			while((protocolStr = br.readLine())!=null) {
				Protocol reProtocol = new Protocol(protocolStr);
				JOptionPane.showMessageDialog(null, "注册成功，您的用户名为：" +"\n"
							+ reProtocol.getInfo() + "。请牢记！");
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
