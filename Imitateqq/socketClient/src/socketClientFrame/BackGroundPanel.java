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
        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小  
        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);  
    }  
}
