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
  * 创建文件 
  * @param fileName 
  * @return 
  */  
	public static boolean createDir(String destDirName) {
	    File dir = new File(destDirName);
	    if(dir.exists()) {
	     System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
	     return false;
	    }
	    if(!destDirName.endsWith(File.separator))
	     destDirName = destDirName + File.separator;
	    // 创建单个目录
	    if(dir.mkdirs()) {
	     System.out.println("创建目录" + destDirName + "成功！");
	     return true;
	    } else {
	     System.out.println("创建目录" + destDirName + "成功！");
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
  * 读TXT文件内容 
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
	 
	 System.out.println("读取出来的文件内容是："+"\n"+result);  
	 return result;  
 }  
   
   
 public static boolean writeTxtFile(String content,File  file)throws Exception{  
	 RandomAccessFile mm=null;  
	 boolean flag=false;  
	
	 try { 
		 FileOutputStream fos=null;  
		 fos = new FileOutputStream(file);  	//打开文件输出流
		 fos.write(content.getBytes("GBK"));  	//将文本写入文件中
		 fos.close();  							//关闭文件输出流
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
        String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("文件存在\n");  
            } else {  
                System.out.print("文件不存在\n");  
                f.createNewFile();// 不存在则创建  
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