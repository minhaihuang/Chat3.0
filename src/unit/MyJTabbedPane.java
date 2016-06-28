

package unit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


public class MyJTabbedPane extends JTabbedPane implements MouseListener{
  /**
     * ����ͼ���Ŵ�С
     */
    private double scaleRatio = 0.3d; 
   
    private HashMap<String, Component> maps = new HashMap<String, Component>();  
     
    public MyJTabbedPane () {
        super();
        addMouseListener(this);
        setTabPlacement(JTabbedPane.LEFT) ;
    }
    
    public boolean isExit(String title){
    	
    	if(maps.get(title) != null){
    		System.out.println("�ñ�ǩ�Ѵ���");
    		return false;
    	}else{
    		return true;
    	}
    }
     
    
    
//    public void setSelectTitle(String title){
//    	setSelectedComponent((JPanel)maps.get(title)) ;
//    }
    
    public  void addmessage(String title,MyJPanel comp){
    	((tipsJPanel)maps.get(title)).add(comp) ;
    	((tipsJPanel)maps.get(title)).validate();
    }
	
	public void addMYTab(String title){
		tipsJPanel jPanel = new tipsJPanel() ;
		maps.put(title, jPanel) ;
		JScrollPane jScrollPane = null ;
		//������ʽ��ֱ����
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jScrollPane = new JScrollPane(jPanel);
		
		addTab(title, null,jScrollPane);
	}
	
     
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {  
        tip ="t"+ component.hashCode();  
        super.insertTab(title, icon, component, tip, index);  
    }  
    
    public void removeTabAt(int index) {  
        super.removeTabAt(index);  
    }  
    
     @Override
    public void removeAll(){
         for(int i=0;i<this.getTabCount();i++)
                    this.removeTabAt(i);
    }
     


 
    @Override
    public void mouseClicked(MouseEvent e){} ;
 
    @Override
    public void mousePressed(MouseEvent e) {};
 
    @Override
    public void mouseReleased(MouseEvent e) {
        //�ر�ͼ��ֻ��Ӧ���
        if(SwingUtilities.isLeftMouseButton(e)) {
            int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());  
            if (tabNumber < 0) {
                return;  
            }  
        } else if(SwingUtilities.isRightMouseButton(e)) {
            showPopupMenu(e);
        }
    }
     
    private void showPopupMenu(final MouseEvent event) {
         
        // �����ǰ�¼����Ҽ��˵��йأ������Ҽ������򵯳��˵�
        if (event.isPopupTrigger()) {
        	
        	//��õ�ǰ���table��ǩ�±�
            final int index = getSelectedIndex() ;
            
           
            final int count = getTabCount(); 
           
            //�����˵�
            JPopupMenu pop = new JPopupMenu();
            
            //��ò˵�ѡ��
            JMenuItem closeCurrent = new JMenuItem("�رյ�ǰ");
            
            closeCurrent.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                	 maps.remove(getTitleAt(index)) ;
                    ((MyJTabbedPane)event.getComponent()).removeTabAt(index);
                   
                }
            });
            
            //��ѡ����ӵ��˵�
            if(index >= 0){
            	 pop.add(closeCurrent);
            }
           
            
            JMenuItem closeLeft = new JMenuItem("�ر���һ��");
            
            closeLeft.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                        ((MyJTabbedPane)event.getComponent()).removeTabAt(index - 1);  
                }
            });

           
            if(index > 0){
            	pop.add(closeLeft);
            }
            
            JMenuItem closeRight = new JMenuItem("�ر���һ��");
            closeRight.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {

                        ((MyJTabbedPane)event.getComponent()).removeTabAt(index + 1);  

                }
            });
            if(index < count - 1){
                pop.add(closeRight);
            }
             
            pop.show(event.getComponent(), event.getX(), event.getY());
            
        }
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
         
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
         
    }
   
    public double getScaleRatio() {  
        return scaleRatio;  
    }  
    public void setScaleRatio(double scaleRatio) {  
        this.scaleRatio = scaleRatio;  
    }  
    
    
    class myToolTip extends JToolTip {  
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
        public Dimension getPreferredSize() {  
            String tip = getTipText();  
            Component component = maps.get(tip);  
            if (component != null) {  
                return new Dimension((int) (getScaleRatio() * component.getWidth()), (int) (getScaleRatio() * component.getHeight()));  
            } else {  
                return super.getPreferredSize();  
            }  
        }  
        @Override
        public void paintComponent(Graphics g) {  
            String tip = getTipText();  
            Component component = maps.get(tip);  
            if (component instanceof JComponent) {  
                JComponent jcomponent = (JComponent) component;  
                Graphics2D g2d = (Graphics2D) g;  
                AffineTransform at = g2d.getTransform();  
                g2d.transform(AffineTransform.getScaleInstance(getScaleRatio(), getScaleRatio()));  
                ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();  
                updateDoubleBuffered(jcomponent, dbcomponents);  
                jcomponent.paint(g);  
                resetDoubleBuffered(dbcomponents);  
                g2d.setTransform(at);  
            }
            this.setOpaque(false);
        }
        
        private void updateDoubleBuffered(JComponent component, ArrayList<JComponent> dbcomponents) {  
            if (component.isDoubleBuffered()) {  
                dbcomponents.add(component);  
                component.setDoubleBuffered(false);  
            }  
            for (int i = 0; i < component.getComponentCount(); i++) {  
                Component c = component.getComponent(i);  
                if (c instanceof JComponent) {  
                    updateDoubleBuffered((JComponent) c, dbcomponents);  
                }  
            }  
        }  
        private void resetDoubleBuffered(ArrayList<JComponent> dbcomponents) {  
            for (JComponent component : dbcomponents) {  
                component.setDoubleBuffered(true);  
            }  
        }  
    }


    
    class tipsJPanel extends JPanel {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public tipsJPanel() {
			super();
		}
    }
    
    
    

}

