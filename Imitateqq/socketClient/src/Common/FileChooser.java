package Common;

import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {
	
	//打开文件
	public static File openFile() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("OpenSource");
		int returnVal = fc.showOpenDialog(fc);
		
		File file;
		if(returnVal == JFileChooser.APPROVE_OPTION) {	//打开操作成功
			file = fc.getSelectedFile();
			return file;
		}
		return null;
	} 
	
	//保存文件
	public static File saveFile(String fileName) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("SavaSource");
		fc.setSelectedFile(new File(fileName));	//设置默认文件名
		
		int returnVal = fc.showSaveDialog(fc);
		
		File file;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			return file;
		}
		return null;
	}
}

