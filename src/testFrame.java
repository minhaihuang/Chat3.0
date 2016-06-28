import java.awt.Container;
import java.awt.Frame;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import unit.MyJPanel;
import unit.MyJTabbedPane;
import unit.myCloseJPanelType;


public class testFrame extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new testFrame().init() ;

	}
	
	private  void init() {
		setLayout(null);
		setSize(400, 400) ;
		setLocationRelativeTo(null) ;
		MyJTabbedPane pane = new MyJTabbedPane();
		pane.setBounds(0, 0, 300, 300) ;
		pane.addMYTab("5645") ;
		pane.addMYTab("7987798") ;
		for(int i = 0 ;i<20;i++){
			if(i%2 == 0)
				pane.addmessage("5645",new MyJPanel(1,new Date(),"dghdfg","798798798798798\n4746465465\n7464654") );
			else
				pane.addmessage("5645",new MyJPanel(2,new Date(),"dghdfg","798798798798798\n4746465465\n7464654") );
		}
		
		add(pane) ;
		setVisible(true);
	}
	
	private void closei(){
		new myCloseJPanelType().dissolveExit(this, 1,true) ;
	}
}
