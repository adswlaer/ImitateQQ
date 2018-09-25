package Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;  
  
public class FileOperation {  
   
	//String account;
	//path = "record/"+account+".txt";
 /** 
  * �����ļ� 
  * @param fileName 
  * @return 
  */  
	public static boolean createDir(String destDirName) {
	    File dir = new File(destDirName);
	    if(dir.exists()) {
	     System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѵ��ڣ�");
	     return false;
	    }
	    if(!destDirName.endsWith(File.separator))
	     destDirName = destDirName + File.separator;
	    // ��������Ŀ¼
	    if(dir.mkdirs()) {
	     System.out.println("����Ŀ¼" + destDirName + "�ɹ���");
	     return true;
	    } else {
	     System.out.println("����Ŀ¼" + destDirName + "�ɹ���");
	     return false;
	    }
	}	
	
	
 public static boolean createFile(File fileName)throws Exception{  
	 boolean flag=false;  
	 try{  
		 if(!fileName.exists()){  
			 fileName.createNewFile();  
			 flag=true;  
		 }  
	 }catch(Exception e){  
		 e.printStackTrace();  
	 }  
	 return true;  
 }   
   
 /** 
  * ��TXT�ļ����� 
  * @param fileName 
  * @return 
  */  
 public static String readTxtFile(File fileName)throws Exception{  
	 String result = "";  
	 FileReader fileReader=null;  
	 BufferedReader bufferedReader=null;  
	 if(!fileName.exists()){  
		 fileName.createNewFile();   
	 }  
	 try{  
		 fileReader=new FileReader(fileName);  
		 bufferedReader=new BufferedReader(fileReader);  
		 try{  
			 String read=null;  
			 while((read=bufferedReader.readLine())!=null){  
				 result=result+read+"\n";  
			 }  
		 }catch(Exception e){  
			 e.printStackTrace();  
		 }  
	 }catch(Exception e){  
		 e.printStackTrace();  
	 }finally{  
		 if(bufferedReader!=null){  
			 bufferedReader.close();  
		 }  
		 if(fileReader!=null){  
			 fileReader.close();  
		 }  
	 }  
	 
	 System.out.println("��ȡ�������ļ������ǣ�"+"\n"+result);  
	 return result;  
 }  
   
   
 public static boolean writeTxtFile(String content,File  file)throws Exception{  
	 RandomAccessFile mm=null;  
	 boolean flag=false;  
	
	 try { 
		 FileOutputStream fos=null;  
		 fos = new FileOutputStream(file);  	//���ļ������
		 fos.write(content.getBytes("GBK"));  	//���ı�д���ļ���
		 fos.close();  							//�ر��ļ������
		 //   mm=new RandomAccessFile(fileName,"rw");  
		 //   mm.writeBytes(content);  
		 flag=true;  
	 } catch (Exception e) {  
		 // TODO: handle exception  
		 e.printStackTrace();  
	 }finally{  
		 if(mm!=null){  
			 mm.close();  
		 }  
	 } 
	 
	 return flag;  
 }  
  
  
  
public static void contentToTxt(String filePath, String content) {  
        String str = new String(); //ԭ��txt����  
        String s1 = new String();//���ݸ���  
        
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("�ļ�����\n");  
            } else {  
                System.out.print("�ļ�������\n");  
                f.createNewFile();// �������򴴽�  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            //System.out.println(s1);  
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}  