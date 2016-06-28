package unit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
/**
 * 
 * @author 93hezhiyuan@sina.com
 *
 */
public class MyJPanel extends JPanel {
    
	private JTextField timetext,nametext ;
	private MyJTextAreaForMyJPanel context ;
	private Color color = Color.pink ;
	
	public static int GET_MESSAGE = 1 ;
	public static int POST_MESSAGE = 2 ;
	private static final long serialVersionUID = 1L;

	public MyJPanel(int way,Date date,String name,String message) {
		super();
        init(way,date,name,message) ;
	}
	
	private void init(int way,Date date,String name,String message) {
		//设置背景颜色
		setBackground(color) ;
		
		//设置箱式垂直布局
		BoxLayout layout=new BoxLayout(this, BoxLayout.Y_AXIS); 
		setLayout(layout);
		Dimension preferredSize = new Dimension(300,150);//设置尺寸
		setPreferredSize(preferredSize);
		timetext = new JTextField("time") ;
		//设置文字颜色
		timetext.setForeground(Color.red);
		
		//设置文字居中
		timetext.setHorizontalAlignment(JTextField.CENTER) ;
        
		//设置不可编辑
		timetext.setEditable(false);
		
		//设置边框为空
		timetext.setBorder(null);
		
		timetext.setOpaque(false);
		
		nametext = new JTextField(name) ;
		
		if(way == GET_MESSAGE)
			nametext.setHorizontalAlignment(JTextField.LEFT) ;
		if(way == POST_MESSAGE)
			nametext.setHorizontalAlignment(JTextField.RIGHT) ;
		
		
		nametext.setBorder(null);
		nametext.setEditable(false);
		nametext.setOpaque(false);
		
		
		JPanel panel = new JPanel();
		BoxLayout layout2=new BoxLayout(panel, BoxLayout.X_AXIS);
		panel.setLayout(layout2) ;
	
		context = new MyJTextAreaForMyJPanel();
		context.setSize(200, 500);
		context.setText(message) ;
		context.setEditable(false) ;

		if(way == GET_MESSAGE){
			panel.add(context);
			panel.add(new ColorJPanel(color)) ;
			panel.add(new ColorJPanel(color)) ;
			panel.add(new ColorJPanel(color)) ;
			panel.add(new ColorJPanel(color)) ;
		}
			
		if(way == POST_MESSAGE){
			panel.add(new ColorJPanel(color)) ;
			panel.add(new ColorJPanel(color)) ;
			panel.add(new ColorJPanel(color)) ;
			panel.add(new ColorJPanel(color)) ;
			panel.add(context);
		}
		
		add(new JLabel("     "));
		
		//超过10分钟，显示一次时间
		if (islong(date)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			timetext.setText(format.format(new Date())) ;
			add(timetext);
		}

		add(nametext) ;
		add(panel) ;

	}

	private boolean islong(Date date) {
		if(date == null){
			return true ;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long time1 = cal.getTimeInMillis();
		long time2 = cal2.getTimeInMillis();

		long between_minunis = (time2 - time1) / (1000 *600);
         System.out.println("时间："+between_minunis);
        if(between_minunis>10){
        	return true ;
        }else{
        	return false;
        }
	}
	
	
	
	class ColorJPanel extends JPanel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ColorJPanel(Color color) {
			super();
			setBackground(color) ;
		}
	}
}
