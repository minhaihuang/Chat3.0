package unit;

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
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public  abstract class MyJTextPane extends JTextPane implements DocumentListener,MouseListener{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPopupMenu pop = null; // ��ʼ�������˵� 
	  
    private JMenuItem copy = null, paste = null, cut = null; // ��ʼ�������˵�ѡ��

	public MyJTextPane() {
		super() ;
		getDocument().addDocumentListener(this) ;
		setCaretPosition(0) ;
		init() ;
	}
	
	private void init() {  
        this.addMouseListener(this);  
        pop = new JPopupMenu();  
        pop.add(copy = new JMenuItem("����"));  
        pop.add(paste = new JMenuItem("ճ��"));  
        pop.add(cut = new JMenuItem("����"));  
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));  
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));  
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));  
        copy.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });  
        paste.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });  
        cut.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });  
        this.add(pop);  
    }
	
	
	/**
	 * DocumentEvent
	 */
	public abstract void DocumentEventinsert();

	public abstract void DocumentEventremove();
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		if(getText() != ""){
			DocumentEventinsert() ;
		   }
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if(getText().toCharArray().length == 0 ){
			DocumentEventremove() ;
		}
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		
		
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
        } else if (str.equals(paste.getText())) { // ����
            this.paste();  
        } else if (str.equals(cut.getText())) { // ����
            this.cut();  
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
  
    /** 
     * �ж��Ƿ�ɸ���
     *  
     * @return true
     */  
    public boolean isCanCopy() {  
        boolean b = false;  
        int start = this.getSelectionStart();  
        int end = this.getSelectionEnd();  
        if (start != end)  
            b = true;  
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
            copy.setEnabled(isCanCopy());  
            paste.setEnabled(isClipboardString());  
            cut.setEnabled(isCanCopy());  
            pop.show(this, e.getX(), e.getY());  
        }  
    }  
  
    public void mouseReleased(MouseEvent e) {  
    }  
  

}
