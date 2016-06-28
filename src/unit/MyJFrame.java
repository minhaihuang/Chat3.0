package unit;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public abstract class MyJFrame extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyJFrame(){
		super();
	}

	public MyJFrame(GraphicsConfiguration gc) {
		super(gc);
		
	}

	public MyJFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		
	}

	public MyJFrame(String title,int x,int y,int width,int height) {
		super(title);
		init(x, y, width, height) ;
	}
	
	private  void init(int x,int y,int width,int height){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
		setLayout(null) ;
        addWindowListener(this) ;
        
        setBounds(x, y, width, height) ;
        
        setLocationRelativeTo(null) ;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
	}
	
	@Override
	public Component add(Component comp) {
		return super.add(comp);
	}
	
	@Override
	public void add(Component comp, Object constraints) {
		super.add(comp, constraints);
	}
    

	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public abstract void windowClosed(WindowEvent e) ;


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
