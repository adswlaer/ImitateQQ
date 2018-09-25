package socketClientFrame;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackGroundPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private ImageIcon icon; 
	private Image img;
	
	public BackGroundPanel(String path) {
		icon = new ImageIcon(path);
		img = icon.getImage();
	}
	
	public void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        //����������Ϊ�˱���ͼƬ���Ը��洰�����е�����С�������Լ����óɹ̶���С  
        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);  
    }  
}
