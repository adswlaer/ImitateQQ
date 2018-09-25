package Common;

import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {
	
	//���ļ�
	public static File openFile() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("OpenSource");
		int returnVal = fc.showOpenDialog(fc);
		
		File file;
		if(returnVal == JFileChooser.APPROVE_OPTION) {	//�򿪲����ɹ�
			file = fc.getSelectedFile();
			return file;
		}
		return null;
	} 
	
	//�����ļ�
	public static File saveFile(String fileName) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("SavaSource");
		fc.setSelectedFile(new File(fileName));	//����Ĭ���ļ���
		
		int returnVal = fc.showSaveDialog(fc);
		
		File file;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			return file;
		}
		return null;
	}
}

