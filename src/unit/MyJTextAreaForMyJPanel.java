package unit;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public   class MyJTextAreaForMyJPanel extends JTextArea implements MouseListener{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPopupMenu pop = null; // ��ʼ�������˵� 
	  
    private JMenuItem copy = null; // ��ʼ�������˵�ѡ��

	public MyJTextAreaForMyJPanel() {
		super() ;
		setCaretPosition(0) ;
		init() ;

        setLineWrap(true);//�Զ����й���
        setWrapStyleWord(true);//���в����ֹ���

	}
	

	private void init() {  
        this.addMouseListener(this);  
        pop = new JPopupMenu();  
        pop.add(copy = new JMenuItem("����"));  
       
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));  
        
        copy.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });   
        this.add(pop);  
    }

	  /** 
     * Mouse
     *  
     * @param e 
     */  
    public void action(ActionEvent e) {  
        String str = e.getActionCommand(); 
        if (str.equals(copy.getText())) { // ����
           this.copy(); 
        }
    }  
  
    public JPopupMenu getPop() {  
        return pop;  
    }  
  
    public void setPop(JPopupMenu pop) {  
        this.pop = pop;  
    }  
  
    /** 
     * �ж��Ƿ��Ҽ�
     *  
     * @return true
     */  
    public boolean isClipboardString() {  
        boolean b = false;  
        Clipboard clipboard = this.getToolkit().getSystemClipboard();  
        Transferable content = clipboard.getContents(this);  
        try {  
            if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {  
                b = true;  
            }  
        } catch (Exception e) {  
        }  
        return b;  
    }  
  

    public void mouseClicked(MouseEvent e) {  

    }  
  
    public void mouseEntered(MouseEvent e) {  
    }  
  
    public void mouseExited(MouseEvent e) {  
    }  
  
    public void mousePressed(MouseEvent e) {  
        if (e.getButton() == MouseEvent.BUTTON3) {  
            pop.show(this, e.getX(), e.getY());  
        }  
    }  
  
    public void mouseReleased(MouseEvent e) {  
    }  
  

}
