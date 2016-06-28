
package unit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 *
 * @author 独树一帜
 */
public class myCloseJPanelType extends JComponent implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String[] RoatType = {"中心旋转缩小", "向左下旋转缩小", "向右下旋转缩小", "向左上旋转缩小", "向右上旋转缩小", "向上平移", "向下平移", "向左平移", "向右平移"};
    private final double PI = 3.14159;
    private int type;
    private JFrame jframe;
    private int jf_W, jf_H, jf_X, jf_Y;
    private BufferedImage buff_jframe;
    private BufferedImage buff_jwindow;
    private JWindow jwindow;
    private int count = 0;
    private boolean isRun;
    @Override
    public void run() {
        try {
           // Thread.currentThread();
			Thread.sleep(100);
            for (int i = 0; i < 10; i++) {
                count = i;
                jwindow.repaint();
                //Thread.currentThread();
				//   input.next();
                Thread.sleep(100);
            }
            if(!isRun)
             jwindow.setVisible(false);
            else
                System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(myCloseJPanelType.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  void dissolveExit(JFrame jframe ,int type,boolean isRun) {
        try {
        	
        	//初始化数据
            init(jframe,type,isRun);
            
            Robot robot = new Robot();
//             Rectangle frame_rect =jframe.getBounds();
            Rectangle frame_rect = new Rectangle();
            frame_rect.setLocation(jframe.getX()+8, jframe.getY());
            frame_rect.setSize(jframe.getWidth()-16, jframe.getHeight());
            this.buff_jframe = robot.createScreenCapture(frame_rect);

            this.jframe.setVisible(false);

            Dimension screensize = Toolkit.getDefaultToolkit()
                    .getScreenSize();
            
            Rectangle screen_rect = new Rectangle(0, 0,
                    screensize.width, screensize.height);
          
            this.buff_jwindow = robot.createScreenCapture(screen_rect);

            this.jwindow = new JWindow(new JFrame());
            this.jwindow.setSize(screensize);
            
            this.jwindow.add(this);
            
            this.setSize(screensize);
            
            this.jwindow.setVisible(true);

            this.jwindow.repaint();
            
            new Thread(this).start();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(this.buff_jwindow, this.jwindow.getX(),
                this.jwindow.getY(), null);
        AffineTransform old_trans = g2.getTransform();
        switch (type) {
            case 0:
                RotateToCenter(g2);
                break;
            case 1:
                RotateToLeft_down(g2);
            case 3:
                RotateToLeft_up(g2);
                break;
            case 4:
                RotateToRight_up(g2);
                break;
            case 5:
                 RotateToRight_down(g2);
                break;
            case 6:
                 RotateToLeft(g2);
                break;
            case 7:
                 RotateToUp(g2);
                break;
            case 8:
                 RotateToDown(g2);
                break;
            case 9:
                RotateToRight(g2);
            default:
                break;   
        }
        g2.setTransform(old_trans);
    }
   
    private void init(JFrame jframe,int type,boolean isRun){
      this.jframe = jframe;
      this.jf_H=jframe.getHeight();
      this.jf_W=jframe.getWidth();
      this.jf_X=jframe.getX();
      this.jf_Y=jframe.getY();
      this.type=type;
      this.isRun=isRun;
}
    
    private void RotateToCenter(Graphics2D g) {
        g.translate(this.jframe.getX() + this.jframe.getWidth() / 2, this.jframe.getY() + this.jframe.getHeight() / 2);
        double scale = 1.0 / (count + 1);
        g.scale(scale, scale);
        g.rotate(count * PI * 0.2, 0, 0);
        g.drawImage(this.buff_jframe, -this.jframe.getWidth() / 2, -this.jframe.getHeight() / 2, null);
    }

    private void RotateToLeft_down(Graphics2D g) {
        g.translate(this.jframe.getX(), this.jframe.getY());
        g.translate(0, (count + 1) * (this.jframe.getX() + this.jframe.getWidth()) / 20);
        double scale = 1.0 / (count + 1);
        g.scale(scale, scale);
        g.rotate(count * PI * 0.2, this.jframe.getWidth() / 2, this.jframe.getHeight() / 2);
        g.drawImage(this.buff_jframe, 0, 0, null);
    }

    private void RotateToLeft_up(Graphics2D g) {
        g.translate(this.jframe.getX(), this.jframe.getY() + this.getHeight() / 2);
        g.translate(0, -(count + 1) * (this.jframe.getX() + this.jframe.getWidth()) / 20);
        double scale = 1.0 / (count + 1);
        g.scale(scale, scale);
        g.rotate(-count * PI * 0.2, this.jframe.getWidth() / 2, -this.jframe.getHeight() / 2);
        g.drawImage(this.buff_jframe, 0,-this.getHeight() / 2, null);
    }

    private void RotateToRight_down(Graphics2D g) {
        g.translate(this.jframe.getX() + this.jframe.getWidth(), this.jframe.getY());
        g.translate(0, (count + 1) * (this.jframe.getX() + this.jframe.getWidth()) / 20);
        double scale = 1.0 / (count + 1);
        g.scale(scale, scale);
        g.rotate(-count * PI * 0.2, -this.jframe.getWidth() / 2, this.jframe.getHeight() / 2);
        g.drawImage(this.buff_jframe,- this.jframe.getWidth(), 0, null);
    }

    private void RotateToRight_up(Graphics2D g) {
        g.translate(this.jframe.getX() + this.jframe.getWidth(), this.jframe.getY() + this.getHeight());
        g.translate(0, (count + 1) * (this.jframe.getX() + this.jframe.getWidth()) / 20);
        double scale = 1.0 / (count + 1);
        g.scale(scale, scale);
        g.rotate(count * PI * 0.2, -this.jframe.getWidth() / 2, -this.jframe.getHeight() / 2);
        g.drawImage(this.buff_jframe, -this.jframe.getWidth(),  -this.getHeight(), null);
    }

    private void RotateToUp(Graphics2D g) {
        g.translate(this.jframe.getX(), this.jframe.getY());
        g.translate(0, -(count + 1) * (jf_Y + jf_H) / 10);
        g.drawImage(this.buff_jframe, 0, 0, null);
    }

    private void RotateToDown(Graphics2D g) {
        g.translate(this.jframe.getX(), this.jframe.getY());
        g.translate(0, +(count + 1) * (this.buff_jwindow.getHeight() - jf_Y) / 10);
        g.drawImage(this.buff_jframe, 0, 0, null);
    }

    private void RotateToLeft(Graphics2D g) {
        g.translate(this.jframe.getX(), this.jframe.getY());
        g.translate(-(count + 1) * (jf_X + jf_W) / 10, 0);
        g.drawImage(this.buff_jframe, 0, 0, null);
    }

    private void RotateToRight(Graphics2D g) {
        g.translate(this.jframe.getX(), this.jframe.getY());
        g.translate((count + 1) * (this.buff_jwindow.getWidth() - jf_X) / 10, 0);
        g.drawImage(this.buff_jframe, 0, 0, null);
    }

}
